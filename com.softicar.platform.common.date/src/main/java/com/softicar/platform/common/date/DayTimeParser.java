package com.softicar.platform.common.date;

import com.softicar.platform.common.core.exceptions.SofticarException;

/**
 * Parses a string into a day time object.
 *
 * @author Oliver Richers
 */
public class DayTimeParser {

	public enum Format {

		YYWWD(5),
		YYMMDD(6),
		YYYYMMDD(8),
		YYMMDDHHMM(10),
		YYMMDDHHMMSS(12),
		YYYYMMDDHHMM(12),
		YYYYMMDDHHMMSS(14),
		/**
		 * Represents the format: YYYY-MM-DD HH:MM:SS
		 */
		YYYY_MM_DD_HH_MM_SS(19),
		/**
		 * Represents the format: DD.MM.YYYY
		 */
		DD_MM_YYYY(10);

		public int length() {

			return m_length;
		}

		public DayTime parse(String text) {

			return DayTimeParser.parse(text, this);
		}

		private Format(int length) {

			m_length = length;
		}

		private final int m_length;
	}

	public enum State {
		OK,
		INVALID_LENGTH,
		INVALID_RANGE
	}

	public DayTimeParser(String text, Format format) {

		m_state = State.OK;

		// check length
		if (text.length() != format.length()) {
			m_state = State.INVALID_LENGTH;
			return;
		}

		// parse the text
		setDayTime(text, format);
	}

	private void setDayTime(String text, Format format) {

		switch (format) {
		case YYWWD: {
			Day day = DateFormat.YYWWD.parseDay(text);
			Integer hour = 0;
			Integer minute = 0;
			Integer second = 0;
			m_dayTime = new DayTime(day, hour, minute, second);
			break;
		}
		case YYMMDD:
			setDayTime(text + "000000", Format.YYMMDDHHMMSS);
			break;
		case YYYYMMDD:
			setDayTime(text + "000000", Format.YYYYMMDDHHMMSS);
			break;
		case YYMMDDHHMM:
			setDayTime(text + "00", Format.YYMMDDHHMMSS);
			break;
		case YYMMDDHHMMSS: {
			Day day = DateFormat.YYMMDD.parseDay(text.substring(0, 6));
			Integer hour = Integer.parseInt(text.substring(6, 8));
			Integer minute = Integer.parseInt(text.substring(8, 10));
			Integer second = Integer.parseInt(text.substring(10, 12));
			m_dayTime = new DayTime(day, hour, minute, second);
			break;
		}
		case YYYYMMDDHHMMSS: {
			Day day = DateFormat.YYYYMMDD.parseDay(text.substring(0, 8));
			Integer hour = Integer.parseInt(text.substring(8, 10));
			Integer minute = Integer.parseInt(text.substring(10, 12));
			Integer second = Integer.parseInt(text.substring(12, 14));
			m_dayTime = new DayTime(day, hour, minute, second);
			break;
		}
		case YYYYMMDDHHMM: {
			Day day = DateFormat.YYYYMMDD.parseDay(text.substring(0, 8));
			Integer hour = Integer.parseInt(text.substring(8, 10));
			Integer minute = Integer.parseInt(text.substring(10, 12));
			m_dayTime = new DayTime(day, hour, minute, 0);
			break;
		}
		case YYYY_MM_DD_HH_MM_SS: {
			text = text.replace("-", "").replace(":", "").replace(" ", "").replace("T", "");
			setDayTime(text, Format.YYYYMMDDHHMMSS);
			break;
		}
		case DD_MM_YYYY: {
			Day day = DateFormat.DD_MM_YYYY.parseDay(text);
			m_dayTime = new DayTime(day, 0, 0, 0);
			break;
		}
		}
	}

	public State getState() {

		return m_state;
	}

	public DayTime getDayTime() {

		return m_dayTime;
	}

	public static DayTime parse(String text, Format format) {

		DayTimeParser parser = new DayTimeParser(text, format);
		if (!parser.getState().equals(State.OK)) {
			throw new SofticarException("Error while parsing date '%s': %s", text, parser.getState().toString());
		}
		return parser.getDayTime();
	}

	private State m_state;
	private DayTime m_dayTime;
}
