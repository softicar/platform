package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * Ensures that each auto-complete element has a unique display string.
 * <p>
 * If two auto-complete elements translate to the same display string, a
 * deduplication is performed, by appending numeric suffixes to the respective
 * display strings, e.g. "Foo (1)", "Foo (2)", etc.
 * <p>
 * Comparison between display strings is case-insensitive, meaning that display
 * strings only varying in letter cases are also deduplicated, e.g. "foo" and
 * "Foo". However, letter cases are retained when suffixes are added, resulting
 * in "foo (1)" and "Foo (2)".
 * <p>
 * This class does not perform any deduplication of elements. If necessary, such
 * a removal of redundant elements should be performed elsewhere.
 *
 * @author Oliver Richers
 */
class DomAutoCompleteDisplayStringDeduplicator<T> {

	private final Function<T, IDisplayString> displayFunction;
	private final Comparator<T> comparator;
	private Map<String, List<T>> listMap;
	private Map<String, T> resultMap;

	public DomAutoCompleteDisplayStringDeduplicator(Function<T, IDisplayString> displayFunction, Comparator<T> comparator) {

		this.displayFunction = displayFunction;
		this.comparator = comparator;
	}

	public Map<String, T> apply(Collection<T> elements) {

		this.listMap = new TreeMap<>(String::compareToIgnoreCase);
		this.resultMap = new TreeMap<>(String::compareToIgnoreCase);

		for (var element: elements) {
			var string = displayFunction.apply(element).toString();
			listMap.computeIfAbsent(string, dummy -> new ArrayList<>()).add(element);
		}

		for (var entry: listMap.entrySet()) {
			var key = entry.getKey();
			var list = entry.getValue();
			if (list.size() > 1) {
				addConflictingElements(list);
			} else {
				addElement(key, list.get(0));
			}
		}

		return resultMap;
	}

	private void addConflictingElements(List<T> elements) {

		Collections.sort(elements, comparator);

		var index = 1;
		for (var element: elements) {
			for (;; index++) {
				var substituteKey = displayFunction.apply(element) + " (" + index + ")";
				if (!listMap.containsKey(substituteKey)) {
					addElement(substituteKey, element);
					index++;
					break;
				}
			}
		}
	}

	private void addElement(String key, T element) {

		resultMap.put(key, element);
	}
}
