package kr.yudonguk.util.calendar.test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import kr.yudonguk.util.calendar.ModifiedJulianDay;

import org.junit.Before;
import org.junit.Test;



public class ModifiedJulianDayTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testGetDay()
	{
		Calendar calendar = Calendar.getInstance();

		final long endDay = calendar.getTimeInMillis() / (24 * 60 * 60 * 1000);

		calendar.setTimeInMillis(0);
		calendar.set(Calendar.HOUR_OF_DAY, 12);

		final long startDay = calendar.getTimeInMillis()
				/ (24 * 60 * 60 * 1000);

		final long startMJD = new ModifiedJulianDay(
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH)).getDay();
		final long maxIndex = (endDay - startDay) + 100 * 365;
		for (long i = 0; i < maxIndex; i++)
		{
			final long expectedDay = calendar.getTimeInMillis()
					/ (24 * 60 * 60 * 1000);
			final long day = new ModifiedJulianDay(calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH) + 1,
					calendar.get(Calendar.DAY_OF_MONTH)).getDay()
					- startMJD;

			assertEquals(expectedDay, day);

			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
}
