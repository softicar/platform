package com.softicar.platform.emf.management.importing.variable.find;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.emf.management.importing.variable.EmfImportVariableCoordinates;
import java.util.List;

public class EmfImportVariablesFinder {

	private static final String VARIABLE_PATTERN = "\\$\\$[a-z]\\$\\$";

	private final List<List<String>> rows;
	private final SetMap<String, EmfImportVariableCoordinates> variableCoordinates = new SetMap<>();

	public EmfImportVariablesFinder(List<List<String>> rows) {

		this.rows = rows;
	}

	public SetMap<String, EmfImportVariableCoordinates> find() {

		variableCoordinates.clear();
		for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
			findCoordinatesOfRowIndex(rowIndex);
		}
		return variableCoordinates;
	}

	private void findCoordinatesOfRowIndex(int rowIndex) {

		List<String> row = rows.get(rowIndex);
		for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
			addVariableCoordinatesIfPatternFound(row.get(columnIndex), columnIndex, rowIndex);
		}
	}

	private void addVariableCoordinatesIfPatternFound(String cell, int columnIndex, int rowIndex) {

		if (cell.matches(VARIABLE_PATTERN)) {
			String variable = cell.substring(2, 3);
			variableCoordinates.addToSet(variable, new EmfImportVariableCoordinates(columnIndex, rowIndex));
		}
	}
}
