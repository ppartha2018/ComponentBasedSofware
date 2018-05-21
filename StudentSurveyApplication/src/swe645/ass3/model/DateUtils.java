package swe645.ass3.model;

import java.util.Date;

public class DateUtils {
	/**
	 * Given a Date, returns a String of the form "Day, Month Number, Year", e.g.,
	 * "Wednesday, November 14, 2012". Used by getStartDay and getEndDay.
	 */
	public static String formatDay(Date date) {
		if (date == null) {
			return ("");
		} else {
			return (String.format("%tA, %tB %te, %tY", date, date, date, date));
		}
	}

	private DateUtils() {
	}
}
