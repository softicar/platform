package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.entity.EntityIdFromDisplayStringExtractor;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Determines numeric identifiers (IDs) for the values of a
 * {@link DomAutoCompleteInput}.
 * <p>
 * The IDs are determined by using {@link EntityIdFromDisplayStringExtractor} on
 * the display strings of the values. If the ID determination for one or more
 * values fails, or if the same ID is determined for different values, the whole
 * ID determination process is considered to have failed.
 *
 * @author Oliver Richers
 */
class DomAutoCompleteIdMapFactory<T> {

	private final Map<String, T> stringToValueMap;

	public DomAutoCompleteIdMapFactory(Map<String, T> stringToValueMap) {

		this.stringToValueMap = Objects.requireNonNull(stringToValueMap);
	}

	/**
	 * Tries to determine a consistent ID-mapping for the values.
	 *
	 * @return a {@link Map} which maps from ID to value if ID determination was
	 *         successful; {@link Optional#empty} otherwise
	 */
	public Optional<Map<Integer, T>> create() {

		var result = new TreeMap<Integer, T>();
		for (var entry: stringToValueMap.entrySet()) {
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
