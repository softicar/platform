package com.softicar.platform.common.core.entity;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.number.parser.IntegerParser;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Extractor for the {@link IEntity} ID from a given {@link IDisplayString}.
 *
 * @author Oliver Richers
 */
public class EntityIdFromDisplayStringExtractor {

	private static final Pattern PATTERN = Pattern.compile(".*\\[([0-9]+)\\]");
	private final String displayString;

	public EntityIdFromDisplayStringExtractor(IDisplayString displayString) {

		this(displayString.toString());
	}

	public EntityIdFromDisplayStringExtractor(String displayString) {

		this.displayString = displayString;
	}

	/**
	 * Returns the extracted ID.
	 * <p>
	 * If no ID could be extracted, an empty {@link Optional} is returned.
	 *
	 * @return the optionally extracted ID
	 */
	public Optional<Integer> extractId() {

		var matcher = PATTERN.matcher(displayString);
		if (matcher.matches()) {
			return IntegerParser.parse(matcher.group(1));
		} else {
			return Optional.empty();
		}
	}
}
