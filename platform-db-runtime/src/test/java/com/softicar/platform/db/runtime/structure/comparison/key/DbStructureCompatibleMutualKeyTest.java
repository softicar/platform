package com.softicar.platform.db.runtime.structure.comparison.key;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureCompatibleComparerStrategyTest;
import com.softicar.platform.db.structure.key.DbKeyType;
import org.junit.Test;

/**
 * @author Daniel Klose
 */
public class DbStructureCompatibleMutualKeyTest extends AbstractDbStructureCompatibleComparerStrategyTest {

	@Test
	public void testWarningIfKeyTypeNotEqual() {

		addKeyStructure(sample, DbKeyType.UNIQUE);
		addKeyStructure(reference, DbKeyType.INDEX);
		executeKeyComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testNoDiagnosticIfKeyTypeEqual() {

		addKeyStructure(sample, DbKeyType.INDEX);
		addKeyStructure(reference, DbKeyType.INDEX);
		executeKeyComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testWarningIfKeyColumnListNotEqual() {

		addKeyStructure(sample, DbKeyType.INDEX).addColumnName(addColumnStructure(sample).getNameOrThrow());
		addKeyStructure(reference, DbKeyType.INDEX).addColumnName(addOtherColumnStructure(reference).getNameOrThrow());
		executeKeyComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testNoDiagnosticIfKeyColumnListEqual() {

		addKeyStructure(sample, DbKeyType.INDEX).addColumnName(addColumnStructure(sample).getNameOrThrow());
		addKeyStructure(reference, DbKeyType.INDEX).addColumnName(addColumnStructure(reference).getNameOrThrow());
		executeKeyComparison();
		new Asserter().assertNoDiagnostics();
	}
}
