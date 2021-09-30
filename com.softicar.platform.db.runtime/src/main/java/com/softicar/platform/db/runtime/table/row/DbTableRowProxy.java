package com.softicar.platform.db.runtime.table.row;

import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.function.Supplier;

/**
 * A proxy for an {@link IDbTableRow}.
 * <p>
 * This class can be used to exchange {@link IDbTableRow} instances between
 * different {@link Thread} instances.
 *
 * @author Oliver Richers
 */
public class DbTableRowProxy<R extends IDbTableRow<R, P>, P> implements Supplier<R> {

	private final IDbTable<R, P> table;
	private final P pk;

	public DbTableRowProxy(R row) {

		this.table = row.table();
		this.pk = row.pk();
	}

	@Override
	public R get() {

		return table.get(pk);
	}
}
