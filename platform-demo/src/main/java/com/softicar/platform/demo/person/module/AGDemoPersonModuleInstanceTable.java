package com.softicar.platform.demo.person.module;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.StandardModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoPersonModuleInstanceTable extends StandardModuleInstanceTable<AGDemoPersonModuleInstance> {

	public AGDemoPersonModuleInstanceTable(IDbSubObjectTableBuilder<AGDemoPersonModuleInstance, AGModuleInstance, Integer> builder) {

		super(builder, DemoPersonModule.class);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoPersonModuleInstance, AGModuleInstance, AGCoreModuleInstance> configuration) {

		configuration.setBusinessKey(AGDemoPersonModuleInstance.UK_TITLE);
	}
}
