package com.softicar.platform.emf.management.importing.variable.find;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.emf.management.importing.variable.EmfImportVariableCoordinates;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Finds variables by a defined pattern in a matrix of {@link String} tokens.
 * <p>
 * The variables are case insensitive. The first occurrence of a variable in the
 * matrix determines its display name in terms of upper and lower case letters.
 *
 * @author Thees Köster
 */
public class EmfImportVariablesFinder {

	private static final String VARIABLE_PATTERN = "\\$\\$[a-zA-Z0-9_]+\\$\\$";

	private final List<List<String>> rows;
	private final Map<String, String> variableToDisplayNameMap = new HashMap<>();
	private final SetMap<String, EmfImportVariableCoordinates> variableDisplayNameToCoordinatesMap = new SetMap<>();

	public EmfImportVariablesFinder(List<List<String>> rows) {

		this.rows = rows;
	}

	/**
	 * Finds variables and their coordinates in the given matrix of
	 * {@link String} tokens.
	 *
	 * @return A map from variable display name to its coordinates.
	 */
	public SetMap<String, EmfImportVariableCoordinates> find() {

		variableToDisplayNameMap.clear();
		variableDisplayNameToCoordinatesMap.clear();

		for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
			findCoordinatesOfRowIndex(rowIndex);
		}
		return variableDisplayNameToCoordinatesMap;
	}

	private void findCoordinatesOfRowIndex(int rowIndex) {

		List<String> row = rows.get(rowIndex);
		for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
			addVariableCoordinatesIfPatternFound(row.get(columnIndex), columnIndex, rowIndex);
		}
	}

	private void addVariableCoordinatesIfPatternFound(String cell, int columnIndex, int rowIndex) {

		if (cell.matches(VARIABLE_PATTERN)) {
			String variableDisplayName = cell.substring(2, cell.length() - 2);
			String variable = variableDisplayName.toLowerCase();
			if (variableToDisplayNameMap.containsKey(variable)) {
				variableDisplayName = variableToDisplayNameMap.get(variable);
			} else {
				variableToDisplayNameMap.put(variable, variableDisplayName);
			}
			variableDisplayNameToCoordinatesMap.addToSet(variableDisplayName, new EmfImportVariableCoordinates(columnIndex, rowIndex));
		}
	}
}
