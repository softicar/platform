package com.softicar.platform.dom.elements.input.auto.entity;

import com.softicar.platform.common.container.derived.DerivedObject;
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
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DomAutoCompleteEntityInMemoryInputEngine<T extends IEntity> extends AbstractDomAutoCompleteTransactionalEntityInputEngine<T> {

	private final DerivedObject<Cache> cache;
	private Supplier<Collection<T>> loader;

	public DomAutoCompleteEntityInMemoryInputEngine() {

		this(Collections::emptyList);
	}

	public DomAutoCompleteEntityInMemoryInputEngine(Supplier<Collection<T>> loader) {

		this.cache = new DerivedObject<>(Cache::new);
		this.loader = loader;
	}

	public void setLoader(Supplier<Collection<T>> loader) {

		this.loader = loader;
		this.cache.invalidate();
	}

	public DomAutoCompleteEntityInMemoryInputEngine<T> addDependsOn(Object sourceObject) {

		cache.addDependsOn(sourceObject);
		return this;
	}

	public void invalidateCache() {

		cache.invalidate();
	}

	@Override
	public T getById(Integer id) {

		return cache.get().getItem(new ItemId(id));
	}

	@Override
	public Collection<T> findMatchingItems(String pattern, int fetchOffset, int fetchSize) {

		return new Sequence(fetchOffset, fetchSize).filter(item -> getNormalizedDisplayName(item).contains(pattern));
	}

	@Override
	public Optional<T> findPerfectMatch(String pattern) {

		Optional<T> perfectMatch = super.findPerfectMatch(pattern);
		if (perfectMatch.isPresent()) {
			return perfectMatch;
		} else {
			Sequence sequence = new Sequence(0, 2);
			List<T> items = sequence.filter(item -> getNormalizedDisplayName(item).equals(pattern));
			if (items.size() != 1) {
				items = sequence.filter(item -> getNormalizedDisplayNameWithoutId(item).equals(pattern));
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

	private class Sequence {

		private final int offset;
		private final int limit;

		public Sequence(int offset, int limit) {

			this.offset = offset;
			this.limit = limit;
		}

		public List<T> filter(Predicate<T> filterPredicate) {

			return cache//
				.get()
				.getItemList()
				.stream()
				.filter(filterPredicate)
				.skip(offset)
				.limit(limit)
				.collect(Collectors.toList());
		}
	}

	private class Cache {

		private final Map<ItemId, T> itemMap;
		private final List<T> itemList;

		public Cache() {

			this.itemMap = new TreeMap<>();
			this.itemList = new ArrayList<>();

			loader.get().forEach(this::addItem);
		}

		public void addItem(T item) {

			this.itemMap.put(item.getItemId(), item);
			this.itemList.add(item);
		}

		public T getItem(ItemId id) {

			return itemMap.get(id);
		}

		public List<T> getItemList() {

			return itemList;
		}
	}
}
