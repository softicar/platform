package com.softicar.platform.common.date;

/**
 * Enumeration of different date formats.
 * <p>
 * You can use this class to parse text into years, months, weeks or days. The
 * name of the enumerations is only a hint for the programmer what the text may
 * look like.
 * <p>
 * For example <i>MM____YYYY</i> means, the text starts with two digits
 * representing the index of the month [1,12], followed by four unknown
 * characters which is followed by four digits representing the year of the
 * month.
 * <p>
 * The name of the enum itself is not used by the parser. The definition, how to
 * parse the text is specified by the constructor of the enum.
 * <p>
 * The format <i>MM____YYYY</i> itself is the parent format of <i>MM_DD_YYYY</i>
 * which represents the day <i>DD</i> of the month represented by
 * <i>MM____YYYY</i>.
 * <p>
 * If you want to add your own formats, take a look at the constructor
 * #DateFormat(Class type, int begin, int end, DateFormat parent, int
 * parentBegin).
 * 
 * @author Oliver Richers
 */
public enum DateFormat {
	// year
	YY(Year.class, 0, 2, null, 0),
	YYYY(Year.class, 0, 4, null, 0),

	// month
	YYMM(Month.class, 2, 4, YY, 0),
	YYYYMM(Month.class, 4, 6, YYYY, 0),
	YYYY_MM(Month.class, 5, 7, YYYY, 0),
	YYYY____MM(Month.class, 8, 10, YYYY, 0),
	MMYYYY(Month.class, 0, 2, YYYY, 2),
	MMYY(Month.class, 0, 2, YY, 2),
	MM_YY(Month.class, 0, 2, YY, 3),
	MM_YYYY(Month.class, 0, 2, YYYY, 3),
	MM____YYYY(Month.class, 0, 2, YYYY, 6),
	MM____YY(Month.class, 0, 2, YY, 6),
	YY_MM___(Month.class, 3, 5, YY, 0),

	// week
	YYWW(Week.class, 2, 4, YY, 0),
	YY__WW(Week.class, 4, 6, YY, 0),
	YYYYWW(Week.class, 4, 6, YYYY, 0),

	// day of month
	YYMMDD(Day.class, 4, 6, YYMM, 0),
	YYYYMMDD(Day.class, 6, 8, YYYYMM, 0),
	YYYY_MM_DD(Day.class, 8, 10, YYYY_MM, 0),
	YYYY_DD_MM(Day.class, 5, 7, YYYY____MM, 0),
	DD_MM_YY(Day.class, 0, 2, MM_YY, 3),
	DD_MM_YYYY(Day.class, 0, 2, MM_YYYY, 3),
	DDMMYYYY(Day.class, 0, 2, MMYYYY, 2),
	DDMMYY(Day.class, 0, 2, MMYY, 2),
	MM_DD_YYYY(Day.class, 3, 5, MM____YYYY, 0),
	MM_DD_YY(Day.class, 3, 5, MM____YY, 0),
	YY_MM_DD(Day.class, 6, 8, YY_MM___, 0),

	// day of week
	YYWWD(Day.class, 4, 5, YYWW, 0),
	YYYYWWD(Day.class, 6, 7, YYYYWW, 0);

	/**
	 * Parses the specified text into a year.
	 * 
	 * @param text
	 *            the text to parse
	 * @return the corresponding year
	 */
	public Year parseYear(String text) {

		if (m_type != Year.class) {
			throw new IllegalStateException("Called parseYear on a date format not representing a year.");
		}

		switch (this) {
		case YY:
			// take current century
			Year currentYear = Day.today().getYear();
			int century = currentYear.getAbsoluteIndex() / 100 * 100;
			Year year = Year.get(century + parse(text));

			// correct if year is too far away from this year
			int distance = currentYear.getDistance(year);
			if (distance >= 50) {
				year = year.getRelative(-100);
			}
			if (distance < -50) {
				year = year.getRelative(100);
			}

			return year;
		case YYYY:
			return Year.get(parse(text));
		default:
			throw new IllegalStateException("Called parseYear on a date format not representing a year.");
		}
	}

	/**
	 * Parses the specified text into a month.
	 * 
	 * @param text
	 *            the text to parse
	 * @return the corresponding month
	 */
	public Month parseMonth(String text) {

		if (m_type != Month.class) {
			throw new IllegalStateException("Called parseMonth on a date format not representing a month.");
		}

		return m_parent.parseYear(text.substring(m_parentBegin)).getMonths().get(parse(text));
	}

	/**
	 * Parses the specified text into a week.
	 * 
	 * @param text
	 *            the text to parse
	 * @return the corresponding week
	 */
	public Week parseWeek(String text) {

		if (m_type != Week.class) {
			throw new IllegalStateException("Called parseWeek on a date format not representing a week.");
		}

		return m_parent.parseYear(text.substring(m_parentBegin)).getWeeks().get(parse(text));
	}

	/**
	 * Parses the specified text into a day.
	 * 
	 * @param text
	 *            the text to parse
	 * @return the corresponding day
	 */
	public Day parseDay(String text) {

		if (m_type != Day.class) {
			throw new IllegalStateException("Called parseDay on a date format not representing a day.");
		}

		if (m_parent.m_type == Month.class) {
			return m_parent.parseMonth(text.substring(m_parentBegin)).getDays().get(parse(text));
		} else if (m_parent.m_type == Week.class) {
			return m_parent.parseWeek(text.substring(m_parentBegin)).getDays().get(parse(text));
		} else {
			throw new IllegalStateException("Invalid parent date format for day.");
		}
	}

	/**
	 * Constructs a new date format with the specified parameters.
	 * 
	 * @param type
	 *            the date item type that is represented by this format
	 * @param begin
	 *            the begin of the sub-string containing the index of this item
	 * @param end
	 *            the end of the sub-string containing the index of this item
	 * @param parent
	 *            the parent format or null if this item has no parent
	 * @param parentBegin
	 *            the begin of the sub-string containing the index of the parent
	 *            date item
	 */
	private DateFormat(Class<?> type, int begin, int end, DateFormat parent, int parentBegin) {

		m_type = type;
		m_begin = begin;
		m_end = end;
		m_parent = parent;
		m_parentBegin = parentBegin;
	}

	private int parse(String text) {

		return Integer.parseInt(text.substring(m_begin, m_end));
	}

	private Class<?> m_type;
	private int m_begin;
	private int m_end;
	private int m_parentBegin;
	private DateFormat m_parent;
}
