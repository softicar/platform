package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.date.IllegalTimeSpecificationException;
import com.softicar.platform.common.date.Time;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Parses a simple time text (e.g. {@code "23:59:59"}) into {@link Time}.
 * <p>
 * Primarily used by {@link DomTimeInput}.
 *
 * @author Alexander Schmidt
 */
public class DomTimeInputParser {

	private static final Pattern TOKEN_PATTERN = Pattern.compile("^[0-9]+$");
	private final String timeText;

	/**
	 * Constructs a new {@link DomTimeInputParser}.
	 * <p>
	 * A time text shall be given in {@code "HH:mm:ss"} format, e.g.
	 * {@code "23:59:59"}. Shorthand formatting is allowed as follows:
	 *
	 * <pre>
	 * Time Text  | Interpretation
	 * -----------+-----------------
	 * "23:59:59" | 23:59:59
	 * "11:22:33" | 11:22:33
	 * "11:22"    | 11:22:00
	 * "11"       | 11:00:00
	 * "1:2:3"    | 01:02:03
	 * "0:0:0"    | 00:00:00
	 * "0:0"      | 00:00:00
	 * "0"        | 00:00:00
	 * ""         | (Optional#empty)
	 * </pre>
	 *
	 * The time text is invalid if either of the following is true:
	 * <ul>
	 * <li>It does not comply with one of the above formats.</li>
	 * <li>It contains characters that are neither digits nor colons
	 * ({@code ":"}).</li>
	 * <li>It contains more than 3 separate tokens.</li>
	 * <li>It contains negative tokens.</li>
	 * <li>It contains a token that overflows its value range.</li>
	 * </ul>
	 * If the time text is invalid, {@link #parseOrThrow()} will throw an
	 * {@link Exception}.
	 *
	 * @param timeText
	 *            the time text to parse (never <i>null</i>; may be empty)
	 */
	public DomTimeInputParser(String timeText) {

		this.timeText = Objects.requireNonNull(timeText);
	}

	/**
	 * Derives a {@link Time} instance from the time text.
	 * <p>
	 * Returns {@link Optional#empty()} if the time text is empty.
	 *
	 * @return the {@link Time} instance derived from the time text
	 * @throws IllegalTimeSpecificationException
	 *             if the time text is invalid
	 */
	public Optional<Time> parseOrThrow() {

		if (timeText.isEmpty()) {
			return Optional.empty();
		}

		try {
			var tokenValues = extractTokenValues();
			if (tokenValues.size() > 3) {
				throw new IllegalArgumentException();
			}

			var values = new Integer[] { 0, 0, 0 };
			for (int i = 0; i < tokenValues.size(); i++) {
				values[i] = tokenValues.get(i);
			}

			return Optional.of(new Time(values[0], values[1], values[2]));
		} catch (Exception exception) {
			throw new IllegalTimeSpecificationException(exception, timeText);
		}
	}

	private List<Integer> extractTokenValues() {

		return List//
			.of(timeText.split(":", -1))
			.stream()
			.map(this::assertValidToken)
			.map(Integer::valueOf)
			.collect(Collectors.toList());
	}

	private String assertValidToken(String token) {

		if (!TOKEN_PATTERN.matcher(token).find()) {
			throw new IllegalArgumentException();
		}
		return token;
	}
}
