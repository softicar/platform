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

		configuration.setScopeField(EmfTestInvoiceModuleInstance.SCOPE_OBJECT);
		configuration.setBusinessKey(EmfTestInvoiceModuleInstance.UK_NAME);
	}
}
