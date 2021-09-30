package com.softicar.platform.emf.collection;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.record.IDbRecord;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import java.util.Collection;
import java.util.Optional;

public abstract class AbstractEmfEntityCollectionTable<C extends AbstractEmfEntityCollection<C, E, EC>, I extends IDbRecord<I, IP>, E extends IEmfEntity<E, ?>, EC extends Collection<E>, IP, S>
		extends EmfObjectTable<C, S>
		implements IEmfEntityCollectionTable<C, E, EC, S> {

	private final IDbField<C, byte[]> hashField;

	public AbstractEmfEntityCollectionTable(IDbObjectTableBuilder<C> builder, IDbField<C, byte[]> hashField) {

		super(builder);
		this.hashField = hashField;
	}

	protected abstract IDbRecordTable<I, IP> getItemTable();

	protected abstract IDbForeignField<I, C> getItemSetField();

	protected abstract IDbForeignRowField<I, E, ?> getItemElementField();

	protected abstract Optional<IDbIntegerField<I>> getItemIndexField();

	@Override
	public C getOrInsert(EC elements) {

		return createManager().getOrInsert(elements);
	}

	@Override
	public void prefetchElements(Collection<C> collections) {

		createManager().prefetchElements(collections);
	}

	@Override
	public EC loadElements(C collection) {

		return createManager().loadElements(collection);
	}

	/**
	 * Returns the element table.
	 * <p>
	 * Please note that this needs to be abstract to support cyclic references
	 * between element tables and referencing tables.
	 */
	@Override
	public abstract IEmfEntityTable<E, ?, ?> getElementTable();

	protected abstract IEmfEntityCollectionItemFetcher<C, I, E, IP> getItemFetcher();

	private EmfEntityCollectionManager<C, I, E, EC, IP> createManager() {

		return new EmfEntityCollectionManager<>(//
			this::createObject,
			hashField,
			getItemTable(),
			getItemSetField(),
			getItemElementField(),
			getItemIndexField(),
			getCollector(),
			getItemFetcher());
	}
}
