package com.softicar.platform.demo.invoice.module.invoice;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.demo.DemoI18n;
import com.softicar.platform.demo.invoice.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.invoice.module.invoice.item.AGDemoInvoiceItem;
import com.softicar.platform.demo.person.module.AGDemoPerson;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.form.tab.factory.EmfFormTabConfiguration;
import com.softicar.platform.emf.management.importing.EmfImportAction;
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
	public void customizeActionSet(EmfActionSet<AGDemoInvoice, AGDemoInvoiceModuleInstance> actionSet) {

		actionSet.addScopeAction(new EmfImportAction<>(this));
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

		attributes//
			.editAttribute(AGDemoInvoice.INVOICE_NUMBER)
			.setHelpDisplay(DemoI18n.ALPHANUMERICAL_CHARACTERS_AND_DASHES);

		attributes//
			.editAttribute(AGDemoInvoice.DOCUMENT)
			.setHelpDisplayByWikiText(DemoI18n.THE_DOCUMENT_THAT_IS_REPRESENTED_BY_THIS_RECORD);

		attributes//
			.editAttribute(AGDemoInvoice.ATTACHMENTS)
			.setHelpDisplayFactory(DemoInvoiceAttachmentsHelpElement::new);

		attributes//
			.editEntityAttribute(AGDemoInvoice.CONTACT)
			.setScope(it -> it.getDemoPersonModuleInstance(), AGDemoPerson::getModuleInstance);
	}
}
