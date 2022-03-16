package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.data.table.IDataTableFilter;

/**
 * @author Alexander Schmidt
 */
interface IInMemoryDataTableFilter<R> extends IDataTableFilter {

	boolean applyFilter(R row);
}
