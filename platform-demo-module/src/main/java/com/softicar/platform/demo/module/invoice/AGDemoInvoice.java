package com.softicar.platform.demo.module.invoice;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.demo.module.invoice.type.AGDemoInvoiceTypeEnum;
import com.softicar.platform.demo.module.person.module.AGDemoPersonModuleInstance;
import com.softicar.platform.emf.object.IEmfObject;

public class AGDemoInvoice extends AGDemoInvoiceGenerated implements IEmfObject<AGDemoInvoice> {

	public static final DemoInvoiceGrossAmountField GROSS_AMOUNT_FIELD = new DemoInvoiceGrossAmountField();

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getInvoiceNumber());
	}

	boolean isInbound() {

		return getType() == AGDemoInvoiceTypeEnum.INBOUND.getRecord();
	}

	boolean isOutbound() {

		return getType() == AGDemoInvoiceTypeEnum.OUTBOUND.getRecord();
	}

	public AGDemoPersonModuleInstance getDemoPersonModuleInstance() {

		return getModuleInstance().getDemoPersonModuleInstance();
	}
}
