package com.softicar.platform.common.io.hash;

import com.softicar.platform.common.string.hash.Hash;

/**
 * Convenience methods for MD5 hashing using class {@link Hash}.
 * 
 * @author Oliver Richers
 */
public class Md5 {

	/**
	 * Returns the hash of the text converted to UTF-8.
	 * 
	 * @param text
	 *            the text to create a hash from
	 * @return the hash as a 128bit byte array
	 */
	public static byte[] getHash(String text) {

		return Hash.MD5.getHash(text);
	}

	public static String getHashStringUC(String text) {

		return Hash.MD5.getHashStringUC(text);
	}

	public static String getHashStringLC(String text) {

		return Hash.MD5.getHashStringLC(text);
	}
}
