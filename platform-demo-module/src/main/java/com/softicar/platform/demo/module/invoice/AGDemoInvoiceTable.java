package com.softicar.platform.demo.module.invoice;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.invoice.item.AGDemoInvoiceItem;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.form.tab.factory.EmfFormTabConfiguration;
import com.softicar.platform.emf.management.importing.engine.EmfImportItemsCollector;
import com.softicar.platform.emf.management.importing.engine.EmfImportItem;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoInvoiceTable extends EmfObjectTable<AGDemoInvoice, AGDemoModuleInstance> {

	public AGDemoInvoiceTable(IDbObjectTableBuilder<AGDemoInvoice> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoInvoice, Integer, AGDemoModuleInstance> configuration) {

		configuration.setScopeField(AGDemoInvoice.MODULE_INSTANCE);
		configuration.addValidator(DemoInvoiceValidator::new);
		configuration.setCreationPopupFactory(scope -> new DemoInvoiceCreationPopup(AGDemoInvoice.TABLE.createEntity(scope)));

		configuration.setBusinessKey(AGDemoInvoice.UK_MODULE_INSTANCE_INVOICE_NUMBER);
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
			.setPredicateVisibleEditableMandatory(DemoInvoicePredicates.INBOUND);

		attributes//
			.editAttribute(AGDemoInvoice.DEBITOR)
			.setPredicateVisibleEditableMandatory(DemoInvoicePredicates.OUTBOUND);
	}

	public static void main(String[] args) {

//		System.out.println(AGDemoInvoice.class);
//
//		EmfTableConfiguration<AGDemoInvoice, ?, ?> configuration = new EmfTableConfiguration<>(AGDemoInvoice.TABLE);
//
//		System.out.println(configuration.getBusinessKey().getFields());

//		new ColumnPrinter<>(AGDemoInvoiceItem.TABLE).printImportColumnNames();

		// Funktioniert:
//		new FkResolver<>(AGModuleInstance.TABLE, CoreModule.getModuleInstance()).resolve();

//		new FkResolver<>(AGDemoInvoice.TABLE).resolve(0);

		for (EmfImportItem importField: new EmfImportItemsCollector<>(AGDemoInvoiceItem.TABLE).getCsvFileItems()) {
			System.out.println(importField);
		}
	}

}
