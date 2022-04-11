package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.List;

public class EmfImportCsvFileTable<R extends IEmfTableRow<R, P>, P> extends AbstractInMemoryDataTable<List<String>> {

	private final List<List<String>> tableRows;

	public EmfImportCsvFileTable(EmfImportEngine<R, P, ?> engine, List<List<String>> tableRows) {

		this.tableRows = tableRows;

		var index = 0;
		for (EmfImportColumn<R, ?> column: engine.getCsvFileColumnsToImport()) {
			addColumn(column, index);
			index++;
		}
	}

	@Override
	protected Collection<List<String>> getTableRows() {

		return tableRows;
	}

	private void addColumn(EmfImportColumn<R, ?> column, int index) {

		newColumn(String.class)//
			.setGetter(row -> row.get(index))
			.setTitle(column.getTitle())
			.addColumn();
	}
}
