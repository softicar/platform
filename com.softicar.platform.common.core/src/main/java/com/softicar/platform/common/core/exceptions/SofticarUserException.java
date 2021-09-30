package com.softicar.platform.common.core.exceptions;

import com.softicar.platform.common.core.i18n.IDisplayString;

/**
 * This exception should be thrown on user errors, e.g. input errors.
 * <p>
 * Error messages shown to users should be localized and translated, that is why
 * this class only takes {@link IDisplayString}.
 *
 * @author Oliver Richers
 */
public class SofticarUserException extends SofticarException {

	private static final long serialVersionUID = 1L;

	public SofticarUserException(IDisplayString message) {

		super(message.toString());
	}

	public SofticarUserException(Throwable cause, IDisplayString message) {

		super(cause, message.toString());
	}
}
