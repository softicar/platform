package com.softicar.platform.emf.collection.set;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.record.IDbRecord;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.emf.attribute.field.foreign.entity.collection.set.EmfForeignEntitySetAttribute;
import com.softicar.platform.emf.collection.AbstractEmfEntityCollectionTable;
import com.softicar.platform.emf.collection.IEmfEntityCollectionItemFetcher;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class AbstractEmfEntitySetTable<C extends AbstractEmfEntitySet<C, E>, I extends IDbRecord<I, Tuple2<C, E>>, E extends IEmfEntity<E, ?>, S>
		extends AbstractEmfEntityCollectionTable<C, I, E, Set<E>, Tuple2<C, E>, S>
		implements IEmfEntitySetTable<C, E, S> {

	private final AbstractEmfEntitySetTable<C, I, E, S>.SetItemFetcher itemFetcher;

	public AbstractEmfEntitySetTable(IDbObjectTableBuilder<C> builder, IDbField<C, byte[]> hashField) {

		super(builder, hashField);
		this.itemFetcher = new SetItemFetcher();
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<C, Integer, S> configuration) {

		configuration.setAttributeFactory(this::createAttribute);
	}

	@Override
	protected final Optional<IDbIntegerField<I>> getItemIndexField() {

		return Optional.empty();
	}

	@Override
	public Collector<E, ?, Set<E>> getCollector() {

		return Collectors.toSet();
	}

	@Override
	protected IEmfEntityCollectionItemFetcher<C, I, E, Tuple2<C, E>> getItemFetcher() {

		return itemFetcher;
	}

	private class SetItemFetcher implements IEmfEntityCollectionItemFetcher<C, I, E, Tuple2<C, E>> {

		@Override
		public I getOrCreateItem(IDbRecordTable<I, Tuple2<C, E>> itemTable, C collection, E element, Integer index) {

			return itemTable.getOrCreate(new Tuple2<>(collection, element));
		}
	}

	private <R extends IEmfTableRow<R, ?>> EmfForeignEntitySetAttribute<R, C, E> createAttribute(IDbForeignRowField<R, C, Integer> field) {

		return new EmfForeignEntitySetAttribute<>(field, this);
	}
}
