package com.softicar.platform.dom.elements.input.auto;

import com.google.common.collect.Maps;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.normalizer.CurrentDiacriticNormalizationCache;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

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
class DomAutoCompleteDisplayStringDeduplicator<T> {

	private final Function<T, IDisplayString> displayFunction;
	private final Comparator<T> valueComparator;
	private final Comparator<String> keyComparator;
	private Map<String, List<T>> listMap;
	private Map<String, T> resultMap;

	public DomAutoCompleteDisplayStringDeduplicator(Function<T, IDisplayString> displayFunction, Comparator<T> valueComparator) {

		this.displayFunction = displayFunction;
		this.valueComparator = valueComparator;
		this.keyComparator = Comparator.comparing(string -> CurrentDiacriticNormalizationCache.get().normalize(string).toLowerCase());
	}

	public Map<String, T> apply(Collection<T> values) {

		this.listMap = new TreeMap<>(keyComparator);
		this.resultMap = Maps.newHashMapWithExpectedSize(values.size());

		for (var value: values) {
			var string = displayFunction.apply(value).toString();
			listMap.computeIfAbsent(string, dummy -> new ArrayList<>()).add(value);
		}

		for (var entry: listMap.entrySet()) {
			var key = entry.getKey();
			var list = entry.getValue();
			if (list.size() > 1) {
				addConflictingValues(list);
			} else {
				addValue(key, list.get(0));
			}
		}

		return resultMap;
	}

	private void addConflictingValues(List<T> values) {

		Collections.sort(values, valueComparator);

		var index = 1;
		for (var value: values) {
			for (;; index++) {
				var substituteKey = displayFunction.apply(value) + " (" + index + ")";
				if (!listMap.containsKey(substituteKey)) {
					addValue(substituteKey, value);
					index++;
					break;
				}
			}
		}
	}

	private void addValue(String key, T value) {

		resultMap.put(key, value);
	}
}
