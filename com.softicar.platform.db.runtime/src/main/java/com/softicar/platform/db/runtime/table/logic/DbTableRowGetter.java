package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.table.DbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.runtime.table.utils.DbTableKeySet;
import com.softicar.platform.db.runtime.table.utils.DbTableRowMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DbTableRowGetter<R extends IDbTableRow<R, P>, P> {

	private final DbTable<R, P> table;
	private final DbTableKeySet<P> keys;

	public DbTableRowGetter(DbTable<R, P> table, P primaryKey) {

		this(table, Collections.singleton(primaryKey));
	}

	public DbTableRowGetter(DbTable<R, P> table, Collection<P> keys) {

		this.table = table;
		this.keys = keys//
			.stream()
			.filter(key -> key != null)
			.collect(DbTableKeySet.collector(table));
	}

	public List<R> getAsList() {

		return getAsCollection(ArrayList::new);
	}

	public <C extends Collection<R>> C getAsCollection(Supplier<C> collectionFactory) {

		return getAsMap()//
			.values()
			.stream()
			.collect(Collectors.toCollection(collectionFactory));
	}

	public Map<P, R> getAsMap() {

		DbTableRowMap<P, R> resultMap = new DbTableRowMap<>(table);
		fetchRowsFromCache(resultMap);
		fetchUncachedRows(resultMap);
		return resultMap;
	}

	private void fetchRowsFromCache(DbTableRowMap<P, R> resultMap) {

		keys//
			.stream()
			.map(key -> table.getCache().getSimple(key))
			.filter(row -> !mustBeLoaded(row))
			.forEach(resultMap::add);
	}

	private boolean mustBeLoaded(R row) {

		return row == null || row.stub() || row.invalidated();
	}

	private void fetchUncachedRows(Map<P, R> resultMap) {

		DbTableKeySet<P> remainingKeys = getRemainingKeys(resultMap);
		if (!remainingKeys.isEmpty()) {
			table//
				.getConfiguration()
				.getTableRowGetterStrategy()
				.loadRowsByPrimaryKey(remainingKeys)
				.forEach((key, row) -> resultMap.put(key, row));
		}
	}

	private DbTableKeySet<P> getRemainingKeys(Map<P, R> resultMap) {

		return keys//
			.stream()
			.filter(key -> !resultMap.containsKey(key))
			.collect(DbTableKeySet.collector(table));
	}
}
