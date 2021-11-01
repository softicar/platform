package com.softicar.platform.emf.collection.list;

import com.softicar.platform.common.container.tuple.Tuple3;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.record.IDbRecord;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.emf.attribute.field.foreign.entity.collection.list.EmfForeignEntityListAttribute;
import com.softicar.platform.emf.collection.AbstractEmfEntityCollectionTable;
import com.softicar.platform.emf.collection.IEmfEntityCollectionItemFetcher;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class AbstractEmfEntityListTable<C extends AbstractEmfEntityList<C, E>, I extends IDbRecord<I, Tuple3<C, E, Integer>>, E extends IEmfEntity<E, ?>, S>
		extends AbstractEmfEntityCollectionTable<C, I, E, List<E>, Tuple3<C, E, Integer>, S>
		implements IEmfEntityListTable<C, E, S> {

	private final AbstractEmfEntityListTable<C, I, E, S>.ListItemFetcher itemFetcher;

	public AbstractEmfEntityListTable(IDbObjectTableBuilder<C> builder, IDbField<C, byte[]> hashField) {

		super(builder, hashField);
		this.itemFetcher = new ListItemFetcher();
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<C, Integer, S> configuration) {

		configuration.setAttributeFactory(this::createAttribute);
	}

	@Override
	public Collector<E, ?, List<E>> getCollector() {

		return Collectors.toList();
	}

	@Override
	protected IEmfEntityCollectionItemFetcher<C, I, E, Tuple3<C, E, Integer>> getItemFetcher() {

		return itemFetcher;
	}

	private class ListItemFetcher implements IEmfEntityCollectionItemFetcher<C, I, E, Tuple3<C, E, Integer>> {

		@Override
		public I getOrCreateItem(IDbRecordTable<I, Tuple3<C, E, Integer>> itemTable, C collection, E element, Integer index) {

			return itemTable.getOrCreate(new Tuple3<>(collection, element, index));
		}
	}

	private <R extends IEmfTableRow<R, ?>> EmfForeignEntityListAttribute<R, C, E> createAttribute(IDbForeignRowField<R, C, Integer> field) {

		return new EmfForeignEntityListAttribute<>(field, this);
	}
}
