package com.softicar.platform.core.module.cron;

import com.softicar.platform.core.module.cron.element.CronEmptyElement;
import com.softicar.platform.core.module.cron.element.CronIllegalElement;
import com.softicar.platform.core.module.cron.element.CronIndexElement;
import com.softicar.platform.core.module.cron.element.CronRangeElement;
import com.softicar.platform.core.module.cron.element.CronStarElement;
import com.softicar.platform.core.module.cron.element.ICronElement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CronParser {

	private static final Map<String, Integer> MONTH_MAP = Map
		.ofEntries(//
			Map.entry("JAN", 1),
			Map.entry("FEB", 2),
			Map.entry("MAR", 3),
			Map.entry("APR", 4),
			Map.entry("MAY", 5),
			Map.entry("JUN", 6),
			Map.entry("JUL", 7),
			Map.entry("AUG", 8),
			Map.entry("SEP", 9),
			Map.entry("OCT", 10),
			Map.entry("NOV", 11),
			Map.entry("DEC", 12));

	private static final Map<String, Integer> WEEKDAY_MAP = Map
		.ofEntries(//
			Map.entry("MON", 1),
			Map.entry("TUE", 2),
			Map.entry("WED", 3),
			Map.entry("THU", 4),
			Map.entry("FRI", 5),
			Map.entry("SAT", 6),
			Map.entry("SUN", 7));

	public static CronExpression parse(String cronString) {

		String[] parts = Optional.ofNullable(cronString).orElse("").split("\\s+", 6);
		CronExpression cronExpression = new CronExpressionBuilder()//
			.setMinutes(parsePart(getAsOptional(parts, 0).orElse("")))
			.setHours(parsePart(getAsOptional(parts, 1).orElse("")))
			.setDays(parsePart(getAsOptional(parts, 2).orElse("")))
			.setMonths(parsePart(getAsOptional(parts, 3).map(CronParser::replaceMonthsWithIndex).orElse("")))
			.setWeekdays(parsePart(getAsOptional(parts, 4).map(CronParser::replaceWeekdaysWithIndex).map(CronParser::replaceZeroSunday).orElse("")))
			.setSurplusTokens(getAsOptional(parts, 5).orElse(null))
			.build();
		return cronExpression;
	}

	private static <T> Optional<T> getAsOptional(T[] array, int index) {

		if (array.length > index) {
			return Optional.ofNullable(array[index]);
		} else {
			return Optional.empty();
		}
	}

	private static String replaceMonthsWithIndex(String partString) {

		for (var monthEntry: MONTH_MAP.entrySet()) {
			partString = partString.replace(monthEntry.getKey(), monthEntry.getValue().toString());
		}
		return partString;
	}

	private static String replaceWeekdaysWithIndex(String partString) {

		for (var dayEntry: WEEKDAY_MAP.entrySet()) {
			partString = partString.replace(dayEntry.getKey(), dayEntry.getValue().toString());
		}
		return partString;
	}

	private static String replaceZeroSunday(String partString) {

		return partString.replace('0', '7');
	}

	private static List<ICronElement> parsePart(String partString) {

		return Arrays//
			.asList(partString.split(","))
			.stream()
			.map(CronParser::parseElement)
			.collect(Collectors.toList());
	}

	private static ICronElement parseElement(String elementString) {

		if (elementString.isEmpty()) {
			return new CronEmptyElement();
		} else {
			Matcher rangeMatcher = Pattern.compile("([0-9]+)-([0-9]+)(/([0-9]+))?").matcher(elementString);
			Matcher starMatcher = Pattern.compile("\\*(/([0-9]+))?").matcher(elementString);
			Matcher indexMatcher = Pattern.compile("([0-9]+)").matcher(elementString);

			if (rangeMatcher.matches()) {
				return new CronRangeElement(
					Integer.parseInt(rangeMatcher.group(1)),
					Integer.parseInt(rangeMatcher.group(2)),
					parseIntOrDefault(rangeMatcher.group(4), 1));
			} else if (starMatcher.matches()) {
				return new CronStarElement(parseIntOrDefault(starMatcher.group(2), 1));
			} else if (indexMatcher.matches()) {
				return new CronIndexElement(Integer.parseInt(indexMatcher.group(1)));
			}
			return new CronIllegalElement(elementString);
		}
	}

	private static Integer parseIntOrDefault(String string, int defaultValue) {

		if (string != null && string.matches("[0-9]+")) {
			return Integer.parseInt(string);
		}
		return defaultValue;
	}
}
