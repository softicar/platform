package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;

public class PageServiceLoginExceptionMaintenanceInProgress extends SofticarUserException {

	public PageServiceLoginExceptionMaintenanceInProgress() {

		super(CoreI18n.CANNOT_LOGIN_DURING_MAINTENANCE);
	}
}
