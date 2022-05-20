package com.softicar.platform.demo.invoice.module.invoice.importing;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.demo.invoice.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.invoice.module.invoice.item.AGDemoInvoiceItem;
import com.softicar.platform.emf.management.importing.engine.EmfImportColumn;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class InvoiceItemInvoiceColumnCreator<R extends IEmfTableRow<R, P>, P> {

	private final EmfImportColumn<R, ?> invoiceItemInvoiceColumn;
	private final EmfImportColumn<R, ?> invoiceModuleInstanceColumn;
	private final EmfImportColumn<R, ?> invoiceModuleInstanceTitleColumn;
	private final EmfImportColumn<R, ?> invoiceCreditorColumn;
	private final EmfImportColumn<R, ?> invoiceDebtorColumn;
	private final EmfImportColumn<R, ?> invoiceInvoiceNumberColumn;
	private final EmfImportColumn<R, ?> invoiceInvoiceDateColumn;

	public InvoiceItemInvoiceColumnCreator() {

		invoiceItemInvoiceColumn = createEmfImportColumn(AGDemoInvoiceItem.INVOICE);

		invoiceModuleInstanceColumn = createEmfImportColumn(AGDemoInvoice.MODULE_INSTANCE);
		invoiceItemInvoiceColumn.addParentColumn(invoiceModuleInstanceColumn);

		invoiceModuleInstanceTitleColumn = createEmfImportColumn(AGDemoInvoiceModuleInstance.TITLE);
		invoiceModuleInstanceColumn.addParentColumn(invoiceModuleInstanceTitleColumn);

		invoiceCreditorColumn = createEmfImportColumn(AGDemoInvoice.CREDITOR);
		invoiceItemInvoiceColumn.addParentColumn(invoiceCreditorColumn);

		invoiceDebtorColumn = createEmfImportColumn(AGDemoInvoice.DEBTOR);
		invoiceItemInvoiceColumn.addParentColumn(invoiceDebtorColumn);

		invoiceInvoiceNumberColumn = createEmfImportColumn(AGDemoInvoice.INVOICE_NUMBER);
		invoiceItemInvoiceColumn.addParentColumn(invoiceInvoiceNumberColumn);

		invoiceInvoiceDateColumn = createEmfImportColumn(AGDemoInvoice.INVOICE_DATE);
		invoiceItemInvoiceColumn.addParentColumn(invoiceInvoiceDateColumn);
	}

	private static <T, R extends IEmfTableRow<R, P>, P> T createEmfImportColumn(IDbField<R, ?> field) {

		return CastUtils.cast(new EmfImportColumn<>(field));
	}

	public InvoiceItemInvoiceColumnCreator<R, P> setInvoiceModuleInstanceTitleColumnValue(String value) {

		invoiceModuleInstanceTitleColumn.setValue(value);
		return this;
	}

	public InvoiceItemInvoiceColumnCreator<R, P> setInvoiceCreditorColumnValue(String value) {

		invoiceCreditorColumn.setValue(value);
		return this;
	}

	public InvoiceItemInvoiceColumnCreator<R, P> setInvoiceDebtorColumnValue(String value) {

		invoiceDebtorColumn.setValue(value);
		return this;
	}

	public InvoiceItemInvoiceColumnCreator<R, P> setInvoiceInvoiceNumberValue(String value) {

		invoiceInvoiceNumberColumn.setValue(value);
		return this;
	}

	public InvoiceItemInvoiceColumnCreator<R, P> setInvoiceInvoiceDateValue(String value) {

		invoiceInvoiceDateColumn.setValue(value);
		return this;
	}

	public <T> T getInvoiceItemInvoiceColumn() {

		return CastUtils.cast(invoiceItemInvoiceColumn);
	}

	public EmfImportColumn<R, ?> getInvoiceModuleInstanceColumn() {

		return invoiceModuleInstanceColumn;
	}
}
