package com.softicar.platform.common.security.crypt;

/**
 * Provides method to encrypt and verify passwords using the Unix crypt(3)
 * password algorithm.
 * <p>
 * Warning: This algorithm is not very secure and should not be used. The
 * purpose of this class is to support special cases for legacy reasons.
 *
 * @author Oliver Richers
 */
public class UnixCrypt {

	/**
	 * Encrypts the given clear-text password.
	 *
	 * @param password
	 *            the clear-text password
	 * @return the hashed password in old Unix crypt(3) format
	 */
	public static String encryptPassword(String password) {

		return org.apache.commons.codec.digest.UnixCrypt.crypt(password);
	}

	/**
	 * Encrypts the given clear-text password using the specified salt.
	 *
	 * @param password
	 *            the clear-text password
	 * @param salt
	 *            the salt to use
	 * @return the hashed password in old Unix crypt(3) format
	 */
	public static String encryptPassword(String password, String salt) {

		return org.apache.commons.codec.digest.UnixCrypt.crypt(password, salt);
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

		return org.apache.commons.codec.digest.UnixCrypt.crypt(password, encryptedPassword).equals(encryptedPassword);
	}
}
