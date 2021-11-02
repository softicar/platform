package com.softicar.platform.core.module.test.module.standard.alpha;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class TestStandardModuleAlphaEntityTable extends EmfObjectTable<TestStandardModuleAlphaEntity, TestStandardModuleAlphaInstance> {

	public TestStandardModuleAlphaEntityTable(IDbObjectTableBuilder<TestStandardModuleAlphaEntity> builder) {

		super(builder);
	}

	@Override
	public void customizeActionSet(EmfActionSet<TestStandardModuleAlphaEntity, TestStandardModuleAlphaInstance> actionSet) {

		actionSet.addPrimaryAction(new TestStandardModuleAlphaEntityAction());
	}
}
