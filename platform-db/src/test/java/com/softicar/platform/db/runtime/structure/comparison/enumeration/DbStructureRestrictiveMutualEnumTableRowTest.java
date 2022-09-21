package com.softicar.platform.db.runtime.structure.comparison.enumeration;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureRestrictiveComparerStrategyTest;
import org.junit.Test;

public class DbStructureRestrictiveMutualEnumTableRowTest extends AbstractDbStructureRestrictiveComparerStrategyTest {

	@Test
	public void testNoDiagnosticsIfEnumEntriesEqual() {

		addEnumTableRowStructure(referenceEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(referenceEnum, 2, "SECOND", 22);
		addEnumTableRowStructure(sampleEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(sampleEnum, 2, "SECOND", 22);
		executeEnumTableRowComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfEnumTableRowPayloadValuesNotEqual() {

		addEnumTableRowStructure(referenceEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(referenceEnum, 2, "SECOND", 22);
		addEnumTableRowStructure(sampleEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(sampleEnum, 2, "SECOND", 999);
		executeEnumTableRowComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfEnumTableRowNamesNotEqual() {

		addEnumTableRowStructure(referenceEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(referenceEnum, 2, "SECOND", 22);
		addEnumTableRowStructure(sampleEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(sampleEnum, 2, "SECONDxxx", 22);
		executeEnumTableRowComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfEnumIdsNotEqual() {

		addEnumTableRowStructure(referenceEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(referenceEnum, 2, "SECOND", 22);
		addEnumTableRowStructure(sampleEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(sampleEnum, 999, "SECOND", 22);
		executeEnumTableRowComparison();
		new Asserter()//
			.assertErrors(2)
			.assertNoWarnings()
			.assertNoInfos();
	}

	@Test
	public void testNoDiagnosticsIfIgnoredReferenceColumnExists() {

		addIgnoredColumnStructure(referenceEnum);
		addEnumTableRowStructure(referenceEnum, 1, "FIRST", 11, "someIgnoredValue");
		addEnumTableRowStructure(referenceEnum, 2, "SECOND", 22, "someOtherIgnoredValue");
		addEnumTableRowStructure(sampleEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(sampleEnum, 2, "SECOND", 22);
		executeEnumTableRowComparison();
		new Asserter().assertNoDiagnostics();
	}
}
