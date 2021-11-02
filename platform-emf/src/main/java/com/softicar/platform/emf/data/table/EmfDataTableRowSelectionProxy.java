package com.softicar.platform.emf.data.table;

import com.softicar.platform.emf.data.table.row.selection.IEmfTableRowSelectionProxy;

class EmfDataTableRowSelectionProxy<R> implements IEmfTableRowSelectionProxy {

	private final EmfDataTable<R> table;

	public EmfDataTableRowSelectionProxy(EmfDataTable<R> table) {

		this.table = table;
	}

	@Override
	public void selectAllRowsOnPage() {

		table.getController().selectAllRowsOnPage();
	}

	@Override
	public void invertSelectionOfRowsOnPage() {

		table.getController().invertSelectionOfRowsOnPage();
	}

	@Override
	public void unselectAllRowsOnPage() {

		table.getController().unselectAllRowsOnPage();
	}

	@Override
	public void unselectAllRowsOfTable() {

		table.getController().unselectAllRowsOfTable();
	}

	@Override
	public long getNumSelectedRows() {

		return table.getController().getSelectedRows().size();
	}

	@Override
	public long getNumSelectedRowsOnCurrentPage() {

		return table.getDisplayedTableRows().stream().filter((it) -> it.isSelected()).count();
	}

	@Override
	public boolean isMultiRowSelection() {

		return table.getController().getRowSelectionMode() == EmfDataTableRowSelectionMode.MULTI;
	}
}
