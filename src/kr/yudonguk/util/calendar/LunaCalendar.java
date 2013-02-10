package kr.yudonguk.util.calendar;

import java.security.InvalidParameterException;

public class LunaCalendar
{
	ModifiedJulianDay mModifiedJulianDay;
	LunaDate mLunaDate;

	public LunaCalendar(int lunaYear, int lunaMonth, int lunaDay)
	{
		this(lunaYear, lunaMonth, lunaDay, false);
	}

	public LunaCalendar(int lunaYear, int lunaMonth, int lunaDay,
			boolean isLeapMonth)
	{
		mLunaDate = new LunaDate(lunaYear, lunaMonth, lunaDay, isLeapMonth);
		mModifiedJulianDay = mLunaDate.toModifiedJulianDay();
	}

	public LunaCalendar(ModifiedJulianDay modifiedJulianDay)
	{
		mModifiedJulianDay = modifiedJulianDay;
		mLunaDate = LunaDate.convert(mModifiedJulianDay);

		if (mLunaDate == null)
		{
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Invalid Paramter >> ");
			stringBuilder.append(modifiedJulianDay.toString());
			throw new InvalidParameterException(stringBuilder.toString());
		}
	}

	public LunaCalendar(LunaCalendar lunaCalendar)
	{
		mModifiedJulianDay = lunaCalendar.mModifiedJulianDay;
		mLunaDate = lunaCalendar.mLunaDate;
	}

	public boolean isLeapYear()
	{
		return mLunaDate.isLeapYear();
	}

	public boolean isLeapMonth()
	{
		return mLunaDate.isLeapMonth;
	}

	public int getLeapMonth()
	{
		return mLunaDate.getLeapMonth();
	}

	public int getYear()
	{
		return mLunaDate.year;
	}

	public int getMonth()
	{
		return mLunaDate.month;
	}

	public int getDay()
	{
		return mLunaDate.day;
	}

	public ModifiedJulianDay getModifiedJulianDay()
	{
		return mModifiedJulianDay;
	}

	public void add(int day)
	{
		mModifiedJulianDay = new ModifiedJulianDay(mModifiedJulianDay.getDay()
				+ day);
		mLunaDate = LunaDate.convert(mModifiedJulianDay);
	}
}
