package com.softicar.platform.emf.test.module.beta.entity;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.test.module.beta.TestModuleBetaInstance;

public class TestModuleBetaEntityTable extends EmfObjectTable<TestModuleBetaEntity, TestModuleBetaInstance> {

	public TestModuleBetaEntityTable(IDbObjectTableBuilder<TestModuleBetaEntity> builder) {

		super(builder);
	}

	@Override
	public void customizeActionSet(EmfActionSet<TestModuleBetaEntity, TestModuleBetaInstance> actionSet) {

		actionSet.addPrimaryAction(new TestModuleBetaEntityAction());
	}
}
