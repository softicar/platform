package com.softicar.platform.common.io.stream;

import com.softicar.platform.common.core.exceptions.SofticarException;

public class EndOfStreamException extends SofticarException {

	private static final long serialVersionUID = -4452384720121767666L;

	public EndOfStreamException() {

		super("Unexpected end of stream.");
	}
}
