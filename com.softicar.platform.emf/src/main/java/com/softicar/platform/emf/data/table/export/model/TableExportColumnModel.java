package com.softicar.platform.emf.data.table.export.model;

public class TableExportColumnModel {

	private final String name;
	private final boolean wasEmpty;
	private boolean selected;

	public TableExportColumnModel(String name, boolean wasEmpty, boolean selected) {

		this.name = name;
		this.wasEmpty = wasEmpty;
		this.selected = selected;
	}

	public String getName() {

		return name;
	}

	public boolean wasEmpty() {

		return wasEmpty;
	}

	public boolean isSelected() {

		return selected;
	}

	public void setSelected(boolean selected) {

		this.selected = selected;
	}
}
