package com.softicar.platform.common.string.binary;

import java.util.Objects;

/**
 * Uses an heuristic approach to decide if a given array of bytes represents
 * text or binary data.
 * <p>
 * Since we do not know the encoding of the potential text, we can only use the
 * non-printable control characters of the ASCII character set as an indicator
 * for binary data.
 *
 * @author Oliver Richers
 */
public class BinaryOrTextDiscriminator {

	private final byte[] bytes;

	/**
	 * Constructs this instance to analyze the given array of bytes.
	 *
	 * @param bytes
	 *            the array of bytes (never <i>null</i>)
	 */
	public BinaryOrTextDiscriminator(byte[] bytes) {

		this.bytes = Objects.requireNonNull(bytes);
	}

	/**
	 * Tests the given array of bytes if it represents binary data or textual
	 * data.
	 *
	 * @return <i>true</i> if the given byte array is binary data; <i>false</i>
	 *         otherwise
	 */
	public boolean isBinary() {

		for (byte b: bytes) {
			if (!isPrintable(b)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests the given array of bytes if it represents binary data or textual
	 * data.
	 *
	 * @return <i>true</i> if the given byte array is textual data; <i>false</i>
	 *         otherwise
	 */
	public boolean isText() {

		return !isBinary();
	}

	private static boolean isPrintable(byte b) {

		if (b >= 0 && b < 32) {
			if (b == '\b' || b == '\f' || b == '\n' || b == '\r' || b == '\t') {
				return true;
			} else {
				return false;
			}
		}
		if (b == 127) {
			return false;
		} else {
			return true;
		}
	}
}
