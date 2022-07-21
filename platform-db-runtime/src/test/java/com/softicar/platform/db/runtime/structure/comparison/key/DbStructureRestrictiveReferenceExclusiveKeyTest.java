package com.softicar.platform.db.runtime.structure.comparison.key;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureRestrictiveComparerStrategyTest;
import com.softicar.platform.db.structure.key.DbKeyType;
import org.junit.Test;

/**
 * @author Daniel Klose
 */
public class DbStructureRestrictiveReferenceExclusiveKeyTest extends AbstractDbStructureRestrictiveComparerStrategyTest {

	@Test
	public void testErrorIfSurplusIndex() {

		addKeyStructure(reference, DbKeyType.INDEX);
		executeKeyComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfSurplusUnique() {

		addKeyStructure(reference, DbKeyType.UNIQUE);
		executeKeyComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfKeyTypeNotEqual() {

		addKeyStructure(sample, DbKeyType.UNIQUE);
		addKeyStructure(reference, DbKeyType.INDEX);
		executeKeyComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfKeyColumnListNotEqual() {

		addKeyStructure(sample, DbKeyType.INDEX);
		addKeyStructure(reference, DbKeyType.INDEX).addColumnName(addColumnStructure(reference).getNameOrThrow());
		executeKeyComparison();
		new Asserter().assertOnlyErrors(1);
	}
}
