package com.softicar.platform.common.io.buffer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * A wrapper around the primitive type <i>byte[]</i>.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class ByteBuffer {

	private final byte[] bytes;

	/**
	 * Constructs a new {@link ByteBuffer} from the supplied
	 * {@link InputStream}.
	 *
	 * @param inputStreamSupplier
	 *            a {@link Supplier} of the {@link InputStream} from which the
	 *            bytes shall be read (never <i>null</i>)
	 */
	public ByteBuffer(Supplier<InputStream> inputStreamSupplier) {

		this(readAllBytes(inputStreamSupplier));
	}

	/**
	 * Constructs a new {@link ByteBuffer} from the given bytes.
	 *
	 * @param bytes
	 *            the bytes to buffer (never <i>null</i>)
	 */
	public ByteBuffer(byte[] bytes) {

		this.bytes = bytes;
	}

	/**
	 * Returns the bytes.
	 *
	 * @return the bytes as <i>byte[]</i> (never <i>null</i>)
	 */
	public byte[] getBytes() {

		return bytes;
	}

	/**
	 * Returns a {@link ByteArrayInputStream} for the content.
	 * <p>
	 * The caller is obliged to close the returned {@link InputStream}.
	 *
	 * @return a new {@link ByteArrayInputStream}, created from the buffered
	 *         bytes (never <i>null</i>)
	 */
	public InputStream getInputStream() {

		return new ByteArrayInputStream(this.bytes);
	}

	private static byte[] readAllBytes(Supplier<InputStream> inputStreamSupplier) {

		try (var inputStream = inputStreamSupplier.get()) {
			return inputStream.readAllBytes();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
