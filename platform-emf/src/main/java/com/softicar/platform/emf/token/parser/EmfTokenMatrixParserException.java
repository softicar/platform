package com.softicar.platform.emf.token.parser;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;

/**
 * Thrown if {@link EmfTokenMatrixParser} encounters a problem.
 * <p>
 * Instances are created via {@link EmfTokenMatrixParserExceptionBuilder}.
 *
 * @author Alexander Schmidt
 */
public class EmfTokenMatrixParserException extends SofticarUserException {

	EmfTokenMatrixParserException(IDisplayString message) {

		super(message);
	}

	EmfTokenMatrixParserException(Throwable cause, IDisplayString message) {

		super(cause, message);
	}
}
