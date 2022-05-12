package com.softicar.platform.demo.module.invoice.item;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.demo.module.business.unit.AGDemoBusinessUnitModuleInstance;
import com.softicar.platform.emf.object.IEmfObject;

public class AGDemoInvoiceItem extends AGDemoInvoiceItemGenerated implements IEmfObject<AGDemoInvoiceItem> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getItem());
	}

	public AGDemoBusinessUnitModuleInstance getDemoBusinessUnitModuleInstance() {

		return getInvoice().getDemoBusinessUnitModuleInstance();
	}
}
