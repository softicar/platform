package com.softicar.platform.core.module.email.message;

import com.softicar.platform.common.core.exceptions.SofticarException;

public class IllegalEmailMessageIdException extends SofticarException {

	public IllegalEmailMessageIdException(String message, Object...arguments) {

		super(message, arguments);
	}
}
