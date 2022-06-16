package com.softicar.platform.core.module.test.module.alpha;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class TestModuleAlphaEntityTable extends EmfObjectTable<TestModuleAlphaEntity, TestModuleAlphaInstance> {

	public TestModuleAlphaEntityTable(IDbObjectTableBuilder<TestModuleAlphaEntity> builder) {

		super(builder);
	}

	@Override
	public void customizeActionSet(EmfActionSet<TestModuleAlphaEntity, TestModuleAlphaInstance> actionSet) {

		actionSet.addPrimaryAction(new TestModuleAlphaEntityAction());
	}
}
