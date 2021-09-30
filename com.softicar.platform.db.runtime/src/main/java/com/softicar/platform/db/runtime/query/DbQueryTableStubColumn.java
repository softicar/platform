package com.softicar.platform.db.runtime.query;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.Collection;
import java.util.function.Function;

/**
 * Default implementation {@link IDbQueryTableColumn} for stubs.
 * <p>
 * A stub column only loads the primary key of the target record. The full
 * record is only loaded when necessary.
 *
 * @author Oliver Richers
 */
public class DbQueryTableStubColumn<R extends IDbQueryRow<R>, T> extends DbQueryColumn<R, T> implements IDbQueryTableColumn<R, T> {

	private final IDbTable<T, ?> table;

	public DbQueryTableStubColumn(Function<R, T> valueGetter, String name, IDbTable<T, ?> table, IDisplayString title) {

		super(valueGetter, name, new DbQueryStubValueType<>(table), title);

		this.table = table;
	}

	public DbQueryTableStubColumn(Function<R, T> valueGetter, String name, IDbTable<T, ?> table) {

		this(valueGetter, name, table, null);
	}

	@Override
	public boolean isTable() {

		return true;
	}

	@Override
	public IDbTable<T, ?> getTable() {

		return table;
	}

	@Override
	public boolean isStub() {

		return true;
	}

	@Override
	public void prefetchData(Collection<T> values) {

		table.unstubAll(values);
	}
}
