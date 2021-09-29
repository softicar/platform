package com.softicar.platform.db.runtime.record;

import com.softicar.platform.db.runtime.table.DbTable;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import java.util.Collection;
import java.util.Map;

public class DbRecordTable<R extends IDbRecord<R, P>, P> extends DbTable<R, P> implements IDbRecordTable<R, P> {

	public DbRecordTable(IDbTableBuilder<R, P> builder) {

		super(builder);
	}

	@Override
	public R getOrCreate(P key) {

		R record = get(key);
		if (record != null) {
			return record;
		} else {
			return getRecordFromCacheOrCreateNewRecord(key);
		}
	}

	@Override
	public Map<P, R> getOrCreateAsMap(Collection<P> keys) {

		Map<P, R> recordMap = getAllAsMap(keys);
		for (P key: keys) {
			if (!recordMap.containsKey(key)) {
				recordMap.put(key, getRecordFromCacheOrCreateNewRecord(key));
			}
		}
		return recordMap;
	}

	private R getRecordFromCacheOrCreateNewRecord(P primaryKey) {

		R record = getCache().getSimple(primaryKey);
		if (record != null) {
			return record;
		} else {
			return createRecordAndPutIntoCache(primaryKey);
		}
	}

	private R createRecordAndPutIntoCache(P primaryKey) {

		R record = getRowFactory()//
			.get()
			.initializer()
			.initializeToDefaults(primaryKey);
		getCache().put(primaryKey, record);
		return record;
	}
}
