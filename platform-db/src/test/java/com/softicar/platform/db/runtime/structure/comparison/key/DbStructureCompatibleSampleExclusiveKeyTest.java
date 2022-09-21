package com.softicar.platform.db.runtime.structure.comparison.key;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureCompatibleComparerStrategyTest;
import com.softicar.platform.db.structure.key.DbKeyType;
import org.junit.Test;

public class DbStructureCompatibleSampleExclusiveKeyTest extends AbstractDbStructureCompatibleComparerStrategyTest {

	@Test
	public void testSurplusIndexKey() {

		addKeyStructure(sample, DbKeyType.INDEX);
		executeKeyComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testSurplusUniqueKey() {

		addKeyStructure(sample, DbKeyType.UNIQUE);
		executeKeyComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testKeyTypeNotEqual() {

		addKeyStructure(sample, DbKeyType.UNIQUE);
		addKeyStructure(reference, DbKeyType.INDEX);
		executeKeyComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testIndexKeyColumnListNotEqual() {

		addKeyStructure(sample, DbKeyType.INDEX).addColumnName(addColumnStructure(sample).getNameOrThrow());
		addKeyStructure(reference, DbKeyType.INDEX);
		executeKeyComparison();
		new Asserter().assertOnlyWarnings(1);
	}
}
