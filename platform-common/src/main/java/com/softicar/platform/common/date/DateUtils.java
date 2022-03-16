package com.softicar.platform.common.date;

import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.common.string.Padding;
import java.util.Date;

/**
 * Some utility functions for dates.
 *
 * @author Oliver Richers
 */
public class DateUtils {

	public static final String FORMAT_ISO = "%y%-%m%-%dom%";
	public static final String FORMAT_DAYTIME_WITHOUT_SECONDS = "%y%-%m%-%dom% %h%:%min%";
	public static final String FORMAT_HOURS_MINUTES = "%h%:%min%";

	private static String formatKey(DayTime dayTime, String key) {

		Day day = dayTime.getDay();

		if (key.equals("")) {
			return "%";
		} else if (key.equals("y")) {
			return "" + day.getYear();
		} else if (key.equals("y_s")) {
			String tmp = day.getYear().toString();
			return tmp.substring(tmp.length() - 2);
		} else if (key.equals("m")) {
			return Padding.padLeft("" + day.getMonth().getIndexWithinYear(), '0', 2);
		} else if (key.equals("m_s")) {
			return "" + day.getMonth().getIndexWithinYear();
		} else if (key.equals("dom")) {
			return Padding.padLeft("" + day.getIndexWithinMonth(), '0', 2);
		} else if (key.equals("dom_s")) {
			return "" + day.getIndexWithinMonth();
		} else if (key.equals("w")) {
			return Padding.padLeft("" + day.getWeek().getIndexWithinYear(), '0', 2);
		} else if (key.equals("h")) {
			return Padding.padLeft("" + dayTime.getHour(), '0', 2);
		} else if (key.equals("h_s")) {
			return "" + dayTime.getHour();
		} else if (key.equals("min")) {
			return Padding.padLeft("" + dayTime.getMinute(), '0', 2);
		} else if (key.equals("min_s")) {
			return "" + dayTime.getMinute();
		} else if (key.equals("sec")) {
			return Padding.padLeft("" + dayTime.getSecond(), '0', 2);
		} else if (key.equals("sec_s")) {
			return "" + dayTime.getSecond();
		} else {
			throw new IllegalArgumentException("Unknown format pattern key '" + key + "'.");
		}
	}

	public static String format(Date date, String pattern) {

		return format(new DayTime(date), pattern);
	}

	public static String format(Day day, String pattern) {

		return format(new DayTime(day, 0, 0, 0), pattern);
	}

	public static String format(DayTime dayTime, String pattern) {

		String[] tmp = pattern.split("%");
		int n = tmp.length;

		for (int i = 1; i < n; i += 2) {
			tmp[i] = formatKey(dayTime, tmp[i]);
		}

		return Imploder.implode(tmp, "");
	}

	/**
	 * Parses the given string into {@link Day} using {@link DayParser}.
	 *
	 * @param dateString
	 *            the string to parse (never null)
	 * @return the parsed {@link Day} or <i>null</i>
	 */
	public static Day parseDate(String dateString) {

		return new DayParser(dateString).parse().orElse(null);
	}
}
