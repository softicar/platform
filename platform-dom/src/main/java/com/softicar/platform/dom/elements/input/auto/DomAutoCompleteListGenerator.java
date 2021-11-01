package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.input.auto.DomAutoCompleteList;
import java.util.Optional;

/**
 * Generates an {@link DomAutoCompleteList} instance using the
 * {@link IDomAutoCompleteInputEngine} and a pattern.
 *
 * @author Alexander Schmidt
 */
public class DomAutoCompleteListGenerator<T> {

	private final IDomAutoCompleteInputEngine<T> inputEngine;
	private final int limit;

	public DomAutoCompleteListGenerator(IDomAutoCompleteInputEngine<T> inputEngine, int limit) {

		this.inputEngine = inputEngine;
		this.limit = limit;
	}

	public DomAutoCompleteList generate(String pattern) {

		DomAutoCompleteList list = new DomAutoCompleteList();
		for (T item: inputEngine.findMatches(pattern, limit)) {
			String displayName = getDisplayName(item);
			if (isNotBlank(displayName)) {
				list.add(displayName, getDescription(item));
			}
		}
		return list;
	}

	private String getDisplayName(T item) {

		return Optional//
			.ofNullable(inputEngine.getDisplayString(item))
			.map(IDisplayString::toString)
			.orElse("");
	}

	private String getDescription(T item) {

		return Optional//
			.ofNullable(inputEngine.getDescription(item))
			.orElse("");
	}

	private boolean isNotBlank(String string) {

		return string != null && !string.trim().isEmpty();
	}
}
