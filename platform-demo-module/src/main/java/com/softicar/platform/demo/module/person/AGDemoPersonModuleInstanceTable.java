package com.softicar.platform.demo.module.person;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.StandardModuleInstanceTable;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoPersonModuleInstanceTable extends StandardModuleInstanceTable<AGDemoPersonModuleInstance> {

	public AGDemoPersonModuleInstanceTable(IDbSubObjectTableBuilder<AGDemoPersonModuleInstance, AGModuleInstance, Integer> builder) {

		super(builder, DemoPersonModule.class);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoPersonModuleInstance, AGModuleInstance, SystemModuleInstance> configuration) {

		configuration.setBusinessKey(AGDemoPersonModuleInstance.UK_TITLE);
	}
}
