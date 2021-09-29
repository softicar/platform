package com.softicar.platform.db.runtime.table.utils;

import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.db.runtime.table.DbTable;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;

public class DbTableRowMap<P, R extends IDbTableRow<R, P>> extends TreeMap<P, R> {

	private static final long serialVersionUID = 1L;

	public DbTableRowMap(IDbTable<R, P> table) {

		super(table.getPrimaryKey());
	}

	public void add(R row) {

		put(row.pk(), row);
	}

	public void add(Pair<P, R> pair) {

		put(pair.getFirst(), pair.getSecond());
	}

	public DbTableRowMap<P, R> addAll(Map<P, R> other) {

		putAll(other);
		return this;
	}

	public static <P, R extends IDbTableRow<R, P>> Collector<R, DbTableRowMap<P, R>, DbTableRowMap<P, R>> rowCollector(DbTable<R, P> table) {

		return Collector.of(() -> new DbTableRowMap<>(table), DbTableRowMap::add, DbTableRowMap::addAll);
	}

	public static <P, R extends IDbTableRow<R, P>> Collector<Pair<P, R>, DbTableRowMap<P, R>, DbTableRowMap<P, R>> pairCollector(DbTable<R, P> table) {

		return Collector.of(() -> new DbTableRowMap<>(table), DbTableRowMap::add, DbTableRowMap::addAll);
	}
}
