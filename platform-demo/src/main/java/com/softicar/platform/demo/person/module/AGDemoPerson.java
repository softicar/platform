package com.softicar.platform.demo.person.module;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGDemoPerson extends AGDemoPersonGenerated implements IEmfObject<AGDemoPerson> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getLastName() + ", " + getFirstName() + " :: " + getIdentityCardNumber());
	}
}
