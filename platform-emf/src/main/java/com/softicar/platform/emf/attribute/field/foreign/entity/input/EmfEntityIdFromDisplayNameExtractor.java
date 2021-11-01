package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.common.core.number.parser.IntegerParser;
import java.util.Optional;

public class EmfEntityIdFromDisplayNameExtractor {

	private final String displayName;

	public EmfEntityIdFromDisplayNameExtractor(String displayName) {

		this.displayName = displayName;
	}

	public Optional<Integer> extractId() {

		if (displayName.matches(".*\\[[0-9]+\\]")) {
			int start = displayName.lastIndexOf('[') + 1;
			int end = displayName.lastIndexOf(']');
			return IntegerParser.parse(displayName.substring(start, end));
		} else {
			return Optional.empty();
		}
	}
}
