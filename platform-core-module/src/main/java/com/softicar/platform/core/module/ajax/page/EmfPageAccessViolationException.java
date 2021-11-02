package com.softicar.platform.core.module.ajax.page;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.page.IEmfPage;

public class EmfPageAccessViolationException extends SofticarUserException {

	public EmfPageAccessViolationException() {

		super(CoreI18n.YOU_DO_NOT_HAVE_THE_PERMISSION_TO_ACCESS_THIS_PAGE);
	}

	public EmfPageAccessViolationException(IEmfPage<?> page) {

		super(CoreI18n.YOU_DO_NOT_HAVE_THE_PERMISSION_TO_ACCESS_PAGE_ARG1.toDisplay(page.getTitle()));
	}
}
