package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;

public class PageServiceLoginExceptionWrongUsernameOrPassword extends SofticarUserException {

	public PageServiceLoginExceptionWrongUsernameOrPassword() {

		super(CoreI18n.WRONG_USERNAME_OR_PASSWORD);
	}
}
