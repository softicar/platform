package com.softicar.platform.emf.collection;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.record.IDbRecord;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @param <C>
 *            the collection table
 * @param <I>
 *            the collection item table
 * @param <E>
 *            the element table
 * @param <CE>
 *            a collection of entity instances
 * @param <IP>
 *            the primary key of the entity collection item table
 */
public abstract class AbstractEmfEntityCollectionManager<C extends IDbObject<C>, I extends IDbRecord<I, IP>, E extends IDbEntity<E, ?>, CE extends Collection<E>, IP>
		implements IEmfEntityCollectionManager<C, E, CE> {

	private final Supplier<C> factory;
	private final IDbField<C, byte[]> hashField;
	private final IDbRecordTable<I, IP> itemTable;
	private final IDbForeignField<I, C> itemCollectionField;
	private final IDbForeignRowField<I, E, ?> itemElementField;
	private final Optional<IDbIntegerField<I>> itemIndexField;
	private final Collector<E, ?, CE> collector;
	private final IEmfEntityCollectionItemFetcher<C, I, E, IP> itemFetcher;

	public AbstractEmfEntityCollectionManager(Supplier<C> factory, IDbField<C, byte[]> hashField, IDbRecordTable<I, IP> itemTable,
			IDbForeignField<I, C> itemCollectionField, IDbForeignRowField<I, E, ?> itemElementField, Optional<IDbIntegerField<I>> itemIndexField,
			Collector<E, ?, CE> collector, IEmfEntityCollectionItemFetcher<C, I, E, IP> itemFetcher) {

		this.factory = factory;
		this.hashField = hashField;
		this.itemTable = itemTable;
		this.itemCollectionField = itemCollectionField;
		this.itemElementField = itemElementField;
		this.itemIndexField = itemIndexField;
		this.collector = collector;
		this.itemFetcher = itemFetcher;
	}

	@Override
	public CE loadElements(C collection) {

		ISqlSelect<I> query = itemTable//
			.createSelect()
			.where(itemCollectionField.isEqual(collection));
		itemIndexField.ifPresent(query::orderBy);
		return query//
			.stream()
			.map(itemElementField::getValue)
			.collect(collector);
	}

	@Override
	public void prefetchElements(Collection<C> collections) {

		collections = collections//
			.stream()
			.filter(element -> element != null)
			.collect(Collectors.toSet());

		List<I> items = itemTable//
			.createSelect()
			.where(itemCollectionField.isIn(collections))
			.list();

		itemElementField.prefetch(items);

		Map<C, List<I>> itemsByCollection = items//
			.stream()
			.collect(Collectors.groupingBy(itemCollectionField::getValue));

		for (Entry<C, List<I>> entry: itemsByCollection.entrySet()) {
			CE elements = entry//
				.getValue()
				.stream()
				.map(itemElementField::getValue)
				.collect(collector);
			setPrefetchedElements(entry.getKey(), elements);
		}
	}

	@Override
	public C getOrInsert(CE elements) {

		byte[] hash = computeHash(elements);
		C collection = getCollectionByHash(hash);
		if (collection != null) {
			return collection;
		} else {
			return insertNewCollection(elements, hash);
		}
	}

	protected abstract void setPrefetchedElements(C collection, CE elements);

	private C getCollectionByHash(byte[] hash) {

		return hashField//
			.getTable()
			.createSelect()
			.where(hashField.isEqual(hash))
			.getOne();
	}

	private C insertNewCollection(CE elements, byte[] hash) {

		C collection = factory.get();
		hashField.setValue(collection, hash);
		try (DbTransaction transaction = new DbTransaction()) {
			// TODO support additional initialization of the collection record, e.g. to allow for entity collections
			// with a tenant field
			collection.save();
			List<I> items = new ArrayList<>();
			for (E element: elements) {
				int index = items.size();
				I item = itemFetcher.getOrCreateItem(itemTable, collection, element, index);
				if (itemIndexField.isPresent()) {
					itemIndexField.get().setValue(item, index);
				}
				items.add(item);
			}
			itemTable.saveAll(items);
			transaction.commit();
		}
		return collection;
	}

	private byte[] computeHash(CE elements) {

		String idString = elements//
			.stream()
			.map(IBasicItem::getId)
			.map(id -> id + "")
			.collect(Collectors.joining(","));
		return Hash.MD5.getHash(idString);
	}
}
