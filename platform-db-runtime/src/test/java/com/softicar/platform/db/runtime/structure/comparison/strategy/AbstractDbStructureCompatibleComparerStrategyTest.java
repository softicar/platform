package com.softicar.platform.db.runtime.structure.comparison.strategy;

import org.junit.Test;

public abstract class AbstractDbStructureCompatibleComparerStrategyTest extends AbstractDbStructureComparisonStrategyTest {

	public AbstractDbStructureCompatibleComparerStrategyTest() {

		super(new DbStructureCompatibilityComparisonStrategy());
	}

	@Test
	public void testNoDiagnosticsWithEmptyStructures() {

		executeComparison();
		new Asserter().assertNoDiagnostics();
	}
}
