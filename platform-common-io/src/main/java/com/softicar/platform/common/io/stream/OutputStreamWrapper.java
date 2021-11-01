package com.softicar.platform.common.io.stream;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This class improves the {@link OutputStream} interface.
 * 
 * @author Oliver Richers
 */
public class OutputStreamWrapper implements Closeable {

	private final OutputStream outputStream;

	public OutputStreamWrapper(OutputStream outputStream) {

		this.outputStream = outputStream;
	}

	/**
	 * Closes the underlying stream.
	 */
	@Override
	public void close() {

		try {
			outputStream.close();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	/**
	 * Writes the specified byte into the underlying stream.
	 * 
	 * @param b
	 *            the byte to write
	 */
	public void write(byte b) {

		try {
			outputStream.write(b);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	/**
	 * Writes the specified bytes into the underlying stream.
	 * 
	 * @param bytes
	 *            the bytes to write
	 */
	public void write(byte[] bytes) {

		try {
			outputStream.write(bytes);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	/**
	 * Writes the specified bytes into the underlying stream.
	 * 
	 * @param bytes
	 *            the bytes to write
	 * @param offset
	 *            starting offset
	 * @param length
	 *            number of bytes to write
	 */
	public void write(byte[] bytes, int offset, int length) {

		try {
			outputStream.write(bytes, offset, length);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
