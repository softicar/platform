package com.softicar.platform.db.runtime.cache;

import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.logic.DbTableRowLoader;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

class DbTableRowCacheRefresher<R extends IDbTableRow<R, P>, P> {

	private final IDbTable<R, P> table;
	private final DbTableRowCacheBurstSizeCalculator calculator;

	public DbTableRowCacheRefresher(IDbTable<R, P> table) {

		this(table, new DbTableRowCacheBurstSizeCalculator());
	}

	public DbTableRowCacheRefresher(IDbTable<R, P> table, DbTableRowCacheBurstSizeCalculator burstSizeCalculator) {

		this.table = Objects.requireNonNull(table);
		this.calculator = Objects.requireNonNull(burstSizeCalculator);
	}

	public void refresh(Collection<R> rows) {

		new DbTableRowLoader<>(table)//
			.addRows(getMoreRows(rows))
			.reload();
	}

	private Stream<R> getMoreRows(Collection<R> rows) {

		return Stream//
			.concat(rows.stream(), table.getCache().getAllValues().stream().filter(row -> !rows.contains(row)))
			.filter(Objects::nonNull)
			.filter(row -> row.stub() || row.invalidated())
			.limit(Math.max(calculator.calculateNextBurstSize(), rows.size()));
	}
}
