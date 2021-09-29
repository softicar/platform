package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;

public class PageServiceLoginExceptionTooManyFailedLogins extends SofticarUserException {

	public PageServiceLoginExceptionTooManyFailedLogins(int loginFailureTimeout) {

		super(CoreI18n.TOO_MANY_FAILED_LOGIN_ATTEMPTS.concatSpace().concat(CoreI18n.PLEASE_WAIT_FOR_ARG1_SECONDS.toDisplay(loginFailureTimeout)));
	}
}
