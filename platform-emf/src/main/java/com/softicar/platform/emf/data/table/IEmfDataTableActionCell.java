package com.softicar.platform.emf.data.table;

import com.softicar.platform.dom.elements.IDomCell;

/**
 * Represents an action cell of a result row of an {@link IEmfDataTable}.
 *
 * @author Oliver Richers
 */
public interface IEmfDataTableActionCell<R> extends IDomCell {

	/**
	 * Returns the table row that this cell is a child of.
	 *
	 * @return the table row (never null)
	 */
	IEmfDataTableRow<R> getTableRow();

	/**
	 * Returns the table that this cell is a child of.
	 *
	 * @return the table (never null)
	 */
	default IEmfDataTable<R> getTable() {

		return getTableRow().getTable();
	}
}
