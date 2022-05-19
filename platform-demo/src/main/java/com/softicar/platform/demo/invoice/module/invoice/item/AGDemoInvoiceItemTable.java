package com.softicar.platform.demo.invoice.module.invoice.item;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.invoice.module.invoice.DemoInvoicePredicates;
import com.softicar.platform.demo.person.module.AGDemoPerson;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoInvoiceItemTable extends EmfObjectTable<AGDemoInvoiceItem, AGDemoInvoice> {

	public AGDemoInvoiceItemTable(IDbObjectTableBuilder<AGDemoInvoiceItem> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoInvoiceItem, Integer, AGDemoInvoice> configuration) {

		configuration.setScopeField(AGDemoInvoiceItem.INVOICE);
		configuration.setCreationPredicate(DemoInvoicePredicates.NOT_LOCKED_ITEMS);
		configuration.setEditPredicate(DemoInvoiceItemPredicates.NOT_LOCKED_ITEMS);
		configuration.setBusinessKey(AGDemoInvoiceItem.UK_INVOICE_ITEM);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGDemoInvoiceItem> attributes) {

		attributes//
			.editEntityAttribute(AGDemoInvoiceItem.CLERK)
			.setScope(it -> it.getDemoPersonModuleInstance(), AGDemoPerson::getModuleInstance);
	}
}
