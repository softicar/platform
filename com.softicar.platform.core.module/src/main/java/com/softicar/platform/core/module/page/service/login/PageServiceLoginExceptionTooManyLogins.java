package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;

public class PageServiceLoginExceptionTooManyLogins extends SofticarUserException {

	public PageServiceLoginExceptionTooManyLogins(int maximumLoginsPeriod) {

		super(CoreI18n.TOO_MANY_LOGIN_ATTEMPTS.concatSpace().concat(CoreI18n.PLEASE_WAIT_FOR_ARG1_SECONDS.toDisplay(maximumLoginsPeriod)));
	}
}
