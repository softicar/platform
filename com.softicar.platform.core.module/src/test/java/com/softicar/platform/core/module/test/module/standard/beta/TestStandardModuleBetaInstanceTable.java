package com.softicar.platform.core.module.test.module.standard.beta;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.StandardModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;

public class TestStandardModuleBetaInstanceTable extends StandardModuleInstanceTable<TestStandardModuleBetaInstance> {

	public TestStandardModuleBetaInstanceTable(IDbSubObjectTableBuilder<TestStandardModuleBetaInstance, AGModuleInstance, Integer> builder) {

		super(builder, TestStandardModuleBeta.class);
	}
}
