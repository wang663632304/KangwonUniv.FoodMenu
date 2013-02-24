package kr.yudonguk.kangwonuniv.foodmenu.data.parser;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.Food;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.FoodGroup;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.Section;
import kr.yudonguk.kangwonuniv.foodmenu.data.WeekFoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.WeekFoodMenu.Week;
import kr.yudonguk.util.ArrayIterator;
import kr.yudonguk.util.StringUtil;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class KnuCoopFoodMenuPraser implements FoodMenuPraser
{
	public static String CHEON_JI_URL = "http://knucoop.kangwon.ac.kr/weekly_menu_01.asp";
	public static String BAEK_ROK_URL = "http://knucoop.kangwon.ac.kr/weekly_menu_02.asp";
	public static String TAE_BAEK_URL = "http://knucoop.kangwon.ac.kr/weekly_menu_03.asp";

	public WeekFoodMenu parse(URL url) throws IOException
	{
		CleanerProperties properties = new CleanerProperties();
		properties.setUseCdataForScriptAndStyle(false);
		properties.setOmitUnknownTags(true);
		properties.setOmitComments(true);

		HtmlCleaner cleaner = new HtmlCleaner(properties);

		TagNode node = cleaner.clean(url);
		if (node == null)
			return null;

		TagNode tableTagNode = findMenuTable(node, "//tbody//tr//td", "구분/날짜");
		if (tableTagNode == null)
			return null;

		return toFoodMenu(new Table(tableTagNode));
	}

	private TagNode findMenuTable(TagNode node, String xPath, String firstCell)
	{
		try
		{
			Object[] objects = node.evaluateXPath(xPath);

			for (Object object : objects)
			{
				TagNode tagNode = (TagNode) object;
				String text = tagNode.getText().toString().trim();

				if (text.equals(firstCell))
				{
					TagNode parent = tagNode.getParent();
					if (parent == null)
						return null;

					parent = parent.getParent();
					if (parent == null)
						return null;

					return parent.getParent();
				}
			}
		} catch (XPatherException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private WeekFoodMenu toFoodMenu(Table table)
	{
		Iterator<List<String>> tableRowItor = table.iterator();
		if (!tableRowItor.hasNext())
			return null;
		tableRowItor.next(); // 테이블 첫 번째 행 제거

		WeekFoodMenu result = new WeekFoodMenu();

		for (; tableRowItor.hasNext();)
		{
			Iterator<String> tableCellItor = tableRowItor.next().iterator();
			if (!tableCellItor.hasNext())
				continue;

			String sectionName = tableCellItor.next();
			sectionName = sectionName == null ? "" : sectionName.trim();
			if (!tableCellItor.hasNext())
				continue;

			String foodGroupName = tableCellItor.next();
			foodGroupName = foodGroupName == null ? "" : foodGroupName.trim();

			// 월~토까지의 식단을 작성하므로, 6개의 FoodMenu를 작성한다.
			for (int i = Week.Monday.value; i <= Week.Saturday.value; i++)
			{
				FoodMenu foodMenu = result.foodMenus[i];
				Section section = findSection(sectionName, foodMenu.sections);
				if (section == null)
				{
					section = new Section(sectionName);
					foodMenu.add(section);
				}

				if (!tableCellItor.hasNext())
					continue;

				FoodGroup foodGroup = new FoodGroup(foodGroupName);
				String rawFoodList = tableCellItor.next();
				rawFoodList = rawFoodList == null ? "" : rawFoodList;

				for (String foodName : StringUtil.split(rawFoodList, "\\r?\\n",
						true))
				{
					String name = StringUtil.removeBracket(foodName, -1).trim();
					if (name.isEmpty())
						continue;

					foodGroup.add(new Food(name));
				}

				section.add(foodGroup);
			}
		}

		Section holidySection = new Section();
		holidySection.add(new Food("휴무"));
		result.get(Week.Sunday).add(holidySection);

		return result;
	}

	private WeekFoodMenu toFoodMenu(TagNode tableNode)
	{
		TagNode[] childNodes = tableNode.getChildTags();
		if (childNodes == null || childNodes.length == 0)
			return null;

		WeekFoodMenu result = new WeekFoodMenu();

		Iterator<TagNode> tableRowItor = new ArrayIterator<TagNode>(childNodes);
		tableRowItor.next(); // 테이블 첫 번째 행 제거

		String sectionName = "";
		int repeatReuseSectionNameCount = 0;
		for (; tableRowItor.hasNext();)
		{
			TagNode tableRow = tableRowItor.next();
			TagNode[] tableCells = tableRow.getChildTags();
			Iterator<TagNode> tableCellItor = new ArrayIterator<TagNode>(
					tableCells);

			if (repeatReuseSectionNameCount <= 0 && tableCellItor.hasNext())
			{
				String rowspanAttribute = tableCells[0]
						.getAttributeByName("rowspan");
				repeatReuseSectionNameCount = rowspanAttribute == null ? 0
						: Integer.parseInt(rowspanAttribute);
				sectionName = tableCellItor.next().getText().toString();
				sectionName = sectionName.trim();
			}
			repeatReuseSectionNameCount -= 1;

			if (!tableCellItor.hasNext())
				continue;

			String foodGroupName = tableCellItor.next().getText().toString();
			foodGroupName = foodGroupName.trim();

			String rawFoodList = "";
			int repeatReuseRawFoodListCount = 0;
			// 월~토까지의 식단을 작성하므로, 6개의 FoodMenu를 작성한다.
			for (int i = Week.Monday.value; i <= Week.Saturday.value; i++)
			{
				FoodMenu foodMenu = result.foodMenus[i];
				Section section = findSection(sectionName, foodMenu.sections);
				if (section == null)
				{
					section = new Section(sectionName);
					foodMenu.add(section);
				}

				FoodGroup foodGroup = new FoodGroup(foodGroupName);

				if (repeatReuseRawFoodListCount <= 0 && tableCellItor.hasNext())
				{
					TagNode foodNameCell = tableCellItor.next();
					String colspanAttribute = foodNameCell
							.getAttributeByName("colspan");
					repeatReuseRawFoodListCount = colspanAttribute == null ? 0
							: Integer.parseInt(colspanAttribute);
					rawFoodList = foodNameCell.getText().toString();
				}
				repeatReuseRawFoodListCount -= 1;

				for (String foodName : StringUtil.split(rawFoodList, "\\r?\\n",
						true))
				{
					String name = StringUtil.removeBracket(foodName, -1).trim();
					if (name.isEmpty())
						continue;

					foodGroup.add(new Food(name));
				}

				section.add(foodGroup);
			}
		}

		Section holidySection = new Section();
		holidySection.add(new Food("휴무"));
		result.get(Week.Sunday).add(holidySection);

		return result;
	}

	private Section findSection(String sectionName, List<Section> sections)
	{
		for (Section section : sections)
		{
			if (section.name.equals(sectionName))
				return section;
		}
		return null;
	}
}
