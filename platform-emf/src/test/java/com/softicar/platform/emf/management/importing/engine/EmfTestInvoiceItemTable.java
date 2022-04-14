package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class EmfTestInvoiceItemTable extends EmfObjectTable<EmfTestInvoiceItem, EmfTestInvoice> {

	public EmfTestInvoiceItemTable(IDbObjectTableBuilder<EmfTestInvoiceItem> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<EmfTestInvoiceItem, Integer, EmfTestInvoice> configuration) {

		configuration.setScopeField(EmfTestInvoiceItem.INVOICE);
		configuration.setBusinessKey(EmfTestInvoiceItem.UK_INVOICE_POSITION);
	}
}
