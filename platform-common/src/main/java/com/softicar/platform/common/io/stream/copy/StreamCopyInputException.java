package com.softicar.platform.common.io.stream.copy;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.IOException;

public class StreamCopyInputException extends SofticarIOException {

	public StreamCopyInputException(IOException exception) {

		super(exception);
	}
}
