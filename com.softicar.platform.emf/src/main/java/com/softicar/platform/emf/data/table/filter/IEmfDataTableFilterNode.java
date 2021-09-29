package com.softicar.platform.emf.data.table.filter;

import com.softicar.platform.emf.data.table.filter.nop.EmfDataTableNopFilter;

public interface IEmfDataTableFilterNode<R> extends IInputNode {

	/**
	 * Creates an {@link IEmfDataTableFilter} from the data the user entered to
	 * the node's input elements.
	 *
	 * @return An {@link IEmfDataTableFilter} created from the data the user
	 *         entered to the node's input elements. Returns a
	 *         {@link EmfDataTableNopFilter} instance for empty/undefined
	 *         filters. Never null.
	 */
	IEmfDataTableFilter<R> createFilter();
}
