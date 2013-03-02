package kr.yudonguk.kangwonuniv.foodmenu.data;

import java.security.InvalidParameterException;
import java.util.Calendar;

public class WeekFoodMenu
{
	public enum Week
	{
		Monday(0), Tuesday(1), Wednseday(2), Thursday(3), Friday(4), Saturday(5), Sunday(
				6);

		public final int value;

		Week(int value_)
		{
			value = value_;
		}

		public static Week toWeek(int calendarDayOfWeek)
		{
			switch (calendarDayOfWeek)
			{
				case Calendar.MONDAY:
					return Monday;
				case Calendar.TUESDAY:
					return Tuesday;
				case Calendar.WEDNESDAY:
					return Wednseday;
				case Calendar.THURSDAY:
					return Thursday;
				case Calendar.FRIDAY:
					return Friday;
				case Calendar.SATURDAY:
					return Saturday;
				case Calendar.SUNDAY:
					return Sunday;
				default:
					throw new InvalidParameterException();
			}
		}
	}

	public final FoodMenu[] foodMenus;

	public WeekFoodMenu()
	{
		foodMenus = new FoodMenu[Week.values().length];
		for (int i = 0; i < foodMenus.length; i++)
		{
			foodMenus[i] = new FoodMenu();
		}
	}

	public FoodMenu get(Week dayOfWeek)
	{
		return foodMenus[dayOfWeek.value];
	}

	public void set(Week dayOfWeek, FoodMenu foodMenu)
	{
		foodMenus[dayOfWeek.value] = foodMenu;
	}
}
