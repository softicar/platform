package com.softicar.platform.emf.data.table;

@FunctionalInterface
public interface IEmfDataTableRowSelectionCallback<R> {

	void handleRowSelectionChange(IEmfDataTable<R> dataTable);
}
