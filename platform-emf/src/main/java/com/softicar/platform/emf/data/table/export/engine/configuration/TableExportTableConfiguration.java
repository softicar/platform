package com.softicar.platform.emf.data.table.export.engine.configuration;

import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.emf.data.table.export.ITableExportDomTableExtension;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import com.softicar.platform.emf.data.table.export.node.style.TableExportTableCssClass;
import java.util.Optional;

public class TableExportTableConfiguration {

	private final TableExportTableModel tableModel;
	private final TableExportColumnConfiguration columnConfiguration;

	public TableExportTableConfiguration(TableExportTableModel tableModel, TableExportColumnConfiguration columnConfiguration) {

		this.tableModel = tableModel;
		this.columnConfiguration = columnConfiguration;
	}

	public DomTable getTable() {

		return tableModel.getTable();
	}

	public Optional<String> getTableName() {

		return tableModel.getTableName();
	}

	public TableExportTableCssClass getTableCssClass() {

		return new TableExportTableCssClass(getTable());
	}

	public TableExportColumnConfiguration getColumnConfiguration() {

		return columnConfiguration;
	}

	public AutoCloseable startTransaction() {

		DomTable table = getTable();
		if (table instanceof ITableExportDomTableExtension) {
			return ((ITableExportDomTableExtension) table).startTransaction();
		} else {
			return () -> {
				// do nothing per default
			};
		}
	}
}
