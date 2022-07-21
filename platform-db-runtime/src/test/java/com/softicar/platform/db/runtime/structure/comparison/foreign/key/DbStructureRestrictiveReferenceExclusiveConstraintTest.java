package com.softicar.platform.db.runtime.structure.comparison.foreign.key;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureRestrictiveComparerStrategyTest;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;
import org.junit.Test;

/**
 * @author Daniel Klose
 */
public class DbStructureRestrictiveReferenceExclusiveConstraintTest extends AbstractDbStructureRestrictiveComparerStrategyTest {

	@Test
	public void testErrorIfSurplusConstraint() {

		addConstraintStructure(reference);
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfConstraintForeignTableNameNotEqual() {

		addConstraintStructure(sample);
		addConstraintStructure(reference).setForeignTableName(new DbTableName("some", "tableName"));
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfConstraintColumnNameMapNotEqual() {

		addConstraintStructure(sample);
		addConstraintStructure(reference).addColumnPair("a", "b");
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(2);
	}

	@Test
	public void testErrorIfConstraintOnDeleteActionNotEqual() {

		addConstraintStructure(sample);
		addConstraintStructure(reference).setOnDeleteAction(DbForeignKeyAction.CASCADE);
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfConstraintOnUpdateActionNotEqual() {

		addConstraintStructure(sample);
		addConstraintStructure(reference).setOnUpdateAction(DbForeignKeyAction.CASCADE);
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}
}
