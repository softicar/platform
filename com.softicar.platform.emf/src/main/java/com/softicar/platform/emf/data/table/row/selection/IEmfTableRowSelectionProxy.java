package com.softicar.platform.emf.data.table.row.selection;

import com.softicar.platform.dom.elements.DomTable;

/**
 * Row selection controller interface for {@link DomTable}.
 * <p>
 * This interface is used by {@link EmfTableRowSelectionControlElement}.
 *
 * @author Alexander Schmidt
 */
public interface IEmfTableRowSelectionProxy {

	// ---------------- CONTROLS ---------------- //

	void selectAllRowsOnPage();

	void invertSelectionOfRowsOnPage();

	void unselectAllRowsOnPage();

	void unselectAllRowsOfTable();

	// ---------------- STATS ---------------- //

	long getNumSelectedRows();

	long getNumSelectedRowsOnCurrentPage();

	// ---------------- META ---------------- //

	boolean isMultiRowSelection();
}
