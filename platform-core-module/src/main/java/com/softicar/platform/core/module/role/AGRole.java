package com.softicar.platform.core.module.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGRole extends AGRoleGenerated implements IEmfObject<AGRole> {

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}
}
