package com.softicar.platform.common.date;

import com.softicar.platform.common.core.utils.DevNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Parses simple date strings into {@link Day}.
 *
 * @author Oliver Richers
 */
public class DayParser {

	private static final int BASE_YEAR = 2000;
	private static final String DOT = "\\.";
	private static final String MINUS = "-";
	private static final String NUMBER = "[0-9]+";
	private static final String SLASH = "/";
	private static final Collection<Format> FORMATS = Arrays
		.asList(//
			new Format(DOT, 2, 1, 0),
			new Format(SLASH, 2, 0, 1),
			new Format(MINUS, 0, 1, 2));
	private final String text;

	public DayParser(String text) {

		this.text = text;
	}

	/**
	 * Parses the given text into a {@link Day}.
	 *
	 * @return the parsed {@link Day} object (never <i>null</i>)
	 * @throws IllegalDateSpecificationException
	 *             if the given text could not be parsed
	 */
	public Day parseOrThrowIfInvalid() {

		try {
			return FORMATS//
				.stream()
				.filter(format -> format.matches(text))
				.findFirst()
				.map(format -> format.parse(text))
				.orElseThrow(() -> new IllegalDateSpecificationException(text));
		} catch (NumberFormatException exception) {
			throw new IllegalDateSpecificationException(exception, text);
		}
	}

	/**
	 * Parses the given text into a {@link Day}.
	 *
	 * @return the parsed {@link Day} object as {@link Optional}; an empty
	 *         {@link Optional} if parsing failed
	 */
	public Optional<Day> parse() {

		try {
			return Optional.of(parseOrThrowIfInvalid());
		} catch (Exception exception) {
			DevNull.swallow(exception);
			return Optional.empty();
		}
	}

	private static class Format {

		private final String separator;
		private final int yearIndex;
		private final int monthIndex;
		private final int dayIndex;
		private final Collection<String> patterns;

		public Format(String separator, int yearIndex, int monthIndex, int dayIndex) {

			this.separator = separator;
			this.yearIndex = yearIndex;
			this.monthIndex = monthIndex;
			this.dayIndex = dayIndex;
			this.patterns = new ArrayList<>();

			patterns.add(NUMBER + separator + NUMBER + separator + NUMBER);
			if (yearIndex > monthIndex && yearIndex > dayIndex) {
				patterns.add(NUMBER + separator + NUMBER);
				patterns.add(NUMBER + separator + NUMBER + separator);
			}
		}

		public boolean matches(String text) {

			return patterns.stream().anyMatch(pattern -> text.matches(pattern));
		}

		public Day parse(String text) {

			List<Integer> elements = split(text, separator);
			int year = getYear(elements);
			int month = elements.get(monthIndex);
			int day = elements.get(dayIndex);
			return createDay(year, month, day);
		}

		private List<Integer> split(String text, String separator) {

			return Arrays//
				.asList(text.split(separator))
				.stream()
				.map(Integer::parseInt)
				.collect(Collectors.toList());
		}

		private int getYear(List<Integer> elements) {

			return yearIndex < elements.size()? elements.get(yearIndex) : Day.today().getYear().getAbsoluteIndex();
		}

		private Day createDay(int year, int month, int day) {

			return Day.fromYMDChecked(rebaseAbbreviatedYear(year), month, day);
		}

		private int rebaseAbbreviatedYear(int year) {

			return year < 100? BASE_YEAR + year : year;
		}
	}
}
