package com.softicar.platform.emf.management.importing.variable;

import java.util.Comparator;
import java.util.Objects;

public class EmfImportVariableCoordinates implements Comparable<EmfImportVariableCoordinates> {

	private final int columnIndex, rowIndex;

	public EmfImportVariableCoordinates(int columnIndex, int rowIndex) {

		this.columnIndex = columnIndex;
		this.rowIndex = rowIndex;
	}

	public int getColumnIndex() {

		return columnIndex;
	}

	public int getRowIndex() {

		return rowIndex;
	}

	@Override
	public int compareTo(EmfImportVariableCoordinates other) {

		return Comparator//
			.comparing(EmfImportVariableCoordinates::getColumnIndex)
			.thenComparing(EmfImportVariableCoordinates::getRowIndex)
			.compare(this, other);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof EmfImportVariableCoordinates) {
			EmfImportVariableCoordinates otherCoordinates = (EmfImportVariableCoordinates) object;
			return compareTo(otherCoordinates) == 0;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(columnIndex, rowIndex);
	}

	@Override
	public String toString() {

		return "EmfImportVariableCoordinates [columnIndex=" + columnIndex + ", rowIndex=" + rowIndex + "]";
	}
}
