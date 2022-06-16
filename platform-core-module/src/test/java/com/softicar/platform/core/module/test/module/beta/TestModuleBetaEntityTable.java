package com.softicar.platform.core.module.test.module.beta;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class TestModuleBetaEntityTable extends EmfObjectTable<TestModuleBetaEntity, TestModuleBetaInstance> {

	public TestModuleBetaEntityTable(IDbObjectTableBuilder<TestModuleBetaEntity> builder) {

		super(builder);
	}

	@Override
	public void customizeActionSet(EmfActionSet<TestModuleBetaEntity, TestModuleBetaInstance> actionSet) {

		actionSet.addPrimaryAction(new TestModuleBetaEntityAction());
	}
}
