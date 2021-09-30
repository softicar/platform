package com.softicar.platform.emf.collection;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.record.IDbRecord;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class EmfEntityCollectionManager<C extends AbstractEmfEntityCollection<C, E, EC>, I extends IDbRecord<I, IP>, E extends IEmfEntity<E, ?>, EC extends Collection<E>, IP>
		extends AbstractEmfEntityCollectionManager<C, I, E, EC, IP> {

	public EmfEntityCollectionManager(Supplier<C> factory, IDbField<C, byte[]> hashField, IDbRecordTable<I, IP> itemTable,
			IDbForeignField<I, C> itemCollectionField, IDbForeignRowField<I, E, ?> itemElementField, Optional<IDbIntegerField<I>> itemIndexField,
			Collector<E, ?, EC> collector, IEmfEntityCollectionItemFetcher<C, I, E, IP> itemFetcher) {

		super(factory, hashField, itemTable, itemCollectionField, itemElementField, itemIndexField, collector, itemFetcher);
	}

	@Override
	protected void setPrefetchedElements(C objectCollection, EC elements) {

		objectCollection.elements = elements;
	}
}
