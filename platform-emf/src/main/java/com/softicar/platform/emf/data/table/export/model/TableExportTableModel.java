package com.softicar.platform.emf.data.table.export.model;

import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.emf.data.table.export.column.preselection.TableExportDefaultColumnPreselector;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

public class TableExportTableModel {

	private final DomTable table;
	private final Optional<String> tableName;
	private final List<TableExportColumnModel> tableColumnModels;

	public TableExportTableModel(DomTable table) {

		this(table, null);
	}

	public TableExportTableModel(DomTable table, String tableName) {

		this.table = Objects.requireNonNull(table);
		this.tableName = Optional.ofNullable(tableName);
		this.tableColumnModels = TableExportColumnModelFetcher.fetchColumnModels(table, new TableExportDefaultColumnPreselector());
	}

	public DomTable getTable() {

		return table;
	}

	public Optional<String> getTableName() {

		return tableName;
	}

	public List<TableExportColumnModel> getTableColumnModels() {

		return tableColumnModels;
	}

	public void setSelectedColumnIndexes(Set<Integer> selectedColumnIndexes) {

		for (int i = 0; i < this.tableColumnModels.size(); i++) {
			TableExportColumnModel columnModel = this.tableColumnModels.get(i);

			if (selectedColumnIndexes != null) {
				columnModel.setSelected(selectedColumnIndexes.contains(i));
			}

			else {
				columnModel.setSelected(false);
			}
		}
	}

	public Map<Integer, TableExportColumnModel> getSelectedColumnModels() {

		Map<Integer, TableExportColumnModel> indexes = new TreeMap<>();

		for (int i = 0; i < this.tableColumnModels.size(); i++) {
			TableExportColumnModel columnModel = this.tableColumnModels.get(i);

			if (columnModel != null && columnModel.isSelected()) {
				indexes.put(i, columnModel);
			}
		}

		return indexes;
	}
}
