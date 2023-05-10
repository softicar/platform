package com.softicar.platform.common.string.hash;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.string.hexadecimal.Hexadecimal;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Supplier;

/**
 * Enumeration of hashing algorithms.
 *
 * @author Oliver Richers
 */
public enum Hash {

	MD5("MD5"),
	SHA1("SHA1");

	private static final int BUFFER_SIZE = 4096;
	private String name;

	private Hash(String name) {

		this.name = name;
	}

	/**
	 * Creates a new instance of a message digest.
	 *
	 * @return new message digest instance
	 */
	public MessageDigest createDigest() {

		try {
			return MessageDigest.getInstance(name);
		} catch (NoSuchAlgorithmException exception) {
			throw new SofticarDeveloperException(exception);
		}
	}

	/**
	 * Returns the hash of the given string as byte array.
	 * <p>
	 * The text is converted into UTF-8 for hashing.
	 *
	 * @param text
	 *            the text to hash
	 * @return the hash of the given text
	 */
	public byte[] getHash(String text) {

		return getHash(text.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Computes the hash of the given bytes.
	 *
	 * @param bytes
	 *            the byte array (never <i>null</i>)
	 * @return the hash of the bytes
	 */
	public byte[] getHash(byte[] bytes) {

		return createDigest().digest(bytes);
	}

	/**
	 * Computes the hash of the data provided by the given input stream.
	 * <p>
	 * The input stream is automatically closed, even in case of an exception.
	 *
	 * @param inputStreamSupplier
	 *            the {@link Supplier} for the {@link InputStream} to read data
	 *            from (never <i>null</i>)
	 * @return the hash of the input stream
	 */
	public byte[] getHash(Supplier<InputStream> inputStreamSupplier) {

		try (InputStream stream = inputStreamSupplier.get()) {
			byte[] buffer = new byte[BUFFER_SIZE];
			MessageDigest digest = createDigest();
			while (true) {
				int n = stream.read(buffer);
				if (n < 0) {
					break;
				}
				digest.update(buffer, 0, n);
			}
			return digest.digest();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	/**
	 * Returns the hash of the given text as a hexadecimal string in upper case.
	 * <p>
	 * The text is converted into UTF-8 for hashing.
	 *
	 * @param text
	 *            the text to hash
	 * @return the hexadecimal hash string
	 */
	public String getHashStringUC(String text) {

		return Hexadecimal.getHexStringUC(getHash(text));
	}

	/**
	 * Returns the hash of the given text as a hexadecimal string in lower case.
	 * <p>
	 * The text is converted into UTF-8 for hashing.
	 *
	 * @param text
	 *            the text to hash
	 * @return the hexadecimal hash string
	 */
	public String getHashStringLC(String text) {

		return Hexadecimal.getHexStringLC(getHash(text));
	}
}
