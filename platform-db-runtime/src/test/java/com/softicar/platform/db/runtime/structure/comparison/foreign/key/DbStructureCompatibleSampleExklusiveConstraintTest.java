package com.softicar.platform.db.runtime.structure.comparison.foreign.key;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureCompatibleComparerStrategyTest;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;
import org.junit.Test;

public class DbStructureCompatibleSampleExklusiveConstraintTest extends AbstractDbStructureCompatibleComparerStrategyTest {

	@Test
	public void testWarningIfSurplusConstraint() {

		addConstraintStructure(sample);
		executeConstraintComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testErrorIfConstraintForeignTableNameNotEqual() {

		addConstraintStructure(sample).setForeignTableName(new DbTableName("some", "tableName"));
		addConstraintStructure(reference);
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testWarningIfConstraintColumnNameMapNotEqual() {

		addConstraintStructure(sample).addColumnPair("a", "b");
		addConstraintStructure(reference);
		executeConstraintComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testWarningIfConstraintOnDeleteActionNotEqual() {

		addConstraintStructure(sample).setOnDeleteAction(DbForeignKeyAction.CASCADE);
		addConstraintStructure(reference);
		executeConstraintComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testWarningIfConstraintOnUpdateActionNotEqual() {

		addConstraintStructure(sample).setOnUpdateAction(DbForeignKeyAction.CASCADE);
		addConstraintStructure(reference);
		executeConstraintComparison();
		new Asserter().assertOnlyWarnings(1);
	}
}
