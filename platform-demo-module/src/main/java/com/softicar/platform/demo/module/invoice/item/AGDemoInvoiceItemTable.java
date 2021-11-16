package com.softicar.platform.demo.module.invoice.item;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.demo.module.invoice.AGDemoInvoice;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoInvoiceItemTable extends EmfObjectTable<AGDemoInvoiceItem, AGDemoInvoice> {

	public AGDemoInvoiceItemTable(IDbObjectTableBuilder<AGDemoInvoiceItem> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoInvoiceItem, Integer, AGDemoInvoice> configuration) {

		configuration.setScopeField(AGDemoInvoiceItem.INVOICE);
	}
}
