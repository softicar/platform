package com.softicar.platform.dom.elements.input.auto;

import com.google.common.collect.Maps;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.normalizer.CurrentDiacriticNormalizationCache;
import com.softicar.platform.common.string.normalizer.DiacriticNormalizationCache;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Ensures that each value of a {@link DomAutoCompleteInput} has a unique
 * display string.
 * <p>
 * If two values translate to equivalent display strings, a deduplication is
 * performed, by appending numeric suffixes to the respective display strings,
 * e.g. "Foo (1)", "Foo (2)", etc.
 * <p>
 * Comparison between display strings is case-insensitive and
 * diacritic-agnostic, meaning that display strings only varying in letter cases
 * and/or diacritics are also deduplicated, e.g. "foo" and "Foo", or "foo" and
 * "fôo". However, letter cases and diacritics are retained when suffixes are
 * added, resulting in "foo (1)", "Foo (2)" and "fôo (3)".
 * <p>
 * This class does not perform any deduplication of values. If necessary, such a
 * removal of redundant values should be performed elsewhere.
 *
 * @author Oliver Richers
 */
public class DomAutoCompleteDisplayStringDeduplicator<T> {

	private final Function<T, IDisplayString> displayFunction;
	private final Comparator<T> valueComparator;
	private final DiacriticNormalizationCache normalizer;
//	private final DiacriticNormalizer normalizer;
	private Map<String, List<ValueMapping>> listMap;
	private Map<String, T> resultMap;

	public DomAutoCompleteDisplayStringDeduplicator(Function<T, IDisplayString> displayFunction, Comparator<T> valueComparator) {

		this.displayFunction = displayFunction;
		this.valueComparator = valueComparator;
		this.normalizer = CurrentDiacriticNormalizationCache.get();
//		this.normalizer = new DiacriticNormalizer();
	}

	public Map<String, T> apply(Collection<T> values) {

		// compute value to display-string pairs
		var valueMappings = values//
			.stream()
			.map(ValueMapping::new)
			.collect(Collectors.toList());

		// normalization
		this.listMap = valueMappings//
			.stream()
//			.parallelStream()
			.map(ValueMapping::computeNormalizedDisplayString)
			.collect(Collectors.groupingBy(it -> it.getNormalizedDisplayString()));

		// de-duplication
		this.resultMap = Maps.newHashMapWithExpectedSize(values.size());
		for (var list: listMap.values()) {
			if (list.size() > 1) {
				addConflictingValues(list);
			} else {
				var valueMapping = list.get(0);
				addValue(valueMapping.getDisplayString(), valueMapping.getValue());
			}
		}
		return resultMap;
	}

	private void addConflictingValues(List<ValueMapping> valueMappings) {

		Collections.sort(valueMappings, (a, b) -> valueComparator.compare(a.getValue(), b.getValue()));

		var index = 1;
		for (var valueMapping: valueMappings) {
			for (;; index++) {
				var substitute = valueMapping.getNormalizedDisplayString(index);
				if (!listMap.containsKey(substitute)) {
					addValue(valueMapping.getDisplayString(index), valueMapping.getValue());
					index++;
					break;
				}
			}
		}
	}

	private void addValue(String key, T value) {

		resultMap.put(key, value);
	}

	private class ValueMapping {

		private final T value;
		private final String displayString;
		private String normalizedDisplayString;

		public ValueMapping(T value) {

			this.value = value;
			this.displayString = displayFunction.apply(value).toString();
			this.normalizedDisplayString = null;
		}

		public T getValue() {

			return value;
		}

		public String getDisplayString() {

			return displayString;
		}

		public String getDisplayString(int index) {

			return displayString + getIndexSuffix(index);
		}

		public String getNormalizedDisplayString() {

			return normalizedDisplayString;
		}

		public String getNormalizedDisplayString(int index) {

			return normalizedDisplayString + getIndexSuffix(index);
		}

		public ValueMapping computeNormalizedDisplayString() {

			this.normalizedDisplayString = normalizer.normalize(displayString).toLowerCase();
			return this;
		}

		private String getIndexSuffix(int index) {

			return " (" + index + ")";
		}
	}
}
