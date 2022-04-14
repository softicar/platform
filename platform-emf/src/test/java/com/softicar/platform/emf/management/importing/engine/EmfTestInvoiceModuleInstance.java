package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class EmfTestInvoiceModuleInstance extends EmfTestInvoiceModuleInstanceGenerated implements IEmfObject<EmfTestInvoiceModuleInstance> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}
}
