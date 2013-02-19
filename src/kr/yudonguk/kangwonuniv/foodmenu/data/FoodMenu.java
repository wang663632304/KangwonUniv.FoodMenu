package kr.yudonguk.kangwonuniv.foodmenu.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FoodMenu
{
	public static class Food
	{
		public String name = "";
		public float rate = -1.0f;

		public boolean isGroup()
		{
			return false;
		}
	}

	public static class FoodGroup extends Food
	{
		public ArrayList<Food> foods = new ArrayList<Food>();

		@Override
		public boolean isGroup()
		{
			return true;
		}

		public void add(Food food)
		{
			foods.add(food);
		}

		public Food get(int index)
		{
			return foods.get(index);
		}

		public int size()
		{
			return foods.size();
		}
	}

	public static class Section
	{
		public String name = "";
		public Date startTime = createTime(0, 0);
		public Date endTime = createTime(0, 0);
		public ArrayList<Food> foods = new ArrayList<FoodMenu.Food>();

		public void add(Food food)
		{
			foods.add(food);
		}

		public Food get(int index)
		{
			return foods.get(index);
		}

		public int size()
		{
			return foods.size();
		}

		public static Date createTime(int hour, int minute)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, minute);

			return calendar.getTime();
		}
	}

	public ArrayList<Section> sections = new ArrayList<FoodMenu.Section>();

	public void add(Section section)
	{
		sections.add(section);
	}

	public Section get(int index)
	{
		return sections.get(index);
	}

	public int size()
	{
		return sections.size();
	}
}
