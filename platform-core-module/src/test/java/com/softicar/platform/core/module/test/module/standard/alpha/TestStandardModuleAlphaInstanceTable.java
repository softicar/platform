package com.softicar.platform.core.module.test.module.standard.alpha;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.StandardModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;

public class TestStandardModuleAlphaInstanceTable extends StandardModuleInstanceTable<TestStandardModuleAlphaInstance> {

	public TestStandardModuleAlphaInstanceTable(IDbSubObjectTableBuilder<TestStandardModuleAlphaInstance, AGModuleInstance, Integer> builder) {

		super(builder, TestStandardModuleAlpha.class);
	}
}
