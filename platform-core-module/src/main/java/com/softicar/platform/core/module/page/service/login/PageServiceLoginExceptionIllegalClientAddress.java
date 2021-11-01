package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;

public class PageServiceLoginExceptionIllegalClientAddress extends SofticarUserException {

	public PageServiceLoginExceptionIllegalClientAddress(String clientAddress) {

		super(CoreI18n.YOU_ARE_NOT_ALLOWED_TO_LOG_IN_FROM_THE_FOLLOWING_IP_ADDRESS_ARG1.toDisplay(clientAddress));
	}
}
