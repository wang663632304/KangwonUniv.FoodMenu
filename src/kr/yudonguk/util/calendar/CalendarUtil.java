package kr.yudonguk.util.calendar;

import java.util.Calendar;

public class CalendarUtil
{
	public static void getWeekBoundary(Calendar outFirstDate,
			Calendar outLastDate, Calendar calendar)
	{
		getWeekBoundary(outFirstDate, outLastDate, calendar,
				calendar.getFirstDayOfWeek());
	}

	public static void getWeekBoundary(Calendar outFirstDate,
			Calendar outLastDate, Calendar calendar, int firstDayOfWeek)
	{
		outFirstDate.setTime(calendar.getTime());
		outFirstDate.set(Calendar.HOUR_OF_DAY, 0);
		outFirstDate.set(Calendar.MINUTE, 0);
		outFirstDate.set(Calendar.SECOND, 0);
		outFirstDate.set(Calendar.MILLISECOND, 0);

		int dayOfWeek = outFirstDate.get(Calendar.DAY_OF_WEEK);
		int dayOfWeekDiff = firstDayOfWeek - dayOfWeek;

		if (dayOfWeek > 0)
			outFirstDate.add(Calendar.DAY_OF_MONTH, -7 + dayOfWeekDiff);
		else
			outFirstDate.add(Calendar.DAY_OF_MONTH, dayOfWeekDiff);

		outLastDate.setTime(outFirstDate.getTime());
		outLastDate.set(Calendar.HOUR_OF_DAY, 23);
		outLastDate.set(Calendar.MINUTE, 59);
		outLastDate.set(Calendar.SECOND, 59);
		outLastDate.set(Calendar.MILLISECOND, 999);

		outLastDate.add(Calendar.DAY_OF_MONTH, 6);
	}
}
