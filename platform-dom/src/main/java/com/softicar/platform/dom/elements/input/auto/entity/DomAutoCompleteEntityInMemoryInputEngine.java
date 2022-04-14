package com.softicar.platform.dom.elements.input.auto.entity;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.common.core.transaction.ITransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DomAutoCompleteEntityInMemoryInputEngine<T extends IEntity> extends AbstractDomAutoCompleteTransactionalEntityInputEngine<T> {

	private final Map<ItemId, T> itemMap;
	private final List<T> itemList;

	public DomAutoCompleteEntityInMemoryInputEngine() {

		this(Collections.emptySet());
	}

	public DomAutoCompleteEntityInMemoryInputEngine(Collection<T> items) {

		this.itemMap = new TreeMap<>();
		this.itemList = new ArrayList<>();
		setItems(items);
	}

	@Override
	public T getById(Integer id) {

		return itemMap.get(new ItemId(id));
	}

	@Override
	public Collection<T> findMatchingItems(String pattern, int fetchOffset, int fetchSize) {

		return new ItemLoader(fetchOffset, fetchSize).load(item -> getNormalizedDisplayName(item).contains(pattern));
	}

	@Override
	public Optional<T> findPerfectMatch(String pattern) {

		Optional<T> perfectMatch = super.findPerfectMatch(pattern);
		if (perfectMatch.isPresent()) {
			return perfectMatch;
		} else {
			ItemLoader loader = new ItemLoader(0, 2);
			List<T> items = loader.load(item -> getNormalizedDisplayName(item).equals(pattern));
			if (items.size() != 1) {
				items = loader.load(item -> getNormalizedDisplayNameWithoutId(item).equals(pattern));
			}
			if (items.size() == 1) {
				return Optional.ofNullable(items.iterator().next());
			} else {
				return Optional.empty();
			}
		}
	}

	@Override
	public Collection<T> filterMatchingItems(String pattern, Collection<T> matchingItems) {

		return matchingItems//
			.stream()
			.filter(item -> matches(item, pattern))
			.collect(Collectors.toList());
	}

	@Override
	protected ITransaction createTransaction() {

		return ITransaction.noOperation();
	}

	public void setItems(Collection<T> items) {

		clearItems();
		items.forEach(this::addItem);
	}

	public Collection<T> getItems() {

		return Collections.unmodifiableCollection(itemList);
	}

	private void clearItems() {

		this.itemMap.clear();
		this.itemList.clear();
	}

	private void addItem(T item) {

		this.itemMap.put(item.getItemId(), item);
		this.itemList.add(item);
	}

	private String getNormalizedDisplayName(T item) {

		return getDisplayString(item).toString().toLowerCase();
	}

	private String getNormalizedDisplayNameWithoutId(T item) {

		return getDisplayStringWithoutId(item).toString().toLowerCase();
	}

	private boolean matches(T item, String pattern) {

		return Optional//
			.ofNullable(getDisplayStringWithoutId(item))
			.map(Object::toString)
			.orElse("")
			.toLowerCase()
			.contains(pattern.toLowerCase());
	}

	private class ItemLoader {

		private final int offset;
		private final int limit;

		public ItemLoader(int offset, int limit) {

			this.offset = offset;
			this.limit = limit;
		}

		public List<T> load(Predicate<T> filterPredicate) {

			return itemList//
				.stream()
				.filter(filterPredicate)
				.skip(offset)
				.limit(limit)
				.collect(Collectors.toList());
		}
	}
}
