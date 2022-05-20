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

	public InvoiceItemClerkColumnCreator() {

		invoiceItemClerkColumn = createEmfImportColumn(AGDemoInvoiceItem.CLERK);

		EmfImportColumn<R, ?> personModuleInstanceColumn = createEmfImportColumn(AGDemoPerson.MODULE_INSTANCE);
		invoiceItemClerkColumn.addParentColumn(personModuleInstanceColumn);

		EmfImportColumn<R, ?> personModuleInstanceTitleColumn = createEmfImportColumn(AGDemoPersonModuleInstance.TITLE);
		personModuleInstanceColumn.addParentColumn(personModuleInstanceTitleColumn);

		EmfImportColumn<R, ?> personIdentityCardNumberColumn = createEmfImportColumn(AGDemoPerson.IDENTITY_CARD_NUMBER);
		invoiceItemClerkColumn.addParentColumn(personIdentityCardNumberColumn);
	}

	private static <T, R extends IEmfTableRow<R, P>, P> T createEmfImportColumn(IDbField<R, ?> field) {

		return CastUtils.cast(new EmfImportColumn<>(field));
	}

	public <T> T getInvoiceItemClerkColumn() {

		return CastUtils.cast(invoiceItemClerkColumn);
	}
}
