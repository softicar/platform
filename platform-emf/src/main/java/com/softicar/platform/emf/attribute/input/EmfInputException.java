package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;

public class EmfInputException extends SofticarUserException {

	public EmfInputException(IDisplayString message) {

		super(message);
	}

	public EmfInputException(Throwable cause, IDisplayString message) {

		super(cause, message);
	}
}
