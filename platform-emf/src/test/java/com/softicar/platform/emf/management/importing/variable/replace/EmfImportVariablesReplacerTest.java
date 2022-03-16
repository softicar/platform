package com.softicar.platform.emf.management.importing.variable.replace;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.emf.management.importing.variable.EmfImportVariableCoordinates;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Assert;
import org.junit.Test;

public class EmfImportVariablesReplacerTest extends Assert {

	@Test
	public void testExecute() {

		List<List<String>> rows = new ArrayList<>();
		rows.add(Arrays.asList("", "", "", ""));
		rows.add(Arrays.asList("", "", "", ""));
		rows.add(Arrays.asList("", "", "", ""));

		SetMap<String, EmfImportVariableCoordinates> variableCoordinates = new SetMap<>();
		variableCoordinates.addToSet("a", new EmfImportVariableCoordinates(0, 0));
		variableCoordinates.addToSet("a", new EmfImportVariableCoordinates(0, 1));
		variableCoordinates.addToSet("b", new EmfImportVariableCoordinates(2, 1));
		variableCoordinates.addToSet("Variable C", new EmfImportVariableCoordinates(3, 2));

		Map<String, String> variableToValueMap = new TreeMap<>();
		variableToValueMap.put("a", "1");
		variableToValueMap.put("b", "22");
		variableToValueMap.put("Variable C", "Hello");

		List<List<String>> expectedRowsWithReplacedVariables = new ArrayList<>();
		expectedRowsWithReplacedVariables.add(Arrays.asList("1", "", "", ""));
		expectedRowsWithReplacedVariables.add(Arrays.asList("1", "", "22", ""));
		expectedRowsWithReplacedVariables.add(Arrays.asList("", "", "", "Hello"));

		assertEquals(
			expectedRowsWithReplacedVariables,//
			new EmfImportVariablesReplacer(rows).execute(variableCoordinates, variableToValueMap));
	}

	@Test
	public void testExecuteWithVariableNameInRows() {

		// Variable names in rows have no effect. Only the coordinates determine where values are replaced.

		List<List<String>> rows = new ArrayList<>();
		rows.add(Arrays.asList("", "mouse"));

		SetMap<String, EmfImportVariableCoordinates> variableCoordinates = new SetMap<>();
		variableCoordinates.addToSet("mouse", new EmfImportVariableCoordinates(0, 0));

		Map<String, String> variableToValueMap = new TreeMap<>();
		variableToValueMap.put("mouse", "cat");

		List<List<String>> expectedRowsWithReplacedVariables = new ArrayList<>();
		expectedRowsWithReplacedVariables.add(Arrays.asList("cat", "mouse"));

		assertEquals(
			expectedRowsWithReplacedVariables,//
			new EmfImportVariablesReplacer(rows).execute(variableCoordinates, variableToValueMap));
	}

	@Test
	public void testExecuteWithNullInRows() {

		List<List<String>> rows = new ArrayList<>();
		rows.add(Arrays.asList(null, null));

		SetMap<String, EmfImportVariableCoordinates> variableCoordinates = new SetMap<>();
		variableCoordinates.addToSet("a", new EmfImportVariableCoordinates(0, 0));

		Map<String, String> variableToValueMap = new TreeMap<>();
		variableToValueMap.put("a", "A");

		List<List<String>> expectedRowsWithReplacedVariables = new ArrayList<>();
		expectedRowsWithReplacedVariables.add(Arrays.asList("A", null));

		assertEquals(
			expectedRowsWithReplacedVariables,//
			new EmfImportVariablesReplacer(rows).execute(variableCoordinates, variableToValueMap));
	}

	@Test
	public void testExecuteWithWrongCoordinates() {

		// Wrong coordinates are ignored

		List<List<String>> rows = new ArrayList<>();
		rows.add(Arrays.asList("", ""));

		SetMap<String, EmfImportVariableCoordinates> variableCoordinates = new SetMap<>();
		variableCoordinates.addToSet("a", new EmfImportVariableCoordinates(2, 0));

		Map<String, String> variableToValueMap = new TreeMap<>();
		variableToValueMap.put("a", "A");

		List<List<String>> expectedRowsWithReplacedVariables = new ArrayList<>();
		expectedRowsWithReplacedVariables.add(Arrays.asList("", ""));

		assertEquals(
			expectedRowsWithReplacedVariables,//
			new EmfImportVariablesReplacer(rows).execute(variableCoordinates, variableToValueMap));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExecuteWithWrongVariableToValueMap() {

		SetMap<String, EmfImportVariableCoordinates> variableCoordinates = new SetMap<>();
		variableCoordinates.addToSet("a", new EmfImportVariableCoordinates(0, 0));

		Map<String, String> variableToValueMap = new TreeMap<>();
		variableToValueMap.put("b", "B");

		new EmfImportVariablesReplacer(Collections.emptyList()).execute(variableCoordinates, variableToValueMap);
	}
}
