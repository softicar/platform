package com.softicar.platform.core.module.test.module.alpha;

import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.ModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;

public class TestModuleAlphaInstanceTable extends ModuleInstanceTable<TestModuleAlphaInstance> {

	public TestModuleAlphaInstanceTable(IDbSubObjectTableBuilder<TestModuleAlphaInstance, AGModuleInstanceBase, Integer> builder) {

		super(builder, TestModuleAlpha.class);
	}
}
