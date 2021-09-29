package com.softicar.platform.emf.table;

import com.softicar.platform.common.container.data.table.IDataTable;

/**
 * Represents a wrapper for {@link IEmfTable}.
 * <p>
 * This wrapper is needed to bypass some problems resulting with generics, e.g.
 * working with {@link IDataTable}.
 *
 * @author Daniel Klose
 */
public class EmfTableWrapper {

	private final IEmfTable<?, ?, ?> table;

	public EmfTableWrapper(IEmfTable<?, ?, ?> table) {

		this.table = table;
	}

	public IEmfTable<?, ?, ?> getTable() {

		return table;
	}
}
