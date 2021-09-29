package com.softicar.platform.emf.data.table.column.handler.filtering;

import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterNode;

/**
 * A factory interface for {@link IEmfDataTableFilter}.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface IEmfDataTableColumnFilterNodeFactory<T> {

	/**
	 * Creates a new instance of {@link IEmfDataTableFilterNode} for the column.
	 * <p>
	 * The returned filter node will be embedded into a {@link DomPopup} with
	 * <i>apply</i> and <i>cancel</i> buttons, so there is no need to implement
	 * this manually. When the user hits the <i>apply</i> button, the method
	 * {@link IEmfDataTableFilterNode#createFilter()} is called to create a new
	 * filter object, which is then applied against the {@link IDataTable}.
	 *
	 * @param column
	 *            the table column to filter by
	 * @return the filter node (never null)
	 * @see IEmfDataTableFilterNode
	 */
	<R> IEmfDataTableFilterNode<R> createFilterNode(IEmfDataTableColumn<R, T> column);
}
