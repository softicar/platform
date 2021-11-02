package com.softicar.platform.emf.data.table.export.model;

import com.softicar.platform.dom.elements.DomTable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TableExportTableModelListFactory {

	private final List<Supplier<DomTable>> tableSuppliers = new ArrayList<>();
	private Supplier<DomTable> mainTableSupplier = null;

	public TableExportTableModelListFactory addMainTable(Supplier<DomTable> tableSupplier) {

		return addTable(tableSupplier, true);
	}

	public TableExportTableModelListFactory addSupportTable(Supplier<DomTable> tableSupplier) {

		return addTable(tableSupplier, false);
	}

	public List<Supplier<DomTable>> getTableSuppliers() {

		return tableSuppliers;
	}

	public TableExportTableModelList create(String mainTableName) {

		TableExportTableModelList list = new TableExportTableModelList();
		Supplier<DomTable> mainTableSupplierOrFallback = getMainTableSupplierOrFallback();
		for (Supplier<DomTable> supplier: tableSuppliers) {
			DomTable table = supplier.get();

			TableExportTableModel tableModel;
			if (supplier == mainTableSupplierOrFallback) {
				tableModel = new TableExportTableModel(table, mainTableName);
				list.setMainTableModel(tableModel);
			} else {
				tableModel = new TableExportTableModel(table, null);
			}
			list.add(tableModel);
		}
		return list;
	}

	private TableExportTableModelListFactory addTable(Supplier<DomTable> tableSupplier, boolean main) {

		tableSuppliers.add(tableSupplier);
		if (main && mainTableSupplier == null) {
			mainTableSupplier = tableSupplier;
		}
		return this;
	}

	private Supplier<DomTable> getMainTableSupplierOrFallback() {

		if (mainTableSupplier != null) {
			return mainTableSupplier;
		} else {
			if (!tableSuppliers.isEmpty()) {
				return tableSuppliers.iterator().next();
			} else {
				return null;
			}
		}
	}
}
