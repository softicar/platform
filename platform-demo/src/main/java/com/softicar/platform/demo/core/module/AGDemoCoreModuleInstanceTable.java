package com.softicar.platform.demo.core.module;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.ModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoCoreModuleInstanceTable extends ModuleInstanceTable<AGDemoCoreModuleInstance> {

	public AGDemoCoreModuleInstanceTable(IDbSubObjectTableBuilder<AGDemoCoreModuleInstance, AGModuleInstanceBase, Integer> builder) {

		super(builder, DemoCoreModule.class);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoCoreModuleInstance, AGModuleInstanceBase, AGCoreModuleInstance> configuration) {

		configuration.setBusinessKey(AGDemoCoreModuleInstance.UK_TITLE);
	}
}
