package com.softicar.platform.emf.data.table.header.secondary;

import java.util.Optional;

class EmfDataTableExtraRowColumnSpan<R> {

	private final Optional<IEmfDataTableExtraRowColumnGroup<R>> columnGroup;
	private int columnCount;

	public EmfDataTableExtraRowColumnSpan(Optional<IEmfDataTableExtraRowColumnGroup<R>> columnGroup) {

		this.columnGroup = columnGroup;
		this.columnCount = 1;
	}

	public Optional<IEmfDataTableExtraRowColumnGroup<R>> getColumnGroup() {

		return columnGroup;
	}

	public int getColumnCount() {

		return columnCount;
	}

	public void incrementColumnCount() {

		columnCount++;
	}

	public boolean isColumnGroup(Optional<IEmfDataTableExtraRowColumnGroup<R>> columnGroup) {

		return this.columnGroup.orElse(null) == columnGroup.orElse(null);
	}
}
