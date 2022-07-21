package com.softicar.platform.db.runtime.structure.comparison.enumeration;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureCompatibleComparerStrategyTest;
import org.junit.Test;

public class DbStructureCompatibleSampleExclusiveEnumTableRowTest extends AbstractDbStructureCompatibleComparerStrategyTest {

	@Test
	public void testErrorIfSurplusEnumTableRow() {

		addEnumTableRowStructure(referenceEnum, 2, "SECOND", 22);
		addEnumTableRowStructure(sampleEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(sampleEnum, 2, "SECOND", 22);
		executeEnumTableRowComparison();
		new Asserter().assertOnlyErrors(1);
	}
}
