package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfTestInvoiceModuleInstanceTable extends EmfObjectTable<EmfTestInvoiceModuleInstance, EmfTestObject> {

	public EmfTestInvoiceModuleInstanceTable(IDbObjectTableBuilder<EmfTestInvoiceModuleInstance> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<EmfTestInvoiceModuleInstance, Integer, EmfTestObject> configuration) {

		configuration.setScopeField(EmfTestInvoiceModuleInstance.MODULE_INSTANCE);
		configuration.setBusinessKey(EmfTestInvoiceModuleInstance.UK_MODULE_INSTANCE_NAME);
	}
}
