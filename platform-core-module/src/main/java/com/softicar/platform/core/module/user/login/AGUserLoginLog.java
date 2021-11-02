package com.softicar.platform.core.module.user.login;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGUserLoginLog extends AGUserLoginLogGenerated implements IEmfObject<AGUserLoginLog> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getUser().getLoginName() + " " + getLoginAt());
	}
}
