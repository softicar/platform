package com.softicar.platform.db.runtime.structure.comparison.foreign.key;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureRestrictiveComparerStrategyTest;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;
import org.junit.Test;

/**
 * @author Daniel Klose
 */
public class DbStructureRestrictiveSampleExclusiveConstraintTest extends AbstractDbStructureRestrictiveComparerStrategyTest {

	@Test
	public void testErrorIfSurplusConstraint() {

		addConstraintStructure(sample);
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfConstraintForeignTableNameNotEqual() {

		addConstraintStructure(sample).setForeignTableName(new DbTableName("some", "tableName"));
		addConstraintStructure(reference);
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfConstraintColumnNameMapNotEqual() {

		addConstraintStructure(sample).addColumnPair("a", "b");
		addConstraintStructure(reference);
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfConstraintOnDeleteActionNotEqual() {

		addConstraintStructure(sample).setOnDeleteAction(DbForeignKeyAction.CASCADE);
		addConstraintStructure(reference);
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfConstraintOnUpdateActionNotEqual() {

		addConstraintStructure(sample).setOnUpdateAction(DbForeignKeyAction.CASCADE);
		addConstraintStructure(reference);
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}
}
