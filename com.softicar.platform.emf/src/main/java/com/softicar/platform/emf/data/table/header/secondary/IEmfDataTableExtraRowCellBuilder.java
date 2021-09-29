package com.softicar.platform.emf.data.table.header.secondary;

import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.emf.data.table.IEmfDataTable;
import com.softicar.platform.emf.data.table.IEmfDataTableRowProvider;

/**
 * Initializes a cell in an extra row (secondary header, or footer) of an
 * {@link IEmfDataTable}.
 * <p>
 * FIXME Misnomer: Should be called "...Initializer", because it does not
 * actually build/create a cell.
 *
 * @author Alexander Schmidt
 */
@FunctionalInterface
public interface IEmfDataTableExtraRowCellBuilder<R> {

	/**
	 * Initializes the given cell. That is, appends content and modifies style.
	 *
	 * @param cell
	 *            the cell to initialize (never null)
	 * @param rowProvider
	 *            an {@link IEmfDataTableRowProvider} for the
	 *            {@link IEmfDataTable} in which the cell is displayed (never
	 *            null)
	 */
	void buildCell(IDomParentElement cell, IEmfDataTableRowProvider<R> rowProvider);
}
