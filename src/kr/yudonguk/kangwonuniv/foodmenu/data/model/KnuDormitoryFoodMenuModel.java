package kr.yudonguk.kangwonuniv.foodmenu.data.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.WeekFoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.WeekFoodMenu.Week;
import kr.yudonguk.kangwonuniv.foodmenu.data.parser.FoodMenuParser;
import kr.yudonguk.kangwonuniv.foodmenu.data.parser.KnuDormitoryFoodMenuParser;
import kr.yudonguk.util.calendar.CalendarUtil;

public abstract class KnuDormitoryFoodMenuModel implements FoodMenuModel
{
	private static final FoodMenuParser parser = new KnuDormitoryFoodMenuParser();
	private WeekFoodMenu mWeekFoodMenu = null;
	private final Calendar mWeekFoodMenuCalendar = Calendar.getInstance();

	@Override
	public FoodMenu getData(int id)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.add(Calendar.DAY_OF_MONTH, id);

		WeekFoodMenu weekFoodMenu = null;

		synchronized (mWeekFoodMenuCalendar)
		{
			if (mWeekFoodMenu != null)
			{
				Calendar startDate = Calendar.getInstance();
				Calendar endDate = Calendar.getInstance();
				CalendarUtil.getWeekBoundary(startDate, endDate,
						mWeekFoodMenuCalendar, Calendar.MONDAY);

				if (calendar.compareTo(startDate) >= 0
						&& calendar.compareTo(endDate) <= 0)
					weekFoodMenu = mWeekFoodMenu;
			}

			if (weekFoodMenu == null)
			{
				weekFoodMenu = getWeekFoodMenu(calendar);

				if (weekFoodMenu == null)
					return null;

				mWeekFoodMenu = weekFoodMenu;
				mWeekFoodMenuCalendar.setTime(calendar.getTime());
			}
		}

		Week dayOfWeek = Week.toWeek(calendar.get(Calendar.DAY_OF_WEEK));
		return weekFoodMenu.get(dayOfWeek);
	}

	public WeekFoodMenu getWeekFoodMenu(Calendar calendar)
	{
		WeekFoodMenu weekFoodMenu = null;
		try
		{
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();

			CalendarUtil.getWeekBoundary(startDate, endDate, calendar,
					Calendar.MONDAY);

			String domitoryUrl = KnuDormitoryFoodMenuParser.AddParameter(
					getKnuDormitoryUrl(), startDate, endDate);
			URL url = new URL(domitoryUrl);
			weekFoodMenu = parser.parse(url);
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return weekFoodMenu;
	}

	public abstract String getKnuDormitoryUrl();
}
