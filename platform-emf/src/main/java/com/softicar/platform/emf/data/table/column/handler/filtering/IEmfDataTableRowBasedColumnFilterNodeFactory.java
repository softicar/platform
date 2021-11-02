package com.softicar.platform.emf.data.table.column.handler.filtering;

import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterNode;

/**
 * A factory interface for {@link IEmfDataTableFilter}.
 * <p>
 * This interface is similar to {@link IEmfDataTableColumnFilterNodeFactory} but
 * requires an explicit type parameter which represents the table row. The
 * advantage is that the filter may apply filtering to all columns of the table.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface IEmfDataTableRowBasedColumnFilterNodeFactory<R> {

	/**
	 * Creates a new instance of {@link IEmfDataTableFilterNode} for the column.
	 * <p>
	 * The returned filter node will be embedded into a {@link DomPopup} with
	 * <i>apply</i> and <i>cancel</i> buttons, so there is no need to implement
	 * this manually. When the user hits the <i>apply</i> button, the method
	 * {@link IEmfDataTableFilterNode#createFilter()} is called to create a new
	 * filter object, which is then applied against the {@link IDataTable}.
	 *
	 * @return the filter node (never null)
	 * @see IEmfDataTableFilterNode
	 */
	IEmfDataTableFilterNode<R> createFilterNode();
}
