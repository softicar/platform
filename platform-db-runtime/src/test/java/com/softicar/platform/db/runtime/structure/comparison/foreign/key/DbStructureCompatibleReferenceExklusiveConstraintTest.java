package com.softicar.platform.db.runtime.structure.comparison.foreign.key;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureCompatibleComparerStrategyTest;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;
import org.junit.Test;

public class DbStructureCompatibleReferenceExklusiveConstraintTest extends AbstractDbStructureCompatibleComparerStrategyTest {

	@Test
	public void testWarningIfSurplusConstraint() {

		addConstraintStructure(reference);
		executeConstraintComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testErrorIfConstraintForeignTableNameNotEqual() {

		addConstraintStructure(sample);
		addConstraintStructure(reference).setForeignTableName(new DbTableName("some", "tableName"));
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testWarningIfConstraintColumnNameMapNotEqual() {

		addConstraintStructure(sample);
		addConstraintStructure(reference).addColumnPair("a", "b");
		executeConstraintComparison();
		new Asserter().assertOnlyWarnings(2);
	}

	@Test
	public void testWarningIfConstraintOnDeleteActionNotEqual() {

		addConstraintStructure(sample);
		addConstraintStructure(reference).setOnDeleteAction(DbForeignKeyAction.CASCADE);
		executeConstraintComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testWarningIfConstraintOnUpdateActionNotEqual() {

		addConstraintStructure(sample);
		addConstraintStructure(reference).setOnUpdateAction(DbForeignKeyAction.CASCADE);
		executeConstraintComparison();
		new Asserter().assertOnlyWarnings(1);
	}
}
