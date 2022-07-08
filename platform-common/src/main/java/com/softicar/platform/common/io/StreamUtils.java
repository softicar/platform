package com.softicar.platform.common.io;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.reader.InputStreamReaderFactory;
import com.softicar.platform.common.io.reader.ManagedReader;
import com.softicar.platform.common.io.stream.copy.StreamCopy;
import com.softicar.platform.common.io.writer.ManagedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * Utility methods for input and output streams.
 *
 * @author Oliver Richers
 */
public class StreamUtils {

	public static final int LINE_FEED = 0x0A;
	public static final int CARRIAGE_RETURN = 0x0D;
	private static final int BUFFER_SIZE = 256 * 1024;
	private static final int MASK_00111111 = (1 << 6) - 1;
	private static final int MASK_10000000 = 1 << 7;
	private static final int MASK_01000000 = 1 << 6;
	private static final int MASK_11000000 = MASK_10000000 + MASK_01000000;

	public static void close(Closeable closeable) {

		try {
			closeable.close();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	public static void copy(InputStream input, MessageDigest md) {

		try {
			int n;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((n = input.read(buffer)) != -1) {
				md.update(buffer, 0, n);
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	public static void copy(InputStream input, OutputStream output) {

		new StreamCopy(input, output).copyAndFlush();
	}

	public static void copyAndClose(InputStream input, OutputStream output) {

		new StreamCopy(input, output).copyAndClose();
	}

	/**
	 * Reads all data from the given input stream into a byte array.
	 * <p>
	 * The input stream is automatically closed after copying the data.
	 *
	 * @param inputStream
	 *            the input stream to read from (never null)
	 * @return the byte array containing all data
	 */
	public static byte[] toByteArray(InputStream inputStream) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		copyAndClose(inputStream, outputStream);
		return outputStream.toByteArray();
	}

	/**
	 * Reads all text from the given input stream into a {@link String}.
	 * <p>
	 * The input stream is automatically closed after copying the data.
	 *
	 * @param inputStream
	 *            the input stream to read from (never null)
	 * @param characterSet
	 *            the {@link Charset} to use (never null)
	 * @return the {@link String} containing all text
	 */
	public static String toString(InputStream inputStream, Charset characterSet) {

		StringBuilder builder = new StringBuilder();
		try (InputStreamReader reader = InputStreamReaderFactory.read(inputStream, characterSet)) {
			new ManagedWriter(builder).write(new ManagedReader(reader));
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
		return builder.toString();
	}

	public static int readUTF8CodePoint(InputStream inputStream) throws IOException {

		int start = inputStream.read();
		if (start != -1) {
			if ((start & MASK_10000000) == 0) {
				return start;
			} else if ((start & MASK_01000000) == 0) {
				throw new SofticarException("Invalid UTF-8 start byte %s. Need two leading 1 bits.", Integer.toBinaryString(start));
			} else {
				// get number of bytes to read
				int n = 0;
				for (int i = 5; i >= 3; --i) {
					if ((start & 1 << i) == 0) {
						n = 7 - i;
						break;
					}
				}

				// check number of bytes
				if (n == 0) {
					throw new SofticarException("Invalid UTF-8 start byte %s. Too many leading 1 bits.", Integer.toBinaryString(start));
				}

				// create code point
				int result = start & (1 << 8 - n - 1) - 1;
				for (int i = 1; i < n; ++i) {
					int tmp = inputStream.read();

					if (tmp == -1) {
						throw new SofticarException("End of stream reached while reading UTF-8 code point.");
					} else if ((tmp & MASK_11000000) != MASK_10000000) {
						throw new SofticarException("Invalid UTF-8 byte %s. The two leading bits must be 10.", Integer.toBinaryString(tmp));
					} else {
						result = result << 6 | tmp & MASK_00111111;
					}
				}
				return result;
			}
		} else {
			return -1;
		}
	}

	public static String readUTF8Until(InputStream inputStream, int codePoint) {

		StringBuilder line = new StringBuilder();
		try {
			while (true) {
				int c = readUTF8CodePoint(inputStream);

				if (c == -1) {
					return line.length() > 0? line.toString() : null;
				}

				if (c == codePoint) {
					return line.toString();
				}

				line.appendCodePoint(c);
			}
		} catch (Exception exception) {
			throw new SofticarException(exception, "Error while decoding UTF-8 stream. Text so far: '%s'.", line.toString());
		}
	}

	public static String readUTF8Definitely(InputStream inputStream, int count) {

		StringBuilder line = new StringBuilder();
		try {
			for (int i = 0; i < count; ++i) {
				int c = readUTF8CodePoint(inputStream);

				if (c == -1) {
					throw new SofticarException("Unexpected end of stream. Wanted to read %d characters but got only %d: '%s'.", count, i, line.toString());
				}

				line.appendCodePoint(c);
			}
			return line.toString();
		} catch (Exception exception) {
			throw new SofticarException(exception, "Error while decoding UTF-8 stream. Text so far: '%s'.", line.toString());
		}
	}
}
