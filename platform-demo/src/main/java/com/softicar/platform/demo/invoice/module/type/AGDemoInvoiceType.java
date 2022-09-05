package com.softicar.platform.demo.invoice.module.type;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;

public class AGDemoInvoiceType extends AGDemoInvoiceTypeGenerated implements IEntity {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getEnum().toDisplay();
	}

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}
}
