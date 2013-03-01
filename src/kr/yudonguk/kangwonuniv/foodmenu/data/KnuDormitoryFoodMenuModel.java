package kr.yudonguk.kangwonuniv.foodmenu.data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import kr.yudonguk.kangwonuniv.foodmenu.data.WeekFoodMenu.Week;
import kr.yudonguk.kangwonuniv.foodmenu.data.parser.FoodMenuParser;
import kr.yudonguk.kangwonuniv.foodmenu.data.parser.KnuDormitoryFoodMenuParser;

public abstract class KnuDormitoryFoodMenuModel implements FoodMenuModel
{
	private static final FoodMenuParser parser = new KnuDormitoryFoodMenuParser();
	private WeekFoodMenu weekFoodMenu = null;
	private final Lock mWeekFoodMenuLock = new ReentrantLock();

	@Override
	public FoodMenu getData(int id)
	{
		if (weekFoodMenu == null)
		{
			synchronized (mWeekFoodMenuLock)
			{
				if (weekFoodMenu == null)
				{
					try
					{
						Calendar startDate = Calendar.getInstance();
						startDate.clear();
						startDate.add(Calendar.DAY_OF_MONTH, id);
						int dayOfWeek = startDate.get(Calendar.DAY_OF_WEEK);

						if (dayOfWeek == Calendar.SUNDAY)
							startDate.add(Calendar.DAY_OF_MONTH, -6);
						else
							startDate.add(Calendar.DAY_OF_MONTH, -dayOfWeek
									+ Calendar.MONDAY);

						Calendar endDate = (Calendar) startDate.clone();
						endDate.add(Calendar.DAY_OF_MONTH, 6);

						String domitoryUrl = KnuDormitoryFoodMenuParser
								.AddParameter(getKnuDormitoryUrl(), startDate,
										endDate);
						URL url = new URL(domitoryUrl);
						weekFoodMenu = parser.parse(url);
					}
					catch (MalformedURLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (weekFoodMenu == null)
						return null;
				}
			}
		}

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.add(Calendar.DAY_OF_MONTH, id);
		Week dayOfWeek = Week.toWeek(calendar.get(Calendar.DAY_OF_WEEK));
		return weekFoodMenu.get(dayOfWeek);
	}

	public abstract String getKnuDormitoryUrl();
}
