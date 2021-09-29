package com.softicar.platform.emf.data.table.export.model;

import java.util.ArrayList;

public class TableExportTableModelList extends ArrayList<TableExportTableModel> {

	private TableExportTableModel mainTableModel = null;

	public void setMainTableModel(TableExportTableModel mainTableModel) {

		this.mainTableModel = mainTableModel;
	}

	public TableExportTableModel getMainTableModel() {

		return mainTableModel;
	}
}
