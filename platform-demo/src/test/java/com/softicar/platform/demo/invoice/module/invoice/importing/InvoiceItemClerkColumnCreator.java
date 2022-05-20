package com.softicar.platform.demo.invoice.module.invoice.importing;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.demo.invoice.module.invoice.item.AGDemoInvoiceItem;
import com.softicar.platform.demo.person.module.AGDemoPerson;
import com.softicar.platform.demo.person.module.AGDemoPersonModuleInstance;
import com.softicar.platform.emf.management.importing.engine.EmfImportColumn;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class InvoiceItemClerkColumnCreator<R extends IEmfTableRow<R, P>, P> {

	private final EmfImportColumn<R, ?> invoiceItemClerkColumn;
	private final EmfImportColumn<R, ?> personModuleInstanceColumn;
	private final EmfImportColumn<R, ?> personModuleInstanceTitleColumn;
	private final EmfImportColumn<R, ?> personIdentityCardNumberColumn;

	public InvoiceItemClerkColumnCreator() {

		invoiceItemClerkColumn = createEmfImportColumn(AGDemoInvoiceItem.CLERK);

		personModuleInstanceColumn = createEmfImportColumn(AGDemoPerson.MODULE_INSTANCE);
		invoiceItemClerkColumn.addParentColumn(personModuleInstanceColumn);

		personModuleInstanceTitleColumn = createEmfImportColumn(AGDemoPersonModuleInstance.TITLE);
		personModuleInstanceColumn.addParentColumn(personModuleInstanceTitleColumn);

		personIdentityCardNumberColumn = createEmfImportColumn(AGDemoPerson.IDENTITY_CARD_NUMBER);
		invoiceItemClerkColumn.addParentColumn(personIdentityCardNumberColumn);
	}

	private static <T, R extends IEmfTableRow<R, P>, P> T createEmfImportColumn(IDbField<R, ?> field) {

		return CastUtils.cast(new EmfImportColumn<>(field));
	}

	public InvoiceItemClerkColumnCreator<R, P> setInvoiceModuleInstanceTitleColumnValue(String value) {

		personModuleInstanceTitleColumn.setValue(value);
		return this;
	}

	public InvoiceItemClerkColumnCreator<R, P> setPersonIdentityCardNumberColumnValue(String value) {

		personIdentityCardNumberColumn.setValue(value);
		return this;
	}

	public <T> T getInvoiceItemClerkColumn() {

		return CastUtils.cast(invoiceItemClerkColumn);
	}

//	public EmfImportColumn<R, ?> getInvoiceInvoiceModuleInstanceColumn() {
//
//		return personModuleInstanceColumn;
//	}

//	public EmfImportColumn<R, ?> getInvoicePartnerColumn() {
//
//		return invoiceCreditorColumn;
//	}
}
