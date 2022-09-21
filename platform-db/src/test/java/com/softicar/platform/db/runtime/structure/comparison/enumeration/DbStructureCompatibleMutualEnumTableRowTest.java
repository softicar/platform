package com.softicar.platform.db.runtime.structure.comparison.enumeration;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureCompatibleComparerStrategyTest;
import org.junit.Test;

public class DbStructureCompatibleMutualEnumTableRowTest extends AbstractDbStructureCompatibleComparerStrategyTest {

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
	public void testErrorAndWarningIfEnumIdsNotEqual() {

		addEnumTableRowStructure(referenceEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(referenceEnum, 2, "SECOND", 22);
		addEnumTableRowStructure(sampleEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(sampleEnum, 999, "SECOND", 22);
		executeEnumTableRowComparison();
		new Asserter()//
			.assertErrors(1)
			.assertWarnings(1)
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
