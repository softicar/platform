package com.softicar.platform.emf.data.table;

import com.softicar.platform.dom.parent.IDomParentElement;

/**
 * Represents an action cell of a result row of an {@link IEmfDataTable}.
 *
 * @author Oliver Richers
 */
public interface IEmfDataTableActionCell<R> extends IDomParentElement {

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

	/**
	 * Returns the {@link IDomParentElement} where the content for this cell
	 * should be appended to.
	 *
	 * @return the {@link IDomParentElement} (never null)
	 */
	IDomParentElement getContentContainer();

}
