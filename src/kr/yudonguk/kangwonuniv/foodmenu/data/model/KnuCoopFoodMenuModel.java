package kr.yudonguk.kangwonuniv.foodmenu.data.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;

import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.WeekFoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.WeekFoodMenu.Week;
import kr.yudonguk.kangwonuniv.foodmenu.data.parser.FoodMenuParser;
import kr.yudonguk.kangwonuniv.foodmenu.data.parser.KnuCoopFoodMenuParser;
import kr.yudonguk.ui.UiData;
import kr.yudonguk.util.calendar.CalendarUtil;

public abstract class KnuCoopFoodMenuModel implements FoodMenuModel
{
	private static final FoodMenuParser parser = new KnuCoopFoodMenuParser();
	private WeekFoodMenu mWeekFoodMenu = null;
	private final Calendar mWeekFoodMenuCalendar = Calendar.getInstance();

	@Override
	public FoodMenu getData(int id)
	{
		if (mWeekFoodMenu == null)
		{
			synchronized (mWeekFoodMenuCalendar)
			{
				if (mWeekFoodMenu == null)
				{
					mWeekFoodMenuCalendar.clear();
					mWeekFoodMenuCalendar.add(Calendar.DAY_OF_MONTH, id);
					mWeekFoodMenu = getWeekFoodMenu(mWeekFoodMenuCalendar);

					if (mWeekFoodMenu == null)
						return null;
				}
			}
		}

		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		CalendarUtil.getWeekBoundary(startDate, endDate, mWeekFoodMenuCalendar,
				Calendar.MONDAY);

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.add(Calendar.DAY_OF_MONTH, id);

		if (calendar.compareTo(startDate) < 0
				|| calendar.compareTo(endDate) > 0)
			return null;

		Week dayOfWeek = Week.toWeek(calendar.get(Calendar.DAY_OF_WEEK));
		return mWeekFoodMenu.get(dayOfWeek);
	}

	@Override
	public Iterator<UiData<FoodMenu>> iterator()
	{
		synchronized (mWeekFoodMenuCalendar)
		{
			return new Iterator<UiData<FoodMenu>>()
			{
				WeekFoodMenu mWeekFoodMenu = KnuCoopFoodMenuModel.this.mWeekFoodMenu;
				Calendar mWeekFoodMenuCalendar = (Calendar) KnuCoopFoodMenuModel.this.mWeekFoodMenuCalendar
						.clone();

				int mIndex = 0;

				@Override
				public void remove()
				{
				}

				@Override
				public UiData<FoodMenu> next()
				{
					synchronized (mWeekFoodMenuCalendar)
					{
						if (!hasNext())
							return null;

						Calendar startDate = Calendar.getInstance();
						Calendar endDate = Calendar.getInstance();
						CalendarUtil.getWeekBoundary(startDate, endDate,
								mWeekFoodMenuCalendar, Calendar.MONDAY);

						int id = startDate.get(Calendar.DAY_OF_MONTH) + mIndex;

						return new UiData<FoodMenu>(id,
								mWeekFoodMenu.foodMenus[mIndex++]);
					}
				}

				@Override
				public boolean hasNext()
				{
					synchronized (mWeekFoodMenuCalendar)
					{
						if (mWeekFoodMenu == null)
							return false;

						if (mWeekFoodMenu.foodMenus.length - 1 <= mIndex)
							return false;

						return true;
					}
				}
			};
		}
	}

	@Override
	public Iterator<UiData<FoodMenu>> reverseIterator()
	{
		synchronized (mWeekFoodMenuCalendar)
		{
			return new Iterator<UiData<FoodMenu>>()
			{
				WeekFoodMenu mWeekFoodMenu = KnuCoopFoodMenuModel.this.mWeekFoodMenu;
				Calendar mWeekFoodMenuCalendar = (Calendar) KnuCoopFoodMenuModel.this.mWeekFoodMenuCalendar
						.clone();

				int mIndex = 6;

				@Override
				public void remove()
				{
				}

				@Override
				public UiData<FoodMenu> next()
				{
					synchronized (mWeekFoodMenuCalendar)
					{
						if (!hasNext())
							return null;

						Calendar startDate = Calendar.getInstance();
						Calendar endDate = Calendar.getInstance();
						CalendarUtil.getWeekBoundary(startDate, endDate,
								mWeekFoodMenuCalendar, Calendar.MONDAY);

						int id = startDate.get(Calendar.DAY_OF_MONTH) + mIndex;

						return new UiData<FoodMenu>(id,
								mWeekFoodMenu.foodMenus[mIndex--]);
					}
				}

				@Override
				public boolean hasNext()
				{
					synchronized (mWeekFoodMenuCalendar)
					{
						if (mWeekFoodMenu == null)
							return false;

						if (mIndex <= 0)
							return false;

						return true;
					}
				}
			};
		}
	}

	public WeekFoodMenu getWeekFoodMenu(Calendar calendar)
	{
		WeekFoodMenu weekFoodMenu = null;
		try
		{
			URL url = new URL(getKnuCoopUrl());
			weekFoodMenu = parser.parse(url);
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		if (weekFoodMenu == null)
			return null;

		return weekFoodMenu;
	}

	public abstract String getKnuCoopUrl();
}
