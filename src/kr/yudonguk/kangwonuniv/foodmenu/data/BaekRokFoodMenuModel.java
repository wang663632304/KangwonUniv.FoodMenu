package kr.yudonguk.kangwonuniv.foodmenu.data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import kr.yudonguk.kangwonuniv.foodmenu.data.WeekFoodMenu.Week;
import kr.yudonguk.kangwonuniv.foodmenu.data.parser.FoodMenuPraser;
import kr.yudonguk.kangwonuniv.foodmenu.data.parser.KnuCoopFoodMenuPraser;
import kr.yudonguk.kangwonuniv.foodmenu.ui.FoodMenuPresenter;

public class BaekRokFoodMenuModel implements FoodMenuModel
{
	final FoodMenuPresenter mPresenter;
	FoodMenuPraser parser = new KnuCoopFoodMenuPraser();
	WeekFoodMenu weekFoodMenu = null;
	final Lock mWeekFoodMenuLock = new ReentrantLock();

	public BaekRokFoodMenuModel(FoodMenuPresenter presenter)
	{
		mPresenter = presenter;
	}

	@Override
	public void update()
	{
	}

	@Override
	public FoodMenu getData(int id)
	{
		mWeekFoodMenuLock.lock();
		if (weekFoodMenu == null)
		{
			try
			{
				URL url = new URL(KnuCoopFoodMenuPraser.BAEK_ROK_URL);
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
		}
		mWeekFoodMenuLock.unlock();

		if (weekFoodMenu == null)
			return null;

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.add(Calendar.DAY_OF_MONTH, id);
		Week dayOfWeek = Week.toWeek(calendar.get(Calendar.DAY_OF_WEEK));
		return weekFoodMenu.get(dayOfWeek);
	}

	@Override
	public void setData(int id, FoodMenu data)
	{
	}
}
