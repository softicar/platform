package com.softicar.platform.db.runtime.structure.comparison.key;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureCompatibleComparerStrategyTest;
import com.softicar.platform.db.structure.key.DbKeyType;
import org.junit.Test;

public class DbStructureCompatibleReferenceExclusiveKeyTest extends AbstractDbStructureCompatibleComparerStrategyTest {

	@Test
	public void testSurplusIndexKey() {

		addKeyStructure(reference, DbKeyType.INDEX);
		executeKeyComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testSurplusUniqueKey() {

		addKeyStructure(reference, DbKeyType.UNIQUE);
		executeKeyComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testKeyTypeNotEqual() {

		addKeyStructure(sample, DbKeyType.INDEX);
		addKeyStructure(reference, DbKeyType.UNIQUE);
		executeKeyComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testIndexKeyColumnListNotEqual() {

		addKeyStructure(sample, DbKeyType.INDEX);
		addKeyStructure(reference, DbKeyType.INDEX).addColumnName(addColumnStructure(reference).getNameOrThrow());
		executeKeyComparison();
		new Asserter().assertOnlyWarnings(1);
	}
}
