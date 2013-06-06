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
import kr.yudonguk.util.StringUtil;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class KnuCoopFoodMenuParser implements FoodMenuParser
{
	public WeekFoodMenu parse(URL url) throws IOException
	{
		return parse(createHtmlCleaner().clean(url));
	}

	@Override
	public WeekFoodMenu parse(String html)
	{
		try
		{
			return parse(createHtmlCleaner().clean(html));
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private HtmlCleaner createHtmlCleaner()
	{
		CleanerProperties properties = new CleanerProperties();
		properties.setUseCdataForScriptAndStyle(false);
		properties.setOmitUnknownTags(true);
		properties.setOmitComments(true);

		return new HtmlCleaner(properties);
	}

	public WeekFoodMenu parse(TagNode node) throws IOException
	{
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
					String name = removeJunk(foodName);
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

	private String removeJunk(String foodName)
	{
		String result = StringUtil.removeBracket(foodName, -1);
		// 식단표의 괄호 오타를 처리하기 위해서 짝이 맞는 괄호 외의 괄호를 제거한다.
		result = result.replaceAll("\\(|\\)", "");

		return result.trim();
	}
}
