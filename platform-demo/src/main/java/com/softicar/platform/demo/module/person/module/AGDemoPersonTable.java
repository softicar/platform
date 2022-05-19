package com.softicar.platform.demo.module.person.module;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoPersonTable extends EmfObjectTable<AGDemoPerson, AGDemoPersonModuleInstance> {

	public AGDemoPersonTable(IDbObjectTableBuilder<AGDemoPerson> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoPerson, Integer, AGDemoPersonModuleInstance> configuration) {

		configuration.setScopeField(AGDemoPerson.MODULE_INSTANCE);
		configuration.setBusinessKey(AGDemoPerson.UK_MODULE_INSTANCE_IDENTITY_CARD_NUMBER);
	}
}
