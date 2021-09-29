package com.softicar.platform.core.module.test.module.standard.beta;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class TestStandardModuleBetaEntityTable extends EmfObjectTable<TestStandardModuleBetaEntity, TestStandardModuleBetaInstance> {

	public TestStandardModuleBetaEntityTable(IDbObjectTableBuilder<TestStandardModuleBetaEntity> builder) {

		super(builder);
	}

	@Override
	public void customizeActionSet(EmfActionSet<TestStandardModuleBetaEntity, TestStandardModuleBetaInstance> actionSet) {

		actionSet.addPrimaryAction(new TestStandardModuleBetaEntityAction());
	}
}
