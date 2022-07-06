package com.softicar.platform.core.module.test.module.beta;

import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.ModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;

public class TestModuleBetaInstanceTable extends ModuleInstanceTable<TestModuleBetaInstance> {

	public TestModuleBetaInstanceTable(IDbSubObjectTableBuilder<TestModuleBetaInstance, AGModuleInstanceBase, Integer> builder) {

		super(builder, TestModuleBeta.class);
	}
}
