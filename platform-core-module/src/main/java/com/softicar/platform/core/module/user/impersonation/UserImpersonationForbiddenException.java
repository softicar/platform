package com.softicar.platform.core.module.user.impersonation;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.page.IEmfPage;

public class UserImpersonationForbiddenException extends SofticarUserException {

	public UserImpersonationForbiddenException(IEmfPage<?> page) {

		super(CoreI18n.USER_IMPERSONATION_IS_FORBIDDEN_FOR_PAGE_ARG1.toDisplay(page.getTitle()));
	}
}
