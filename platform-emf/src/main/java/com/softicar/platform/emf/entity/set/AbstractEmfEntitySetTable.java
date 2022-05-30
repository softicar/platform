package com.softicar.platform.emf.entity.set;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.record.IDbRecord;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.emf.attribute.field.foreign.entity.set.EmfForeignEntitySetAttribute;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractEmfEntitySetTable<ES extends AbstractEmfEntitySet<ES, E>, I extends IDbRecord<I, Tuple2<ES, E>>, E extends IEmfEntity<E, ?>, S>
		extends EmfObjectTable<ES, S>
		implements IEmfEntitySetTable<ES, E, S> {

	private final IDbField<ES, byte[]> hashField;
	private final IDbRecordTable<I, Tuple2<ES, E>> setItemTable;
	private final IDbForeignField<I, ES> entitySetField;
	private final IDbForeignRowField<I, E, ?> entitySetElementField;

	public AbstractEmfEntitySetTable(IDbObjectTableBuilder<ES> builder, IDbRecordTable<I, Tuple2<ES, E>> setItemTable, IDbForeignField<I, ES> entitySetField,
			IDbForeignRowField<I, E, ?> entitySetElementField, IDbField<ES, byte[]> hashField) {

		super(builder);
		this.hashField = hashField;
		this.setItemTable = setItemTable;
		this.entitySetField = entitySetField;
		this.entitySetElementField = entitySetElementField;
	}

	@Override
	public ES getOrInsert(Set<E> elements) {

		byte[] hash = computeHash(elements);
		ES entitySet = getEntitySetByHash(hash);
		if (entitySet != null) {
			return entitySet;
		} else {
			return insertEntitySet(elements, hash);
		}
	}

	@Override
	public void prefetchElements(Collection<ES> entitySets) {

		entitySets = entitySets//
			.stream()
			.filter(Objects::nonNull)
			.collect(Collectors.toSet());

		List<I> items = setItemTable//
			.createSelect()
			.where(entitySetField.isIn(entitySets))
			.list();

		entitySetElementField.prefetch(items);

		Map<ES, List<I>> itemsByEntitySet = items//
			.stream()
			.collect(Collectors.groupingBy(entitySetField::getValue));

		for (Entry<ES, List<I>> entry: itemsByEntitySet.entrySet()) {
			Set<E> elements = entry//
				.getValue()
				.stream()
				.map(entitySetElementField::getValue)
				.collect(Collectors.toSet());
			entry.getKey().setElements(elements);
		}
	}

	@Override
	public Set<E> loadElements(ES entitySet) {

		return setItemTable//
			.createSelect()
			.where(entitySetField.isEqual(entitySet))
			.stream()
			.map(entitySetElementField::getValue)
			.collect(Collectors.toSet());
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<ES, Integer, S> configuration) {

		configuration.setAttributeFactory(this::createAttribute);
	}

	private <R extends IEmfTableRow<R, ?>> EmfForeignEntitySetAttribute<R, ES, E> createAttribute(IDbForeignRowField<R, ES, Integer> field) {

		return new EmfForeignEntitySetAttribute<>(field, this);
	}

	private byte[] computeHash(Set<E> elements) {

		String idString = elements//
			.stream()
			.map(IBasicItem::getId)
			.map(id -> id + "")
			.collect(Collectors.joining(","));
		return Hash.MD5.getHash(idString);
	}

	private ES getEntitySetByHash(byte[] hash) {

		return hashField//
			.getTable()
			.createSelect()
			.where(hashField.isEqual(hash))
			.getOne();
	}

	private ES insertEntitySet(Set<E> elements, byte[] hash) {

		ES entitySet = createObject();
		hashField.setValue(entitySet, hash);
		try (DbTransaction transaction = new DbTransaction()) {
			entitySet.save();
			List<I> items = new ArrayList<>();
			for (E element: elements) {
				items.add(getOrCreateItem(entitySet, element));
			}
			setItemTable.saveAll(items);
			transaction.commit();
		}
		return entitySet;
	}

	private I getOrCreateItem(ES entitySet, E element) {

		return setItemTable.getOrCreate(new Tuple2<>(entitySet, element));
	}
}
