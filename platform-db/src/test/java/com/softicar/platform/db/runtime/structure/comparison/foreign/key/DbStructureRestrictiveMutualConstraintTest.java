package com.softicar.platform.db.runtime.structure.comparison.foreign.key;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureRestrictiveComparerStrategyTest;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;
import org.junit.Test;

/**
 * @author Daniel Klose
 */
public class DbStructureRestrictiveMutualConstraintTest extends AbstractDbStructureRestrictiveComparerStrategyTest {

	@Test
	public void testErrorIfConstraintForeignTableNameNotEqual() {

		addConstraintStructure(sample);
		addConstraintStructure(reference).setForeignTableName(new DbTableName("someOtherDatabase", "someOtherTable"));
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfConstraintForeignTableNameEqual() {

		addConstraintStructure(sample);
		addConstraintStructure(reference);
		executeConstraintComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfConstraintColumnNameMapNotEqual() {

		addConstraintStructure(sample).addColumnPair("a", "b");
		addConstraintStructure(reference).addColumnPair("a", "c");
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfConstraintColumnNameMapEqual() {

		addConstraintStructure(sample).addColumnPair("a", "b");
		addConstraintStructure(reference).addColumnPair("a", "b");
		executeConstraintComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfConstraintOnDeleteActionNotEqual() {

		addConstraintStructure(sample).setOnDeleteAction(DbForeignKeyAction.RESTRICT);
		addConstraintStructure(reference).setOnDeleteAction(DbForeignKeyAction.NO_ACTION);
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfConstraintOnDeleteActionEqual() {

		addConstraintStructure(sample).setOnDeleteAction(DbForeignKeyAction.CASCADE);
		addConstraintStructure(reference).setOnDeleteAction(DbForeignKeyAction.CASCADE);
		executeConstraintComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfConstraintOnUpdateActionNotEqual() {

		addConstraintStructure(sample).setOnUpdateAction(DbForeignKeyAction.RESTRICT);
		addConstraintStructure(reference).setOnUpdateAction(DbForeignKeyAction.NO_ACTION);
		executeConstraintComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfConstraintOnUpdateActionEqual() {

		addConstraintStructure(sample).setOnUpdateAction(DbForeignKeyAction.CASCADE);
		addConstraintStructure(reference).setOnUpdateAction(DbForeignKeyAction.CASCADE);
		executeConstraintComparison();
		new Asserter().assertNoDiagnostics();
	}
}
