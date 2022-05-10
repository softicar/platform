package com.softicar.platform.demo.module.invoice;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.demo.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.module.invoice.item.AGDemoInvoiceItem;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.form.tab.factory.EmfFormTabConfiguration;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoInvoiceTable extends EmfObjectTable<AGDemoInvoice, AGDemoInvoiceModuleInstance> {

	public AGDemoInvoiceTable(IDbObjectTableBuilder<AGDemoInvoice> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoInvoice, Integer, AGDemoInvoiceModuleInstance> configuration) {

		configuration.setScopeField(AGDemoInvoice.MODULE_INSTANCE);
		configuration.addValidator(DemoInvoiceValidator::new);
		configuration.setFormFactory(DemoInvoiceForm::new);
		configuration.setBusinessKey(AGDemoInvoice.UK_MODULE_INSTANCE_CREDITOR_DEBTOR_INVOICE_NUMBER_INVOICE_DATE);
	}

	@Override
	public void customizeFormTabs(EmfFormTabConfiguration<AGDemoInvoice> tabConfiguration) {

		tabConfiguration.addManagementTab(AGDemoInvoiceItem.TABLE);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGDemoInvoice> attributes) {

		attributes.addTransientAttribute(AGDemoInvoice.GROSS_AMOUNT_FIELD);

		attributes//
			.editAttribute(AGDemoInvoice.CREDITOR)
			.setConditionallyRequired(DemoInvoicePredicates.INBOUND, "");

		attributes//
			.editAttribute(AGDemoInvoice.DEBTOR)
			.setConditionallyRequired(DemoInvoicePredicates.OUTBOUND, "");
	}
}
