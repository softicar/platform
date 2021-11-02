package com.softicar.platform.emf.data.table.export.model;

import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.data.table.export.column.preselection.TableExportDefaultColumnPreselector;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TableExportTableModel {

	//TODO Find a better way to get rid of the actions column
	private static final String ACTIONS_LABEL = EmfI18n.ACTIONS.toString();

	private final DomTable table;
	private final Optional<String> tableName;
	private final List<TableExportColumnModel> tableColumnModels;

	public TableExportTableModel(DomTable table) {

		this(table, null);
	}

	public TableExportTableModel(DomTable table, String tableName) {

		this.table = Objects.requireNonNull(table);
		this.tableName = Optional.ofNullable(tableName);
		this.tableColumnModels = TableExportColumnModelFetcher
			.fetchColumnModels(table, new TableExportDefaultColumnPreselector())
			.stream()
			.filter(model -> !model.getName().equals(ACTIONS_LABEL))
			.collect(Collectors.toList());
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
				if (selectedColumnIndexes.contains(i)) {
					columnModel.setSelected(true);
				} else {
					columnModel.setSelected(false);
				}
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
