package com.softicar.platform.emf.matrix;

import com.softicar.platform.emf.matrix.dimension.EmfSettingMatrixHashDimensionStrategy;
import org.junit.After;
import org.junit.Test;

/**
 * Unit tests for {@link EmfSettingMatrixDiv}.
 * <p>
 * TODO test read-only mode
 * <p>
 * TODO test wildcard row
 */
public class EmfSettingMatrixDivTest extends AbstractEmfSettingMatrixDivTest {

	@Test
	public void testLoad() {

		setup//
			.addStandardComparableEntries()
			.executeSetup();

		asserter//
			.assertRowNames(//
				ROW_NAME_ONE,
				ROW_NAME_TWO)
			.assertColumnNames(//
				COLUMN_NAME_ONE,
				COLUMN_NAME_TWO)
			.assertValues(//
				VALUE_ONE_ONE,
				VALUE_ONE_TWO,
				VALUE_TWO_ONE,
				VALUE_TWO_TWO)
			.assertPersistenceLoaded();
	}

	@Test
	public void testSave() {

		setup//
			.addStandardComparableEntries()
			.executeSetup();

		interaction//
			.setFirstInputValue(VALUE_EDITED)
			.clickSaveButton();

		asserter//
			.assertFirstInputValue(VALUE_EDITED)
			.assertPersistenceSaved();
	}

	@Test
	public void testSavePerformsReload() {

		setup//
			.addStandardComparableEntries()
			.executeSetup();

		interaction//
			.setFirstInputValue(VALUE_EDITED)
			.clickSaveButton();

		asserter//
			.assertFirstInputValue(VALUE_EDITED)
			.assertPersistenceSaveCount(1)
			.assertPersistenceLoadCount(2);
	}

	@Test
	public void testReload() {

		setup//
			.addStandardComparableEntries()
			.executeSetup();

		interaction//
			.setFirstInputValue(VALUE_EDITED)
			.clickReloadButton();

		asserter//
			.assertFirstInputValue(VALUE_ONE_ONE);
	}

	@Test
	public void testFlipping() {

		setup//
			.addStandardComparableEntries()
			.executeSetup();

		interaction//
			.clickFlipCheckbox()
			.clickApply();

		asserter//
			.assertRowNames(//
				COLUMN_NAME_ONE,
				COLUMN_NAME_TWO)
			.assertColumnNames(//
				ROW_NAME_ONE,
				ROW_NAME_TWO)
			.assertValues(//
				VALUE_ONE_ONE,
				VALUE_ONE_TWO,
				VALUE_TWO_ONE,
				VALUE_TWO_TWO);
	}

	@Test
	public void testRowFilteringAndCaseInsensitivity() {

		setup//
			.addStandardComparableEntries()
			.executeSetup();

		interaction//
			.enterRowFilterText("two")
			.clickApply();

		asserter//
			.assertRowNames(//
				ROW_NAME_TWO)
			.assertColumnNames(//
				COLUMN_NAME_ONE,
				COLUMN_NAME_TWO)
			.assertValues(//
				VALUE_TWO_ONE,
				VALUE_TWO_TWO);
	}

	@Test
	public void testRowFilteringWithFlipping() {

		setup//
			.addStandardComparableEntries()
			.executeSetup();

		interaction//
			.enterRowFilterText("two")
			.clickFlipCheckbox()
			.clickApply();

		asserter//
			.assertRowNames(//
				COLUMN_NAME_ONE,
				COLUMN_NAME_TWO)
			.assertColumnNames(//
				ROW_NAME_TWO)
			.assertValues(//
				VALUE_TWO_ONE,
				VALUE_TWO_TWO);
	}

	@Test
	public void testRowAndColumnFiltering() {

		setup//
			.addStandardComparableEntries()
			.executeSetup();

		interaction//
			.enterRowFilterText("two")
			.enterColumnFilterText("one")
			.clickApply();

		asserter//
			.assertRowNames(//
				ROW_NAME_TWO)
			.assertColumnNames(//
				COLUMN_NAME_ONE)
			.assertValues(//
				VALUE_TWO_ONE);
	}

	@Test
	public void testRowAndColumnFilteringWithEmptyResult() {

		setup//
			.addStandardComparableEntries()
			.executeSetup();

		interaction//
			.enterRowFilterText("xxx")
			.clickApply();

		asserter//
			.assertDummyRow();
	}

	@Test
	public void testWithNonComparableDimensions() {

		setup//
			.setRowStrategy(new EmfSettingMatrixHashDimensionStrategy())
			.setColumnStrategy(new EmfSettingMatrixHashDimensionStrategy())
			.addNonComparableEntry(ROW_NAME_ONE, COLUMN_NAME_ONE, VALUE_ONE_ONE)
			.addNonComparableEntry(ROW_NAME_ONE, COLUMN_NAME_TWO, VALUE_ONE_TWO)
			.addNonComparableEntry(ROW_NAME_TWO, COLUMN_NAME_ONE, VALUE_TWO_ONE)
			.addNonComparableEntry(ROW_NAME_TWO, COLUMN_NAME_TWO, VALUE_TWO_TWO)
			.executeSetup();

		asserter//
			.assertRowNames(//
				ROW_NAME_ONE,
				ROW_NAME_TWO)
			.assertColumnNames(//
				COLUMN_NAME_ONE,
				COLUMN_NAME_TWO)
			.assertValues(//
				VALUE_ONE_ONE,
				VALUE_ONE_TWO,
				VALUE_TWO_ONE,
				VALUE_TWO_TWO);
	}

	@After
	public void testSetupExecuted() {

		setup.assertExecuted();
	}
}
