package com.softicar.platform.demo.core.module;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.StandardModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoCoreModuleInstanceTable extends StandardModuleInstanceTable<AGDemoCoreModuleInstance> {

	public AGDemoCoreModuleInstanceTable(IDbSubObjectTableBuilder<AGDemoCoreModuleInstance, AGModuleInstance, Integer> builder) {

		super(builder, DemoCoreModule.class);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoCoreModuleInstance, AGModuleInstance, AGCoreModuleInstance> configuration) {

		configuration.setBusinessKey(AGDemoCoreModuleInstance.UK_TITLE);
	}
}
