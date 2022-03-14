package com.softicar.platform.emf.management.importing.variable.replace;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.emf.management.importing.variable.Coordinates;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class EmfImportVariablesReplacer {

	private final List<List<String>> rows;

	public EmfImportVariablesReplacer(List<List<String>> rows) {

		this.rows = rows;
	}

	public List<List<String>> execute(SetMap<String, Coordinates> variableCoordinates, Map<String, String> variableValueMap) {

		List<List<String>> rowsWithReplacedVariables = createRowsCopy();

		for (Map.Entry<String, TreeSet<Coordinates>> entry: variableCoordinates.entrySet()) {
			String variable = entry.getKey();
			for (Coordinates coordinates: entry.getValue()) {
				List<String> row = rowsWithReplacedVariables.get(coordinates.getY());
				row.remove(coordinates.getX());
				row.add(coordinates.getX(), variableValueMap.get(variable));
			}
		}

		return rowsWithReplacedVariables;
	}

	private List<List<String>> createRowsCopy() {

		List<List<String>> copy = new ArrayList<>();
		addRowsToCopy(copy);
		return copy;
	}

	private void addRowsToCopy(List<List<String>> copy) {

		rows.stream().forEach(row -> copy.add(createRowCopy(row)));
	}

	private List<String> createRowCopy(List<String> row) {

		return row.stream().collect(Collectors.toList());
	}
}
