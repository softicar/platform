package com.softicar.platform.demo.module.invoice.module;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.StandardModuleInstanceTable;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoInvoiceModuleInstanceTable extends StandardModuleInstanceTable<AGDemoInvoiceModuleInstance> {

	public AGDemoInvoiceModuleInstanceTable(IDbSubObjectTableBuilder<AGDemoInvoiceModuleInstance, AGModuleInstance, Integer> builder) {

		super(builder, DemoInvoiceModule.class);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoInvoiceModuleInstance, AGModuleInstance, SystemModuleInstance> configuration) {

		configuration.setBusinessKey(AGDemoInvoiceModuleInstance.UK_TITLE);
	}
}
