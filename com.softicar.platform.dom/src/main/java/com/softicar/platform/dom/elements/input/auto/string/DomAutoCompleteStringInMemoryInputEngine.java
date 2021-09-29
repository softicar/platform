package com.softicar.platform.dom.elements.input.auto.string;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DomAutoCompleteStringInMemoryInputEngine extends AbstractDomAutoCompleteStringInputEngine {

	private final Map<String, String> items;

	public DomAutoCompleteStringInMemoryInputEngine() {

		this(Collections.emptySet());
	}

	public DomAutoCompleteStringInMemoryInputEngine(Collection<String> items) {

		this.items = new TreeMap<>();
		items.forEach(this::addItem);
	}

	@Override
	public Collection<String> findMatchingItems(String pattern, int fetchOffset, int fetchSize) {

		return new ItemLoader(fetchOffset, fetchSize).load(item -> getNormalizedDisplayName(item).contains(pattern));
	}

	@Override
	public Optional<String> findPerfectMatch(String pattern) {

		List<String> items = new ItemLoader(0, 2).load(item -> getNormalizedDisplayName(item).equals(pattern));
		if (items.size() == 1) {
			return Optional.of(items.iterator().next());
		} else {
			return Optional.empty();
		}
	}

	public void addItem(String item) {

		addItem(item, null);
	}

	public void addItem(String item, String description) {

		items.put(item, description);
	}

	public void setItems(Collection<String> items) {

		this.items.clear();
		items.forEach(this::addItem);
	}

	public void setItems(Map<String, String> items) {

		this.items.clear();
		this.items.putAll(items);
	}

	public Map<String, String> getItems() {

		return Collections.unmodifiableMap(items);
	}

	private String getNormalizedDisplayName(String item) {

		return getDisplayString(item).toString().toLowerCase();
	}

	private class ItemLoader {

		private final int offset;
		private final int limit;

		public ItemLoader(int offset, int limit) {

			this.offset = offset;
			this.limit = limit;
		}

		public List<String> load(Predicate<String> filterPredicate) {

			return items//
				.keySet()
				.stream()
				.filter(filterPredicate)
				.skip(offset)
				.limit(limit)
				.collect(Collectors.toList());
		}
	}
}
