package com.softicar.platform.emf.management.importing.variable.find;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.emf.management.importing.variable.EmfImportVariableCoordinates;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class EmfImportVariablesFinderTest extends AbstractTest {

	@Test
	public void testFind() {

		List<List<String>> rows = new ArrayList<>();
		rows.add(Arrays.asList("", "", "", "", ""));
		rows.add(Arrays.asList("$$a$$", "", "", "", ""));
		rows.add(Arrays.asList("$$a$$", "$$bounty$$", "", "", ""));
		rows.add(Arrays.asList("", "", "", "", "$$cat$$"));

		SetMap<String, EmfImportVariableCoordinates> expectedVariableCoordinates = new SetMap<>();
		expectedVariableCoordinates.addToSet("a", new EmfImportVariableCoordinates(0, 1));
		expectedVariableCoordinates.addToSet("a", new EmfImportVariableCoordinates(0, 2));
		expectedVariableCoordinates.addToSet("bounty", new EmfImportVariableCoordinates(1, 2));
		expectedVariableCoordinates.addToSet("cat", new EmfImportVariableCoordinates(4, 3));

		assertEquals(expectedVariableCoordinates, new EmfImportVariablesFinder(rows).find());
	}

	@Test
	public void testFindWithSingleRow() {

		List<List<String>> rows = new ArrayList<>();
		rows.add(Arrays.asList("$$x$$", "y", "$$zorro$$", "123", "abc", "$$x$$", "x", "zorro"));

		SetMap<String, EmfImportVariableCoordinates> expectedVariableCoordinates = new SetMap<>();
		expectedVariableCoordinates.addToSet("x", new EmfImportVariableCoordinates(0, 0));
		expectedVariableCoordinates.addToSet("x", new EmfImportVariableCoordinates(5, 0));
		expectedVariableCoordinates.addToSet("zorro", new EmfImportVariableCoordinates(2, 0));

		assertEquals(expectedVariableCoordinates, new EmfImportVariablesFinder(rows).find());
	}

	@Test
	public void testFindWithEmptyRows() {

		assertTrue(new EmfImportVariablesFinder(Collections.emptyList()).find().isEmpty());
	}

	@Test
	public void testFindWithAllowedPatterns() {

		List<List<String>> rows = new ArrayList<>();
		rows.add(Arrays.asList("$$_$$", "$$__$$", "$$4$$", "$$one_two$$", "$$_planB$$", "$$Number_42$$"));

		SetMap<String, EmfImportVariableCoordinates> expectedVariableCoordinates = new SetMap<>();
		expectedVariableCoordinates.addToSet("_", new EmfImportVariableCoordinates(0, 0));
		expectedVariableCoordinates.addToSet("__", new EmfImportVariableCoordinates(1, 0));
		expectedVariableCoordinates.addToSet("4", new EmfImportVariableCoordinates(2, 0));
		expectedVariableCoordinates.addToSet("one_two", new EmfImportVariableCoordinates(3, 0));
		expectedVariableCoordinates.addToSet("_planB", new EmfImportVariableCoordinates(4, 0));
		expectedVariableCoordinates.addToSet("Number_42", new EmfImportVariableCoordinates(5, 0));

		assertEquals(expectedVariableCoordinates, new EmfImportVariablesFinder(rows).find());
	}

	@Test
	public void testFindWithWrongPatterns() {

		List<List<String>> rows = new ArrayList<>();
		rows.add(Arrays.asList("$$-$$", "$$Dining Room$$", "$$garden fence$$", "$$ $$", "$a$$", "$ $a$$", "$$ a $$", "$ $a$ $", "a$$a$$", "$$ä$$", "$$$$$"));

		assertTrue(new EmfImportVariablesFinder(rows).find().isEmpty());
	}

	@Test
	public void testFindWithDifferentNamesOfSameVariable() {

		List<List<String>> rows = new ArrayList<>();
		rows.add(Arrays.asList("$$Garden_Fence$$", "$$garden_fence$$", "$$Garden_fence$$"));
		rows.add(Arrays.asList("$$diningRoom$$", "$$DiningRoom$$", "$$diningroom$$"));
		rows.add(Arrays.asList("$$woodenmallet$$", "$$woodenMallet$$", "$$WoodenMallet$$"));
		rows.add(Arrays.asList("", "", "$$Test$$"));
		rows.add(Arrays.asList("$$test$$", "", ""));

		SetMap<String, EmfImportVariableCoordinates> expectedVariableCoordinates = new SetMap<>();
		expectedVariableCoordinates.addToSet("Garden_Fence", new EmfImportVariableCoordinates(0, 0));
		expectedVariableCoordinates.addToSet("Garden_Fence", new EmfImportVariableCoordinates(1, 0));
		expectedVariableCoordinates.addToSet("Garden_Fence", new EmfImportVariableCoordinates(2, 0));

		expectedVariableCoordinates.addToSet("diningRoom", new EmfImportVariableCoordinates(0, 1));
		expectedVariableCoordinates.addToSet("diningRoom", new EmfImportVariableCoordinates(1, 1));
		expectedVariableCoordinates.addToSet("diningRoom", new EmfImportVariableCoordinates(2, 1));

		expectedVariableCoordinates.addToSet("woodenmallet", new EmfImportVariableCoordinates(0, 2));
		expectedVariableCoordinates.addToSet("woodenmallet", new EmfImportVariableCoordinates(1, 2));
		expectedVariableCoordinates.addToSet("woodenmallet", new EmfImportVariableCoordinates(2, 2));

		expectedVariableCoordinates.addToSet("Test", new EmfImportVariableCoordinates(2, 3));
		expectedVariableCoordinates.addToSet("Test", new EmfImportVariableCoordinates(0, 4));

		assertEquals(expectedVariableCoordinates, new EmfImportVariablesFinder(rows).find());
	}
}
