package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfTestBusinessUnitModuleInstanceTable extends EmfObjectTable<EmfTestBusinessUnitModuleInstance, EmfTestObject> {

	public EmfTestBusinessUnitModuleInstanceTable(IDbObjectTableBuilder<EmfTestBusinessUnitModuleInstance> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<EmfTestBusinessUnitModuleInstance, Integer, EmfTestObject> configuration) {

		configuration.setScopeField(EmfTestBusinessUnitModuleInstance.MODULE_INSTANCE);
		configuration.setBusinessKey(EmfTestBusinessUnitModuleInstance.UK_NAME);
	}
}
