package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;

public class DomInputException extends SofticarUserException {

	public DomInputException(IDisplayString message) {

		super(message);
	}

	public DomInputException(Throwable cause, IDisplayString message) {

		super(cause, message);
	}
}
