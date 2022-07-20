package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.entity.EntityIdFromDisplayStringExtractor;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Attempts to extract numeric identifiers (IDs) from the displayed strings of
 * auto-complete elements.
 * <p>
 * ID extraction is performed using {@link EntityIdFromDisplayStringExtractor}.
 * If ID extraction for one or more elements fails, or if the same ID is
 * extracted for different elements, the whole ID extraction is considered to
 * have failed.
 *
 * @author Oliver Richers
 */
class DomAutoCompleteIdMapFactory<T> {

	private final Map<String, T> stringToElementMap;

	public DomAutoCompleteIdMapFactory(Map<String, T> stringToElementMap) {

		this.stringToElementMap = stringToElementMap;
	}

	/**
	 * Tries to extract a consistent ID-mapping for the auto-complete elements.
	 *
	 * @return a {@link Map} which maps from ID to auto-complete element if an
	 *         ID extraction was successful; an empty {@link Optional} otherwise
	 *         (never <i>null</i>)
	 */
	public Optional<Map<Integer, T>> create() {

		var result = new TreeMap<Integer, T>();
		for (var entry: stringToElementMap.entrySet()) {
			var id = new EntityIdFromDisplayStringExtractor(entry.getKey()).extractId();
			if (id.isPresent()) {
				var previous = result.put(id.get(), entry.getValue());
				if (previous != null) {
					return Optional.empty();
				}
			} else {
				return Optional.empty();
			}
		}
		return Optional.of(result);
	}
}
