package com.softicar.platform.common.security.crypt;

import org.apache.commons.codec.digest.Md5Crypt;

/**
 * Provides method to encrypt and verify passwords using the Apache MD5 password
 * algorithm.
 *
 * @author Oliver Richers
 */
public class Apr1Crypt {

	/**
	 * Encrypts the given clear-text password.
	 *
	 * @param password
	 *            the clear-text password
	 * @return the hashed password in the form $apr1$salt$encryptedPassword
	 */
	public static String encryptPassword(String password) {

		return Md5Crypt.apr1Crypt(password);
	}

	/**
	 * Encrypts the given clear-text password using the specified salt.
	 *
	 * @param password
	 *            the clear-text password
	 * @param salt
	 *            the salt to use
	 * @return the hashed password in the form $apr1$salt$encryptedPassword
	 */
	public static String encryptPassword(String password, String salt) {

		return Md5Crypt.apr1Crypt(password, salt);
	}

	/**
	 * Checks if the given clear-text password and the encrypted password match.
	 *
	 * @param password
	 *            the clear-text password
	 * @param encryptedPassword
	 *            the encrypted password
	 * @return true if and only if the two passwords match
	 */
	public static boolean verifyPassword(String password, String encryptedPassword) {

		return Md5Crypt.apr1Crypt(password, encryptedPassword).equals(encryptedPassword);
	}
}
