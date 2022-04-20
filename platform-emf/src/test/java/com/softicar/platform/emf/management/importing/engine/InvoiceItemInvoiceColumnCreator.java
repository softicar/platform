package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class InvoiceItemInvoiceColumnCreator<R extends IEmfTableRow<R, P>, P> {

	private final EmfImportColumn<R, ?> invoiceItemInvoiceColumn;

	private final EmfImportColumn<R, ?> invoiceModuleInstanceNameColumn;
	private final EmfImportColumn<R, ?> businessUnitModuleInstanceNameColumn;
	private final EmfImportColumn<R, ?> businessPartnerVatIdColumn;
	private final EmfImportColumn<R, ?> invoiceNumberColumn;

	public InvoiceItemInvoiceColumnCreator() {

		invoiceItemInvoiceColumn = createEmfImportColumn(EmfTestInvoiceItem.INVOICE);

		EmfImportColumn<R, ?> invoiceInvoiceModuleInstanceColumn = createEmfImportColumn(EmfTestInvoice.INVOICE_MODULE_INSTANCE);
		invoiceItemInvoiceColumn.addParentColumn(invoiceInvoiceModuleInstanceColumn);

		invoiceModuleInstanceNameColumn = createEmfImportColumn(EmfTestInvoiceModuleInstance.NAME);
		invoiceInvoiceModuleInstanceColumn.addParentColumn(invoiceModuleInstanceNameColumn);

		EmfImportColumn<R, ?> invoicePartnerColumn = createEmfImportColumn(EmfTestInvoice.PARTNER);
		invoiceItemInvoiceColumn.addParentColumn(invoicePartnerColumn);

		EmfImportColumn<R, ?> businessPartnerBusinessUnitModuleInstanceColumn = createEmfImportColumn(EmfTestBusinessPartner.BUSINESS_UNIT_MODULE_INSTANCE);
		invoicePartnerColumn.addParentColumn(businessPartnerBusinessUnitModuleInstanceColumn);

		businessUnitModuleInstanceNameColumn = createEmfImportColumn(EmfTestBusinessUnitModuleInstance.NAME);
		businessPartnerBusinessUnitModuleInstanceColumn.addParentColumn(businessUnitModuleInstanceNameColumn);

		businessPartnerVatIdColumn = createEmfImportColumn(EmfTestBusinessPartner.VAT_ID);
		invoicePartnerColumn.addParentColumn(businessPartnerVatIdColumn);

		invoiceNumberColumn = createEmfImportColumn(EmfTestInvoice.NUMBER);
		invoiceItemInvoiceColumn.addParentColumn(invoiceNumberColumn);
	}

	private static <T, R extends IEmfTableRow<R, P>, P> T createEmfImportColumn(IDbField<R, ?> field) {

		return CastUtils.cast(new EmfImportColumn<>(field));
	}

	public InvoiceItemInvoiceColumnCreator<R, P> setInvoiceModuleInstanceNameColumnValue(String value) {

		invoiceModuleInstanceNameColumn.setValue(value);
		return this;
	}

	public InvoiceItemInvoiceColumnCreator<R, P> setBusinessUnitModuleInstanceNameColumnValue(String value) {

		businessUnitModuleInstanceNameColumn.setValue(value);
		return this;
	}

	public InvoiceItemInvoiceColumnCreator<R, P> setBusinessPartnerVatIdColumnValue(String value) {

		businessPartnerVatIdColumn.setValue(value);
		return this;
	}

	public InvoiceItemInvoiceColumnCreator<R, P> setInvoiceNumberColumnValue(String value) {

		invoiceNumberColumn.setValue(value);
		return this;
	}

	public <T> T getInvoiceItemInvoiceColumn() {

		return CastUtils.cast(invoiceItemInvoiceColumn);
	}
}
