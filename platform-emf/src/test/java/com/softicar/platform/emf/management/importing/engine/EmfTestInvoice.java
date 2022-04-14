package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class EmfTestInvoice extends EmfTestInvoiceGenerated implements IEmfObject<EmfTestInvoice> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getInvoiceModuleInstance().toDisplay() + "::" + getBusinessPartner().toDisplay() + "::" + getNumber());
	}
}
