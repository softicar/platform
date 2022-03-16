package com.softicar.platform.common.core.entity;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.number.parser.IntegerParser;
import java.util.Optional;

/**
 * Extractor for the {@link IEntity} ID from a given {@link IDisplayString}.
 *
 * @author Oliver Richers
 */
public class EntityIdFromDisplayStringExtractor {

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

		if (displayString.matches(".*\\[[0-9]+\\]")) {
			int start = displayString.lastIndexOf('[') + 1;
			int end = displayString.lastIndexOf(']');
			return IntegerParser.parse(displayString.substring(start, end));
		} else {
			return Optional.empty();
		}
	}
}
