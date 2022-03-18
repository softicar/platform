package com.softicar.platform.emf.management.importing.variable.replace;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.emf.management.importing.variable.EmfImportVariableCoordinates;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class EmfImportVariablesReplacer {

	private final List<List<String>> rows;
	private Map<EmfImportVariableCoordinates, String> coordinatesToVariableValueMap;

	public EmfImportVariablesReplacer(List<List<String>> rows) {

		this.rows = rows;
	}

	public List<List<String>> execute(SetMap<String, EmfImportVariableCoordinates> variableCoordinates, Map<String, String> variableToValueMap) {

		if (!variableCoordinates.keySet().equals(variableToValueMap.keySet())) {
			throw new IllegalArgumentException("variableCoordinates must contain the same keys as variableToValueMap");
		}

		createCoordinatesToVariableValueMap(variableCoordinates, variableToValueMap);
		return createRowsCopyWithReplacedVariables();
	}

	private Map<EmfImportVariableCoordinates, String> createCoordinatesToVariableValueMap(SetMap<String, EmfImportVariableCoordinates> variableCoordinates,
			Map<String, String> variableValueMap) {

		coordinatesToVariableValueMap = new TreeMap<>();

		for (Map.Entry<String, TreeSet<EmfImportVariableCoordinates>> entry: variableCoordinates.entrySet()) {
			String variableValue = variableValueMap.get(entry.getKey());
			for (EmfImportVariableCoordinates coordinates: entry.getValue()) {
				coordinatesToVariableValueMap.put(coordinates, variableValue);
			}
		}
		return coordinatesToVariableValueMap;
	}

	private List<List<String>> createRowsCopyWithReplacedVariables() {

		List<List<String>> rowsCopyWithReplacedVariables = new ArrayList<>();
		for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
			rowsCopyWithReplacedVariables.add(createRowCopyWithReplacedVariablesOfRowIndex(rowIndex));
		}
		return rowsCopyWithReplacedVariables;
	}

	private List<String> createRowCopyWithReplacedVariablesOfRowIndex(int rowIndex) {

		List<String> rowCopyWithReplacedVariables = new ArrayList<>();

		List<String> row = rows.get(rowIndex);
		for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
			String newValue = coordinatesToVariableValueMap.get(new EmfImportVariableCoordinates(columnIndex, rowIndex));
			if (newValue == null) {
				rowCopyWithReplacedVariables.add(row.get(columnIndex));
			} else {
				rowCopyWithReplacedVariables.add(newValue);
			}
		}

		return rowCopyWithReplacedVariables;
	}
}
