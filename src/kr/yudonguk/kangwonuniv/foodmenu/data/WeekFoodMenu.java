package kr.yudonguk.kangwonuniv.foodmenu.data;


public class WeekFoodMenu
{
	enum DayOfWeek
	{
		Monday(0), Tuesday(1), Wednseday(2), Thursday(3), Friday(4), Saturday(5), Sunday(
				6);

		public final int value;

		DayOfWeek(int value_)
		{
			value = value_;
		}
	}

	public final FoodMenu[] foodMenus;

	public WeekFoodMenu()
	{
		foodMenus = new FoodMenu[DayOfWeek.values().length];
		for (int i = 0; i < foodMenus.length; i++)
		{
			foodMenus[i] = new FoodMenu();
		}
	}

	public FoodMenu get(DayOfWeek dayOfWeek)
	{
		return foodMenus[dayOfWeek.value];
	}

	public void set(DayOfWeek dayOfWeek, FoodMenu foodMenu)
	{
		foodMenus[dayOfWeek.value] = foodMenu;
	}
}
