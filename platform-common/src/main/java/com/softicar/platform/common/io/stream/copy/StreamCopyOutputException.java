package com.softicar.platform.common.io.stream.copy;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.IOException;

public class StreamCopyOutputException extends SofticarIOException {

	public StreamCopyOutputException(IOException exception) {

		super(exception);
	}
}
