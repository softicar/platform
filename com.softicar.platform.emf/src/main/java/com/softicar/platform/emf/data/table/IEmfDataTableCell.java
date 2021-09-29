package com.softicar.platform.emf.data.table;

import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;

/**
 * Represents a cell of a result row of an {@link IEmfDataTable}.
 * 
 * @author Oliver Richers
 */
public interface IEmfDataTableCell<R, T> extends IDomParentElement {

	/**
	 * Returns the table row that this cell is a child of.
	 *
	 * @return the table row (never null)
	 */
	IEmfDataTableRow<R> getTableRow();

	/**
	 * Returns the table column that this cell is a part of.
	 *
	 * @return the table column (never null)
	 */
	IEmfDataTableColumn<R, T> getColumn();
}
