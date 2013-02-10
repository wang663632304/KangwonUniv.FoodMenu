package kr.yudonguk.util.calendar.test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import kr.yudonguk.util.calendar.GregorianDate;

import org.junit.Before;
import org.junit.Test;



public class GregorianDateTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testIsLeapYear()
	{
		GregorianCalendar calendar = new GregorianCalendar();

		final long endDay = calendar.getTimeInMillis() / (24 * 60 * 60 * 1000);

		calendar.setTimeInMillis(0);

		final long startDay = calendar.getTimeInMillis()
				/ (24 * 60 * 60 * 1000);

		final long maxIndex = (endDay - startDay) + 100 * 365;
		for (long i = 0; i < maxIndex; i++)
		{
			GregorianDate date = new GregorianDate(calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH) + 1,
					calendar.get(Calendar.DAY_OF_MONTH));

			assertEquals(calendar.isLeapYear(calendar.get(Calendar.YEAR)),
					date.isLeapYear());

			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

	@Test
	public void testIsLeapMonth()
	{
		GregorianCalendar calendar = new GregorianCalendar();

		final long endDay = calendar.getTimeInMillis() / (24 * 60 * 60 * 1000);

		calendar.setTimeInMillis(0);

		final long startDay = calendar.getTimeInMillis()
				/ (24 * 60 * 60 * 1000);

		final long maxIndex = (endDay - startDay) + 100 * 365;
		for (long i = 0; i < maxIndex; i++)
		{
			GregorianDate date = new GregorianDate(calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH) + 1,
					calendar.get(Calendar.DAY_OF_MONTH));

			assertEquals(calendar.isLeapYear(calendar.get(Calendar.YEAR))
					&& calendar.get(Calendar.MONTH) == 1, date.isLeapMonth());

			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

}
