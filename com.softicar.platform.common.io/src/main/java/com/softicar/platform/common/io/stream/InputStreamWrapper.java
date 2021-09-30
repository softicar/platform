package com.softicar.platform.common.io.stream;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class improves the {@link InputStream} interface.
 * 
 * @author Oliver Richers
 */
public class InputStreamWrapper implements Closeable {

	private final InputStream inputStream;

	public InputStreamWrapper(InputStream inputStream) {

		this.inputStream = inputStream;
	}

	/**
	 * Closes the underlying stream.
	 */
	@Override
	public void close() {

		try {
			inputStream.close();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	/**
	 * Tries to read a byte from the underlying stream.
	 * <p>
	 * This method works analogous to the method {@link InputStream#read()}.
	 * 
	 * @return the read byte or -1 if the end of stream was reached
	 */
	public int probe() {

		try {
			return inputStream.read();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	/**
	 * Tries to read bytes from the underlying stream.
	 * <p>
	 * This method works analogous to the method {@link InputStream#read()}.
	 * 
	 * @return the read byte or -1 if the end of stream was reached
	 */
	public int probe(byte[] bytes, int offset, int length) {

		try {
			return inputStream.read(bytes, offset, length);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	/**
	 * Reads a byte from the underlying stream.
	 * <p>
	 * If no byte can be read because the end of the stream is reached, this
	 * will throw an exception.
	 * 
	 * @return the read byte
	 * @throws EndOfStreamException
	 *             if no byte could be read
	 */
	public byte read() {

		int value = probe();
		if (value < 0) {
			throw new EndOfStreamException();
		}
		return (byte) value;
	}

	/**
	 * Reads bytes from the underlying stream into the given byte array.
	 * <p>
	 * This method works analogous to {@link #read(byte[], int, int)} with an
	 * offset of zero.
	 * 
	 * @param bytes
	 *            the byte array to fill
	 */
	public void read(byte[] bytes) {

		read(bytes, 0, bytes.length);
	}

	/**
	 * Reads bytes from the underlying stream into the given byte array.
	 * <p>
	 * This method blocks until the complete array has been filled. If the end
	 * of the stream is reached before all bytes have been read, this will throw
	 * an exception.
	 * 
	 * @param bytes
	 *            the byte array to fill
	 * @param offset
	 *            the offset from where to fill the array
	 * @param length
	 *            the number of bytes to read from the stream
	 * @throws EndOfStreamException
	 *             if the end of the stream is reached before all bytes have
	 *             been read
	 */
	public void read(byte[] bytes, int offset, int length) {

		while (length > 0) {
			int n = probe(bytes, offset, length);
			if (n < 0) {
				throw new EndOfStreamException();
			} else {
				offset += n;
				length -= n;
			}
		}
	}

	/**
	 * Reads the specified amount of bytes from the underlying stream.
	 * 
	 * @param count
	 *            the amount of bytes to read
	 * @return the read bytes
	 * @throws EndOfStreamException
	 *             if the end of the stream is reached before all bytes have
	 *             been read
	 */
	public byte[] read(int count) {

		byte[] bytes = new byte[count];
		read(bytes);
		return bytes;
	}
}
