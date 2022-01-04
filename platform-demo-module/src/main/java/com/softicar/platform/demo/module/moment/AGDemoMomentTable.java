package com.softicar.platform.demo.module.moment;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoMomentTable extends EmfObjectTable<AGDemoMoment, AGDemoModuleInstance> {

	public AGDemoMomentTable(IDbObjectTableBuilder<AGDemoMoment> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoMoment, Integer, AGDemoModuleInstance> configuration) {

		configuration.setScopeField(AGDemoMoment.MODULE_INSTANCE);
	}
}
