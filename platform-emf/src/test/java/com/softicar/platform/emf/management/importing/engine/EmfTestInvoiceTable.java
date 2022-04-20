package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class EmfTestInvoiceTable extends EmfObjectTable<EmfTestInvoice, EmfTestInvoiceModuleInstance> {

	public EmfTestInvoiceTable(IDbObjectTableBuilder<EmfTestInvoice> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<EmfTestInvoice, Integer, EmfTestInvoiceModuleInstance> configuration) {

		configuration.setScopeField(EmfTestInvoice.INVOICE_MODULE_INSTANCE);
		configuration.setBusinessKey(EmfTestInvoice.UK_INVOICE_MODULE_INSTANCE_PARTNER_NUMBER);
	}
}
