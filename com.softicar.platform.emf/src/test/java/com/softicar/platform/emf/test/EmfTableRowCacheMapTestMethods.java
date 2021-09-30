package com.softicar.platform.emf.test;

import com.softicar.platform.db.runtime.cache.CurrentDbTableRowCacheMap;
import com.softicar.platform.db.runtime.cache.DbTableRowCacheMap;
import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.db.runtime.entity.IDbEntityTable;
import java.util.function.Consumer;

// TODO get rid of this; it should not be necessary
public interface EmfTableRowCacheMapTestMethods {

	static <E extends IDbEntity<E, P>, P> void useOtherCacheMapAndModify(E object, Consumer<E> modifier) {

		DbTableRowCacheMap originalCacheMap = CurrentDbTableRowCacheMap.get();

		IDbEntityTable<E, P> table = object.table();
		P primaryKay = object.pk();

		try {
			CurrentDbTableRowCacheMap.set(null);

			E otherObject = table.get(primaryKay);
			modifier.accept(otherObject);
			otherObject.save();
		} finally {
			CurrentDbTableRowCacheMap.set(originalCacheMap);
		}
	}
}
