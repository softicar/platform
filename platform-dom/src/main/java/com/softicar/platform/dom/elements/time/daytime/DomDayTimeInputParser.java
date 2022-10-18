package com.softicar.platform.dom.elements.time.daytime;

import com.softicar.platform.common.core.locale.ILocale;
import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayParser;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.IllegalDayTimeSpecificationException;
import com.softicar.platform.common.date.IllegalTimeSpecificationException;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.elements.time.DomTimeInputParser;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Parses a simple daytime text (e.g. {@code "2022-12-31 23:59:59"}) into
 * {@link DayTime}.
 * <p>
 * Primarily used by {@link DomDayTimeInput}.
 *
 * @author Alexander Schmidt
 */
public class DomDayTimeInputParser {

	private static final Pattern SEPARATOR = Pattern.compile("\\s+");
	private final String dayTimeText;

	/**
	 * Constructs a new {@link DomDayTimeInputParser}.
	 * <p>
	 * A daytime text shall be given in a format that depends on the
	 * {@link ILocale} of the {@link CurrentBasicUser}, e.g.
	 * {@code "2021-12-31 23:59:59"} for an {@link ILocale} that uses ISO date
	 * format.
	 * <p>
	 * The daytime text consists of two parts, a <i>date</i> part and a
	 * <i>time</i> part.
	 * <ul>
	 * <li>The <i>date</i> part is parsed using {@link DayParser}.</li>
	 * <li>The <i>time</i> part is parsed using {@link DomTimeInputParser}.</li>
	 * <li>Format parsing capabilities and restrictions are inherited from the
	 * respective parsers.</li>
	 * <li>If either of the parsers fails to determine a value, the daytime text
	 * is invalid.</li>
	 * <li>If a <i>date</i> part is specified, and a <i>time</i> part is
	 * omitted, a time of {@code "00:00:00"} is implied; e.g.
	 * {@code "2021-12-31"} implies {@code "2021-12-31 00:00:00"}.</li>
	 * </ul>
	 * <p>
	 * If the daytime text is invalid, {@link #parseOrThrow()} will throw an
	 * {@link Exception}.
	 *
	 * @param dayTimeText
	 *            the daytime text to parse (never <i>null</i>; may be empty)
	 */
	public DomDayTimeInputParser(String dayTimeText) {

		this.dayTimeText = Objects.requireNonNull(dayTimeText);
	}

	/**
	 * Derives a {@link DayTime} instance from the daytime text.
	 * <p>
	 * Returns {@link Optional#empty()} if the daytime text is empty.
	 *
	 * @return the {@link DayTime} instance derived from the daytime text
	 * @throws IllegalTimeSpecificationException
	 *             if the daytime text is invalid
	 */
	public Optional<DayTime> parseOrThrow() {

		if (dayTimeText.isEmpty()) {
			return Optional.empty();
		}

		try {
			var tokens = extractTokens();
			if (tokens.size() > 2) {
				throw new IllegalArgumentException();
			}

			var values = new String[] { "", "00:00:00" };
			for (int i = 0; i < tokens.size(); i++) {
				values[i] = tokens.get(i);
			}

			Day day = new DayParser(values[0]).parse().orElseThrow();
			Time time = new DomTimeInputParser(values[1]).parseOrThrow().orElseThrow();

			return Optional.of(new DayTime(day, time));
		} catch (Exception exception) {
			throw new IllegalDayTimeSpecificationException(exception, dayTimeText);
		}
	}

	private List<String> extractTokens() {

		return List.of(SEPARATOR.split(dayTimeText, -1));
	}
}
