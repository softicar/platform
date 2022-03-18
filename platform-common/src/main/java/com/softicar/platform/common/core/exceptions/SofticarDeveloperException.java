package com.softicar.platform.common.core.exceptions;

/**
 * This exception should be thrown on logic errors.
 * <p>
 * Logic errors are caused by erroneous code, and are aimed to be received by
 * developers and not users.
 * <p>
 * An example of a logic error is {@link SofticarUnknownEnumConstantException}.
 *
 * @author Oliver Richers
 */
public class SofticarDeveloperException extends SofticarException {

	private static final long serialVersionUID = 1L;

	public SofticarDeveloperException(String message) {

		super(message);
	}

	public SofticarDeveloperException(String format, Object...args) {

		super(format, args);
	}

	public SofticarDeveloperException(Throwable cause) {

		super(cause);
	}

	public SofticarDeveloperException(Throwable cause, String message) {

		super(cause, message);
	}

	public SofticarDeveloperException(Throwable cause, String format, Object...args) {

		super(cause, format, args);
	}
}
