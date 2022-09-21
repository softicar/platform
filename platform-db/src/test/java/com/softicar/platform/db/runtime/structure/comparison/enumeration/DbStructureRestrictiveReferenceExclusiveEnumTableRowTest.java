package com.softicar.platform.db.runtime.structure.comparison.enumeration;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureRestrictiveComparerStrategyTest;
import org.junit.Test;

public class DbStructureRestrictiveReferenceExclusiveEnumTableRowTest extends AbstractDbStructureRestrictiveComparerStrategyTest {

	@Test
	public void testErrorIfSurplusEnumTableRow() {

		addEnumTableRowStructure(referenceEnum, 1, "FIRST", 11);
		addEnumTableRowStructure(referenceEnum, 2, "SECOND", 22);
		addEnumTableRowStructure(sampleEnum, 2, "SECOND", 22);
		executeEnumTableRowComparison();
		new Asserter().assertOnlyErrors(1);
	}
}
