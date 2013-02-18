package kr.yudonguk.util.calendar;

import java.security.InvalidParameterException;

/**
 * GregorianDate는 Immutable 객체이다.
 */
public class GregorianDate
{
	public final int year;
	public final int month;
	public final int day;

	public GregorianDate(int year_, int month_, int day_)
	{
		if (!isValidDate(year_, month_, day_))
		{
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Invalid Paramter >> ");
			stringBuilder.append("Year : " + year_);
			stringBuilder.append(", Month : " + month_);
			stringBuilder.append(", Day : " + day_);
			throw new InvalidParameterException(stringBuilder.toString());
		}

		year = year_;
		month = month_;
		day = day_;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof GregorianDate)
		{
			GregorianDate date = (GregorianDate) obj;

			if (date.year == year && date.month == month && date.day == day)
				return true;
		}

		return super.equals(obj);
	}

	@Override
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder(super.toString());
		stringBuilder.append("[");
		stringBuilder.append(getClass().getName());
		stringBuilder.append(">>");
		stringBuilder.append("year : " + year);
		stringBuilder.append(", month : " + month);
		stringBuilder.append(", day : " + day);
		stringBuilder.append("]");

		return stringBuilder.toString();
	}

	public boolean isLeapYear()
	{
		return isLeapYear(year);
	}

	public boolean isLeapMonth()
	{
		return isLeapYear() && month == 2;
	}

	private boolean isLeapYear(int year)
	{
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
			return true;
		else
			return false;
	}

	private boolean isValidDate(int year, int month, int day)
	{
		if (year <= 0 || month <= 0 || day <= 0)
			return false;

		if (month > 12)
			return false;

		switch (month)
		{
		case 1:
		case 3:
		case 4:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if (day > 31)
				return false;
			break;

		case 2:
			if (isLeapYear(year))
			{
				if (day > 29)
					return false;
			}
			else
			{
				if (day > 28)
					return false;
			}
			break;

		default:
			if (day > 30)
				return false;
			break;
		}

		return true;
	}
}