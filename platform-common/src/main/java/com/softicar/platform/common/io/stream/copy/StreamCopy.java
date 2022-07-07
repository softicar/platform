package com.softicar.platform.common.io.stream.copy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copies an {@link InputStream} to an {@link OutputStream}.
 * <p>
 * This class aims to allow the distinction between output and input exceptions.
 *
 * @see StreamCopyInputException
 * @see StreamCopyOutputException
 * @author Oliver Richers
 */
public class StreamCopy {

	private static final int DEFAULT_BUFFER_SIZE = 256 * 1024;
	private final InputStream input;
	private final OutputStream output;
	private int bufferSize;

	public StreamCopy(InputStream input, OutputStream output) {

		this.input = input;
		this.output = output;
		this.bufferSize = DEFAULT_BUFFER_SIZE;
	}

	public StreamCopy setBufferSize(int bufferSize) {

		if (bufferSize < 1) {
			throw new IllegalArgumentException("Buffer size must be at least 1.");
		}

		this.bufferSize = bufferSize;
		return this;
	}

	public void copyAndFlush() {

		new Copier().copy();
		flushOutput();
	}

	public void copyAndClose() {

		try (var inputCloser = new InputCloser(); var outputCloser = new OutputCloser()) {
			new Copier().copy();
		}
	}

	private void closeInput() {

		try {
			input.close();
		} catch (IOException exception) {
			throw new StreamCopyInputException(exception);
		}
	}

	private void closeOutput() {

		try {
			output.close();
		} catch (IOException exception) {
			throw new StreamCopyOutputException(exception);
		}
	}

	private void flushOutput() {

		try {
			output.flush();
		} catch (IOException exception) {
			throw new StreamCopyOutputException(exception);
		}
	}

	private class Copier {

		private final byte[] buffer;
		private int count;

		public Copier() {

			this.buffer = new byte[bufferSize];
			this.count = 0;
		}

		public void copy() {

			while (read()) {
				write();
			}
		}

		private boolean read() {

			try {
				count = input.read(buffer);
				return count != -1;
			} catch (IOException exception) {
				throw new StreamCopyInputException(exception);
			}
		}

		private void write() {

			try {
				output.write(buffer, 0, count);
			} catch (IOException exception) {
				throw new StreamCopyOutputException(exception);
			}
		}
	}

	private class InputCloser implements AutoCloseable {

		@Override
		public void close() {

			closeInput();
		}
	}

	private class OutputCloser implements AutoCloseable {

		@Override
		public void close() {

			closeOutput();
		}
	}
}
