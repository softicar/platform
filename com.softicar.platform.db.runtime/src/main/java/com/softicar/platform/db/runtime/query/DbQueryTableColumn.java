package com.softicar.platform.db.runtime.query;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.function.Function;

/**
 * Default implementation of {@link IDbQueryTableColumn}.
 *
 * @author Oliver Richers
 */
public class DbQueryTableColumn<R extends IDbQueryRow<R>, T> extends DbQueryColumn<R, T> implements IDbQueryTableColumn<R, T> {

	private final IDbTable<T, ?> table;

	public DbQueryTableColumn(Function<R, T> valueGetter, String name, IDbTable<T, ?> table, IDisplayString title) {

		super(valueGetter, name, table, title);

		this.table = table;
	}

	public DbQueryTableColumn(Function<R, T> valueGetter, String name, IDbTable<T, ?> table) {

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

		return false;
	}
}
