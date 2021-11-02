package com.softicar.platform.core.module.user.login.failure;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGUserLoginFailureLog extends AGUserLoginFailureLogGenerated implements IEmfObject<AGUserLoginFailureLog> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getUsername() + " " + getType() + " " + getLoginAt());
	}
}
