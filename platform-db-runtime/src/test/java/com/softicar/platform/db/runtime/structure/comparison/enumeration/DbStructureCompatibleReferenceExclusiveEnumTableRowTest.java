package com.softicar.platform.db.runtime.structure.comparison.enumeration;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureCompatibleComparerStrategyTest;
import org.junit.Test;

public class DbStructureCompatibleReferenceExclusiveEnumTableRowTest extends AbstractDbStructureCompatibleComparerStrategyTest {

	@Test
	public void testWarningIfSurplusEnumTableRow() {

		addEnumTableRowStructure(referenceEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(referenceEnum, 2, "SECOND", 22);
		addEnumTableRowStructure(sampleEnum, 2, "SECOND", 22);
		executeEnumTableRowComparison();
		new Asserter().assertOnlyWarnings(1);
	}
}
