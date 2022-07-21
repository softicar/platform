package com.softicar.platform.db.runtime.structure.comparison.key;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureRestrictiveComparerStrategyTest;
import com.softicar.platform.db.structure.key.DbKeyType;
import org.junit.Test;

/**
 * @author Daniel Klose
 */
public class DbStructureRestrictiveSampleExclusiveKeyTest extends AbstractDbStructureRestrictiveComparerStrategyTest {

	@Test
	public void testErrorIfSurplusIndex() {

		addKeyStructure(sample, DbKeyType.INDEX);
		executeKeyComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfSurplusUnique() {

		addKeyStructure(sample, DbKeyType.UNIQUE);
		executeKeyComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfKeyTypeNotEqual() {

		addKeyStructure(sample, DbKeyType.INDEX);
		addKeyStructure(reference, DbKeyType.UNIQUE);
		executeKeyComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfKeyColumnListNotEqual() {

		addKeyStructure(sample, DbKeyType.INDEX).addColumnName(addColumnStructure(sample).getNameOrThrow());
		addKeyStructure(reference, DbKeyType.INDEX);
		executeKeyComparison();
		new Asserter().assertOnlyErrors(1);
	}
}
