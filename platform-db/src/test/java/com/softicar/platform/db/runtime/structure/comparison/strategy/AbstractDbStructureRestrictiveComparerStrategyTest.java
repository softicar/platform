package com.softicar.platform.db.runtime.structure.comparison.strategy;

import org.junit.Test;

public abstract class AbstractDbStructureRestrictiveComparerStrategyTest extends AbstractDbStructureComparisonStrategyTest {

	public AbstractDbStructureRestrictiveComparerStrategyTest() {

		super(new DbStructureEqualityComparisonStrategy());
	}

	@Test
	public void testNoDiagnosticsWithEmptyStructures() {

		executeComparison();
		new Asserter().assertNoDiagnostics();
	}
}
