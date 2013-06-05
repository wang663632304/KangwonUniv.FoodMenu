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
import kr.yudonguk.ui.UiModel;
import kr.yudonguk.ui.UiPresenter;
import kr.yudonguk.util.calendar.CalendarUtil;

public abstract class KnuCoopFoodMenuModel implements UiModel<FoodMenu>
{
	private static final FoodMenuParser parser = new KnuCoopFoodMenuParser();
	private WeekFoodMenu mWeekFoodMenu = null;
	private final Calendar mWeekFoodMenuCalendar = Calendar.getInstance();

	@Override
	public boolean enable(UiPresenter<FoodMenu> presenter)
	{
		return true;
	}

	@Override
	public void disable()
	{}

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
		return iterator(Integer.MIN_VALUE);
	}

	@Override
	public Iterator<UiData<FoodMenu>> iterator(final int startId)
	{
		synchronized (mWeekFoodMenuCalendar)
		{
			final Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			CalendarUtil.getWeekBoundary(startDate, endDate,
					mWeekFoodMenuCalendar, Calendar.MONDAY);

			return new Iterator<UiData<FoodMenu>>()
			{
				WeekFoodMenu mWeekFoodMenu = KnuCoopFoodMenuModel.this.mWeekFoodMenu;

				int mIndex = Math.max(
						startId - startDate.get(Calendar.DAY_OF_MONTH), 0);

				@Override
				public void remove()
				{}

				@Override
				public UiData<FoodMenu> next()
				{
					synchronized (startDate)
					{
						if (!hasNext())
							return null;

						int id = startDate.get(Calendar.DAY_OF_MONTH) + mIndex;

						return new UiData<FoodMenu>(id,
								mWeekFoodMenu.foodMenus[mIndex++]);
					}
				}

				@Override
				public boolean hasNext()
				{
					synchronized (startDate)
					{
						if (mWeekFoodMenu == null)
							return false;

						if (mWeekFoodMenu.foodMenus.length <= mIndex)
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
		return reverseIterator(Integer.MAX_VALUE);
	}

	@Override
	public Iterator<UiData<FoodMenu>> reverseIterator(final int startId)
	{
		synchronized (mWeekFoodMenuCalendar)
		{
			final Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			CalendarUtil.getWeekBoundary(startDate, endDate,
					mWeekFoodMenuCalendar, Calendar.MONDAY);

			return new Iterator<UiData<FoodMenu>>()
			{
				WeekFoodMenu mWeekFoodMenu = KnuCoopFoodMenuModel.this.mWeekFoodMenu;

				int mIndex = Math.min(
						startId - startDate.get(Calendar.DAY_OF_MONTH), 6);

				@Override
				public void remove()
				{}

				@Override
				public UiData<FoodMenu> next()
				{
					synchronized (startDate)
					{
						if (!hasNext())
							return null;

						int id = startDate.get(Calendar.DAY_OF_MONTH) + mIndex;

						return new UiData<FoodMenu>(id,
								mWeekFoodMenu.foodMenus[mIndex--]);
					}
				}

				@Override
				public boolean hasNext()
				{
					synchronized (startDate)
					{
						if (mWeekFoodMenu == null)
							return false;

						if (mIndex < 0)
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
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		if (weekFoodMenu == null)
			return null;

		return weekFoodMenu;
	}

	public abstract String getKnuCoopUrl();
}
