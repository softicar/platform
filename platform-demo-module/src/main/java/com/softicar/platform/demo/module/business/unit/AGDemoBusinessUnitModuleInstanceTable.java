package com.softicar.platform.demo.module.business.unit;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.StandardModuleInstanceTable;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoBusinessUnitModuleInstanceTable extends StandardModuleInstanceTable<AGDemoBusinessUnitModuleInstance> {

	public AGDemoBusinessUnitModuleInstanceTable(IDbSubObjectTableBuilder<AGDemoBusinessUnitModuleInstance, AGModuleInstance, Integer> builder) {

		super(builder, DemoBusinessUnitModule.class);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoBusinessUnitModuleInstance, AGModuleInstance, SystemModuleInstance> configuration) {

		configuration.setBusinessKey(AGDemoBusinessUnitModuleInstance.UK_TITLE);
	}
}
