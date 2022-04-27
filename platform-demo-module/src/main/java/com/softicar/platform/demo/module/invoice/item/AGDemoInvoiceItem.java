package com.softicar.platform.demo.module.invoice.item;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.management.importing.engine.EmfImportColumnsStructure;
import com.softicar.platform.emf.object.IEmfObject;

public class AGDemoInvoiceItem extends AGDemoInvoiceItemGenerated implements IEmfObject<AGDemoInvoiceItem> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getItem());
	}

	public static void main(String[] args) {

		new EmfImportColumnsStructure<>(AGDemoInvoiceItem.TABLE).getTableColumns();
	}
}
