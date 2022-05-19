package com.softicar.platform.demo.invoice.module.paid;

import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.trait.table.EmfTraitTable;

public class AGDemoInvoicePaymentTable extends EmfTraitTable<AGDemoInvoicePayment, AGDemoInvoice> {

	protected AGDemoInvoicePaymentTable(IDbTableBuilder<AGDemoInvoicePayment, AGDemoInvoice> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoInvoicePayment, AGDemoInvoice, AGDemoInvoice> configuration) {

		configuration.setFormFactory(DemoInvoicePaymentForm::new);
	}
}
