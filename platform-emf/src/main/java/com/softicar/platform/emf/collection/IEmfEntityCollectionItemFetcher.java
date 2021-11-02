package com.softicar.platform.emf.collection;

import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.record.IDbRecord;
import com.softicar.platform.db.runtime.record.IDbRecordTable;

public interface IEmfEntityCollectionItemFetcher<C extends IDbObject<C>, I extends IDbRecord<I, P>, E, P> {

	I getOrCreateItem(IDbRecordTable<I, P> itemTable, C collection, E element, Integer index);
}
