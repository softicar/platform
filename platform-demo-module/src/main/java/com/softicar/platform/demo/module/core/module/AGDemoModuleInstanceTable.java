package com.softicar.platform.demo.module.core.module;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.StandardModuleInstanceTable;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoModuleInstanceTable extends StandardModuleInstanceTable<AGDemoModuleInstance> {

	public AGDemoModuleInstanceTable(IDbSubObjectTableBuilder<AGDemoModuleInstance, AGModuleInstance, Integer> builder) {

		super(builder, DemoModule.class);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoModuleInstance, AGModuleInstance, SystemModuleInstance> configuration) {

		configuration.setBusinessKey(AGDemoModuleInstance.UK_TITLE);
	}
}
