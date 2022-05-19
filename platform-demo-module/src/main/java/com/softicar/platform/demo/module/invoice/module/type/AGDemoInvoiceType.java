package com.softicar.platform.demo.module.invoice.module.type;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.demo.module.DemoI18n;

public class AGDemoInvoiceType extends AGDemoInvoiceTypeGenerated implements IEntity {

	@Override
	public IDisplayString toDisplayWithoutId() {

		switch (getEnum()) {
		case INBOUND:
			return DemoI18n.INBOUND;
		case OUTBOUND:
			return DemoI18n.OUTBOUND;
		}
		throw new SofticarUnknownEnumConstantException(getEnum());
	}

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}
}
