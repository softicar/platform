package com.softicar.platform.demo.module.business.unit.partner;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGDemoBusinessPartner extends AGDemoBusinessPartnerGenerated implements IEmfObject<AGDemoBusinessPartner> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName() + " :: " + getVatId());
	}
}
