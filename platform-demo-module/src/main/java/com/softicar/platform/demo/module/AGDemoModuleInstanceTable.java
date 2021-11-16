package com.softicar.platform.demo.module;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.StandardModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;

public class AGDemoModuleInstanceTable extends StandardModuleInstanceTable<AGDemoModuleInstance> {

	public AGDemoModuleInstanceTable(IDbSubObjectTableBuilder<AGDemoModuleInstance, AGModuleInstance, Integer> builder) {

		super(builder, DemoModule.class);
	}
}
