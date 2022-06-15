package com.softicar.platform.core.module.test.module.standard.alpha;

import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.ModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;

public class TestModuleAlphaInstanceTable extends ModuleInstanceTable<TestModuleAlphaInstance> {

	public TestModuleAlphaInstanceTable(IDbSubObjectTableBuilder<TestModuleAlphaInstance, AGModuleInstance, Integer> builder) {

		super(builder, TestModuleAlpha.class);
	}
}
