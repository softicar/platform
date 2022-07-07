package com.softicar.platform.demo.invoice.module;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.ModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoInvoiceModuleInstanceTable extends ModuleInstanceTable<AGDemoInvoiceModuleInstance> {

	public AGDemoInvoiceModuleInstanceTable(IDbSubObjectTableBuilder<AGDemoInvoiceModuleInstance, AGModuleInstanceBase, Integer> builder) {

		super(builder, DemoInvoiceModule.class);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoInvoiceModuleInstance, AGModuleInstanceBase, AGCoreModuleInstance> configuration) {

		configuration.setBusinessKey(AGDemoInvoiceModuleInstance.UK_TITLE);
	}
}
