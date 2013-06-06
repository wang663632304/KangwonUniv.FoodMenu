package kr.yudonguk.kangwonuniv.foodmenu.data.parser;

import android.annotation.SuppressLint;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.Food;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu.Section;
import kr.yudonguk.kangwonuniv.foodmenu.data.WeekFoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.WeekFoodMenu.Week;
import kr.yudonguk.util.StringUtil;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class KnuDormitoryFoodMenuParser implements FoodMenuParser
{
	public static String FIRST_RESTAURANT_URL = "http://knudorm.kangwon.ac.kr/home/sub02/sub02_05_pirnt.jsp?mode=7301000&bil=1";
	public static String THIRD_RESTAURANT_URL = "http://knudorm.kangwon.ac.kr/home/sub02/sub02_05_pirnt.jsp?mode=7301000&bil=3";
	public static String BTL_URL = "http://knudorm.kangwon.ac.kr/home/sub02/sub02_05_pirnt.jsp?mode=7302000&bil=3";

	@SuppressLint("SimpleDateFormat")
	private static final DateFormat mDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static String AddParameter(String url, Calendar startDate,
			Calendar endDate)
	{
		StringBuilder result = new StringBuilder();
		result.append(url);
		result.append("&sdate=");
		result.append(mDateFormat.format(startDate.getTime()));
		result.append("&edate=");
		result.append(mDateFormat.format(endDate.getTime()));

		return result.toString();
	}

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

		TagNode tableTagNode = findMenuTable(node, "//tbody//tr//td", "월");
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

		for (int i = Week.Monday.value; tableRowItor.hasNext()
				&& i <= Week.Sunday.value; i++)
		{
			FoodMenu foodMenu = result.foodMenus[i];
			Iterator<String> tableCellItor = tableRowItor.next().iterator();

			if (tableCellItor.hasNext())
				tableCellItor.next(); // 첫 번째 열 제거

			Section[] sections = { new Section("아침"), new Section("점심"),
					new Section("저녁") };

			for (Section section : sections)
			{
				foodMenu.add(section);

				if (!tableCellItor.hasNext())
					continue;

				String rawFoodList = tableCellItor.next();

				for (String foodName : StringUtil.split(rawFoodList, "\\r?\\n",
						true))
				{
					String name = removeJunk(foodName);
					if (name.isEmpty())
						continue;

					section.add(new Food(name));
				}
			}
		}

		return result;
	}

	private String removeJunk(String foodName)
	{
		String result = StringUtil.removeBracket(foodName, -1);
		// 식단표의 괄호 오타를 처리하기 위해서 짝이 맞는 괄호 외의 괄호를 제거한다.
		result = result.replaceAll("\\(|\\)", "");

		return result.trim();
	}
}
