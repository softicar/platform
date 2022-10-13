package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.date.IllegalTimeSpecificationException;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.common.string.Tokenizer;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Parses simple time strings into {@link Time}.
 *
 * @author Daniel Klose
 */
public class TimeParser {

	private final String text;

	public TimeParser(String text) {

		this.text = Objects.requireNonNull(text);
	}

	public Optional<Time> parseOrThrow() {

		var tokens = tokenize(text);
		if (tokens.isEmpty()) {
			return Optional.empty();
		} else {
			try {
				var hours = Integer.valueOf(tokens.get(0));
				var minutes = Integer.valueOf(tokens.get(1));
				var seconds = Integer.valueOf(tokens.get(2));
				return Optional.of(new Time(hours, minutes, seconds));
			} catch (Exception exception) {
				throw new IllegalTimeSpecificationException(exception, text);
			}
		}
	}

	private List<String> tokenize(String timeString) {

		Objects.requireNonNull(timeString);
		if (timeString.isBlank()) {
			return Collections.emptyList();
		} else {
			var tokens = new Tokenizer(':', '\\').tokenize(timeString);
			if (tokens.size() > 3 || !validateTokenLength(tokens)) {
				throw new IllegalTimeSpecificationException(timeString);
			}
			while (tokens.size() < 3) {
				tokens.add("0");
			}
			return tokens;
		}
	}

	private boolean validateTokenLength(List<String> tokens) {

		return tokens//
			.stream()
			.allMatch(it -> it.length() <= 2);
	}
}
