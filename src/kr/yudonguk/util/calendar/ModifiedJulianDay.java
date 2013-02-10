package kr.yudonguk.util.calendar;

import java.util.Calendar;

/**
 * ModifiedJulianDay는 Immutable 객체이다.
 */
public final class ModifiedJulianDay
{
	private static final int[] dayOfWeek = { Calendar.WEDNESDAY,
			Calendar.TUESDAY, Calendar.FRIDAY, Calendar.SATURDAY,
			Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY };
	private static final String[] cheonGan = { "갑", "을", "병", "정", "무", "기",
			"경", "신", "임", "계" };
	private static final String[] jiSin = { "인", "묘", "진", "사", "오", "미", "신",
			"유", "술", "해", "자", "축" };

	private final long mModifiedJulianDay;
	private final GregorianDate mDate;

	public ModifiedJulianDay(int year, int month, int day)
	{
		this(new GregorianDate(year, month, day));
	}

	public ModifiedJulianDay(GregorianDate date)
	{
		mDate = date;
		mModifiedJulianDay = convertToModifiedJulianDay(date);

		assert (convertToGregorian(mModifiedJulianDay).equals(date));
	}

	public ModifiedJulianDay(long modifiedJulianDay)
	{
		mModifiedJulianDay = modifiedJulianDay;
		mDate = convertToGregorian(modifiedJulianDay);

		assert (convertToModifiedJulianDay(mDate) == modifiedJulianDay);
	}

	public ModifiedJulianDay(ModifiedJulianDay modifiedJulianDay)
	{
		mModifiedJulianDay = modifiedJulianDay.mModifiedJulianDay;
		mDate = modifiedJulianDay.mDate;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ModifiedJulianDay)
		{
			ModifiedJulianDay modifiedJulianDay = (ModifiedJulianDay) obj;

			if (modifiedJulianDay.mModifiedJulianDay == mModifiedJulianDay)
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
		stringBuilder.append("mModifiedJulianDay : " + mModifiedJulianDay);
		stringBuilder.append(", ");
		stringBuilder.append(mDate.toString());
		stringBuilder.append("]");

		return stringBuilder.toString();
	}

	public int getDayOfWeek()
	{
		int week = (int) (mModifiedJulianDay % dayOfWeek.length);
		week = week < 0 ? week + 7 : week;
		return dayOfWeek[week];
	}

	public String getCheonGan()
	{
		int cheonGanIndex = (int) (mModifiedJulianDay % cheonGan.length);
		cheonGanIndex = cheonGanIndex < 0 ? cheonGanIndex + cheonGan.length
				: cheonGanIndex;
		return cheonGan[cheonGanIndex];
	}

	public String getJiSin()
	{
		int jiSinIndex = (int) (mModifiedJulianDay % jiSin.length);
		jiSinIndex = jiSinIndex < 0 ? jiSinIndex + jiSin.length : jiSinIndex;
		return jiSin[jiSinIndex];
	}

	public long getDay()
	{
		return mModifiedJulianDay;
	}

	public int getYear()
	{
		return mDate.year;
	}

	public int getMonth()
	{
		return mDate.month;
	}

	public int getDayOfMonth()
	{
		return mDate.day;
	}

	public boolean isLeapYear()
	{
		return mDate.isLeapYear();
	}

	public boolean isLeapMonth()
	{
		return mDate.isLeapMonth();
	}

	private static long convertToModifiedJulianDay(GregorianDate date)
	{
		int year = date.year;
		int month = date.month;
		int day = date.day;

		// 1, 2월일 경우 전년의 13, 14월로 가정하여 계산
		if (month == 1 || month == 2)
		{
			year -= 1;
			month += 12;
		}

		long modifiedJulianDay = (long) (365.25 * year) + (long) (year / 400.0)
				- (long) (year / 100.0) + (long) (30.59 * (month - 2)) + day
				- 678912;

		return modifiedJulianDay;
	}

	private static GregorianDate convertToGregorian(long modifiedJulianDay)
	{
		long j = modifiedJulianDay + 2400000 + 1 - 1721119;
		long y = (4 * j - 1) / 146097;
		j = 4 * j - 1 - 146097 * y;
		long d = j / 4;
		j = (4 * d + 3) / 1461;
		d = 4 * d + 3 - 1461 * j;
		d = (d + 4) / 4;
		long m = (5 * d - 3) / 153;
		d = 5 * d - 3 - 153 * m;
		d = (d + 5) / 5;
		y = 100 * y + j;

		if (m < 10)
		{
			m = m + 3;
		}
		else
		{
			m = m - 9;
			y += 1;
		}

		return new GregorianDate((int) y, (int) m, (int) d);
	}
}
