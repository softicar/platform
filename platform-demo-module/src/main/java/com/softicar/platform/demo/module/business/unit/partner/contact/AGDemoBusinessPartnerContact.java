package com.softicar.platform.demo.module.business.unit.partner.contact;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.demo.module.business.unit.AGDemoBusinessUnitModuleInstance;
import com.softicar.platform.emf.object.IEmfObject;

public class AGDemoBusinessPartnerContact extends AGDemoBusinessPartnerContactGenerated implements IEmfObject<AGDemoBusinessPartnerContact> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getLastName() + ", " + getFirstName() + " :: " + getEmployeeId());
	}

	public AGDemoBusinessUnitModuleInstance getModuleInstance() {

		return getBusinessPartner().getModuleInstance();
	}
}
