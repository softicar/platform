package com.softicar.platform.db.runtime.table.utils;

import com.softicar.platform.db.runtime.table.DbTable;
import java.util.TreeSet;
import java.util.stream.Collector;

public class DbTableKeySet<P> extends TreeSet<P> {

	private static final long serialVersionUID = 1L;

	public DbTableKeySet(DbTable<?, P> table) {

		super(table.getPrimaryKey());
	}

	public DbTableKeySet<P> addAll(DbTableKeySet<P> other) {

		this.addAll(other);
		return this;
	}

	public static <P> Collector<P, DbTableKeySet<P>, DbTableKeySet<P>> collector(DbTable<?, P> table) {

		return Collector.of(() -> new DbTableKeySet<>(table), DbTableKeySet::add, DbTableKeySet::addAll);
	}
}
