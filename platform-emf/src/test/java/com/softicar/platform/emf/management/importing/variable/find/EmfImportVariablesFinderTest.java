package com.softicar.platform.emf.management.importing.variable.find;

import com.softicar.platform.common.container.map.set.SetMap;
import com.softicar.platform.emf.management.importing.variable.EmfImportVariableCoordinates;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class EmfImportVariablesFinderTest extends Assert {

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
	public void testFindWithEmptyRows() {

		assertTrue(new EmfImportVariablesFinder(Collections.emptyList()).find().isEmpty());
	}

	@Test
	public void testFindWithWrongPatterns() {

		List<List<String>> rows = new ArrayList<>();
		rows.add(Arrays.asList("a", "$$A$$", "$$diningRoom$$", "$$garden fence$$", "$$ $$", "$a$$", "$ $a$$", "$$ a $$", "$ $a$ $", "a$$a$$", "$$ä$$"));

		assertTrue(new EmfImportVariablesFinder(rows).find().isEmpty());
	}

	@Test
	public void testFindWithSingleRow() {

		List<List<String>> rows = new ArrayList<>();
		rows.add(Arrays.asList("$$x$$", "$$y$$", "$$zorro$$", "123", "abc", "$$x$$"));

		SetMap<String, EmfImportVariableCoordinates> expectedVariableCoordinates = new SetMap<>();
		expectedVariableCoordinates.addToSet("x", new EmfImportVariableCoordinates(0, 0));
		expectedVariableCoordinates.addToSet("x", new EmfImportVariableCoordinates(5, 0));
		expectedVariableCoordinates.addToSet("y", new EmfImportVariableCoordinates(1, 0));
		expectedVariableCoordinates.addToSet("zorro", new EmfImportVariableCoordinates(2, 0));

		assertEquals(expectedVariableCoordinates, new EmfImportVariablesFinder(rows).find());
	}
}
