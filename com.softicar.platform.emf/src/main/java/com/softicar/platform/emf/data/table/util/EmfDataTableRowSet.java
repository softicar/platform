package com.softicar.platform.emf.data.table.util;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EmfDataTableRowSet<R> {

	private final Collection<? extends IDataTableColumn<R, ?>> columns;
	private final Map<EmfDataTableRowKey<R>, R> rows;

	public EmfDataTableRowSet(Collection<? extends IDataTableColumn<R, ?>> columns) {

		this.columns = columns;
		this.rows = new HashMap<>();
	}

	public Collection<R> getRows() {

		return rows.values();
	}

	public void clear() {

		rows.clear();
	}

	public void addRow(R row) {

		rows.put(new EmfDataTableRowKey<>(columns, row), row);
	}

	public void removeRow(R row) {

		rows.remove(new EmfDataTableRowKey<>(columns, row));
	}

	public boolean containsRow(R row) {

		return rows.containsKey(new EmfDataTableRowKey<>(columns, row));
	}
}
