package com.softicar.platform.common.io.stream.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A {@link ByteArrayOutputStream} for testing.
 *
 * @author Oliver Richers
 */
public class TestOutputStream extends ByteArrayOutputStream {

	private int closeCount;

	public TestOutputStream() {

		this.closeCount = 0;
	}

	@Override
	public void close() throws IOException {

		closeCount++;
		super.close();
	}

	/**
	 * Asserts that this {@link OutputStream} was closed exactly once.
	 *
	 * @throws AssertionError
	 *             if this stream was not closed or was closed more than once
	 */
	public void assertWasClosedOnce() {

		if (closeCount == 0) {
			throw new AssertionError("Output stream was not closed.");
		}
		if (closeCount > 1) {
			throw new AssertionError("Output stream was closed multiple times.");
		}
	}
}
