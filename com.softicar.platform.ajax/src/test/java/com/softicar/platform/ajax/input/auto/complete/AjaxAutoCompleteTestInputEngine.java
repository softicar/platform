package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.thread.Locker;
import com.softicar.platform.dom.elements.input.auto.AbstractDomAutoCompletePreferenceInputEngine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AjaxAutoCompleteTestInputEngine extends AbstractDomAutoCompletePreferenceInputEngine<AjaxAutoCompleteTestItem> {

	private final Collection<AjaxAutoCompleteTestItem> items;
	private final Lock lock;
	private Optional<Consumer<String>> requestListener;

	public AjaxAutoCompleteTestInputEngine(AjaxAutoCompleteTestItem...items) {

		this.items = new ArrayList<>();
		this.items.addAll(Arrays.asList(items));
		this.lock = new ReentrantLock();
		this.requestListener = Optional.empty();
	}

	@Override
	public IDisplayString getDisplayString(AjaxAutoCompleteTestItem item) {

		return IDisplayString.create(item.getName());
	}

	@Override
	public String getDescription(AjaxAutoCompleteTestItem item) {

		return item.getDescription();
	}

	@Override
	public Collection<AjaxAutoCompleteTestItem> findMatches(String pattern, int limit) {

		try (Locker locker = lock()) {
			requestListener.ifPresent(listener -> listener.accept(pattern));
			return super.findMatches(pattern, limit);
		}
	}

	@Override
	public Collection<AjaxAutoCompleteTestItem> findMatchingItems(String pattern, int fetchOffset, int fetchSize) {

		return new ItemLoader(fetchOffset, fetchSize).load(item -> getNormalizedDisplayName(item).contains(pattern));
	}

	@Override
	public Optional<AjaxAutoCompleteTestItem> findPerfectMatch(String pattern) {

		List<AjaxAutoCompleteTestItem> items = new ItemLoader(0, 2).load(item -> getNormalizedDisplayName(item).equals(pattern));
		if (items.size() == 1) {
			return Optional.of(items.iterator().next());
		} else {
			return Optional.empty();
		}
	}

	private String getNormalizedDisplayName(AjaxAutoCompleteTestItem item) {

		return getDisplayString(item).toString().toLowerCase();
	}

	public AjaxAutoCompleteTestInputEngine clearItems() {

		items.clear();
		return this;
	}

	public AjaxAutoCompleteTestInputEngine addItem(AjaxAutoCompleteTestItem item) {

		items.add(item);
		return this;
	}

	public AjaxAutoCompleteTestInputEngine addItems(AjaxAutoCompleteTestItem...items) {

		return addItems(Arrays.asList(items));
	}

	public AjaxAutoCompleteTestInputEngine addItems(Collection<AjaxAutoCompleteTestItem> items) {

		items.forEach(this::addItem);
		return this;
	}

	public AjaxAutoCompleteTestInputEngine addStringItem(String name) {

		items.add(new AjaxAutoCompleteTestItem(name, ""));
		return this;
	}

	public AjaxAutoCompleteTestInputEngine addStringItems(Collection<String> items) {

		items.forEach(this::addStringItem);
		return this;
	}

	public AjaxAutoCompleteTestInputEngine setRequestListener(Consumer<String> requestListener) {

		this.requestListener = Optional.of(requestListener);
		return this;
	}

	public Locker lock() {

		return new Locker(lock);
	}

	private class ItemLoader {

		private final int offset;
		private final int limit;

		public ItemLoader(int offset, int limit) {

			this.offset = offset;
			this.limit = limit;
		}

		public List<AjaxAutoCompleteTestItem> load(Predicate<AjaxAutoCompleteTestItem> filterPredicate) {

			return items//
				.stream()
				.filter(filterPredicate)
				.skip(offset)
				.limit(limit)
				.collect(Collectors.toList());
		}
	}
}
