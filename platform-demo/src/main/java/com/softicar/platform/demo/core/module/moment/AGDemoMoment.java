package com.softicar.platform.demo.core.module.moment;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGDemoMoment extends AGDemoMomentGenerated implements IEmfObject<AGDemoMoment> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getPointInTime().toString());
	}
}
