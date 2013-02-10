package kr.yudonguk.util.calendar.test;

import static org.junit.Assert.assertTrue;
import kr.yudonguk.util.calendar.LunaCalendar;

import org.junit.Before;
import org.junit.Test;



public class LunaCalendarTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testAdd()
	{
		LunaCalendar lunaCalendar = new LunaCalendar(LunaData.startDay);

		for (int year = 0; year < LunaData.daysOfYear.length; year++)
		{
			for (int month = 0; month < LunaData.daysOfMonth[year].length - 1; month++)
			{
				int dayMax = LunaData.daysOfMonth[year][month];

				for (int day = 0; day < dayMax; day++)
				{
					assertTrue(lunaCalendar.getYear() == (year + LunaData.startYear)
							&& lunaCalendar.getMonth() == (month + 1)
							&& lunaCalendar.getDay() == (day + 1)
							&& lunaCalendar.isLeapMonth() == false);

					lunaCalendar.add(1);
				}

				if (month == LunaData.leapMonth[year] - 1)
				{
					dayMax = LunaData.daysOfMonth[year][12];
					for (int day = 0; day < dayMax; day++)
					{
						assertTrue(lunaCalendar.getYear() == (year + LunaData.startYear)
								&& lunaCalendar.getMonth() == (month + 1)
								&& lunaCalendar.getDay() == (day + 1)
								&& lunaCalendar.isLeapMonth() == true);

						lunaCalendar.add(1);
					}
				}
			}
		}
	}
}
