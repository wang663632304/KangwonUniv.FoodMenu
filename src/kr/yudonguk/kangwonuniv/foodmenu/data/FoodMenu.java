package kr.yudonguk.kangwonuniv.foodmenu.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class FoodMenu implements Serializable
{
	private static final long serialVersionUID = 5662631438827753748L;

	public static class Food implements Serializable
	{
		private static final long serialVersionUID = -5848567020893619123L;

		public String name;
		public float rate;

		public Food()
		{
			this("");
		}

		public Food(String name)
		{
			this(name, -1.0f);
		}

		public Food(String name_, float rate_)
		{
			name = name_;
			rate = rate_;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o)
				return true;

			if (!(o instanceof Food))
				return false;

			Food rhs = (Food) o;

			return name.equals(rhs.name) && rate == rhs.rate;
		}

		public boolean isGroup()
		{
			return false;
		}
	}

	public static class FoodGroup extends Food
	{
		private static final long serialVersionUID = 2273778400758717991L;

		public final List<Food> foods = new ArrayList<Food>();

		public FoodGroup()
		{
			this("");
		}

		public FoodGroup(String name)
		{
			super(name);
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o)
				return true;

			if (!(o instanceof FoodGroup))
				return false;

			FoodGroup rhs = (FoodGroup) o;

			return super.equals(o) && foods.equals(rhs.foods);
		}

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

	public static class Section implements Serializable
	{
		private static final long serialVersionUID = -1056979296710076797L;

		private static final TimeZone DEFAULT_TIME_ZONE = TimeZone
				.getTimeZone("Asia/Seoul");

		public String name;
		public final Calendar startTime = Calendar.getInstance();
		public final Calendar endTime = Calendar.getInstance();
		public final List<Food> foods = new ArrayList<FoodMenu.Food>();

		public Section()
		{
			this("");
		}

		public Section(String name_)
		{
			name = name_;
			setStartTime(0, 0);
			setEndTime(0, 0);
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o)
				return true;

			if (!(o instanceof Section))
				return false;

			Section rhs = (Section) o;

			return name.equals(rhs.name) && startTime.equals(rhs.startTime)
					&& endTime.equals(rhs.endTime) && foods.equals(rhs.foods);
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

		public void setStartTime(int hour, int minute)
		{
			startTime.setTimeZone(DEFAULT_TIME_ZONE);
			startTime.set(Calendar.HOUR_OF_DAY, hour);
			startTime.set(Calendar.MINUTE, minute);
		}

		public void setEndTime(int hour, int minute)
		{
			startTime.setTimeZone(DEFAULT_TIME_ZONE);
			endTime.set(Calendar.HOUR_OF_DAY, hour);
			endTime.set(Calendar.MINUTE, minute);
		}
	}

	public final List<Section> sections = new ArrayList<FoodMenu.Section>();

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (!(o instanceof FoodMenu))
			return false;

		FoodMenu rhs = (FoodMenu) o;

		return sections.equals(rhs.sections);
	}

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
