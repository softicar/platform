package com.softicar.platform.common.security.crypt;

/**
 * Provides method to encrypt and verify passwords using the BCrypt algorithm.
 *
 * @author Oliver Richers
 */
public class Bcrypt {

	public static final int DEFAULT_NUMBER_OF_ROUNDS = 10;

	/**
	 * Encrypts the given clear-text password using the default number of
	 * rounds.
	 *
	 * @param password
	 *            the clear-text password
	 * @return the hashed password in the form $2a$rounds$encryptedPassword
	 */
	public static String encryptPassword(String password) {

		return encryptPassword(password, DEFAULT_NUMBER_OF_ROUNDS);
	}

	/**
	 * Encrypts the given clear-text password using the specified number of
	 * rounds.
	 *
	 * @param password
	 *            the clear-text password
	 * @param rounds
	 *            the number of rounds (this is actually given as a power of
	 *            two)
	 * @return the hashed password in the form $2a$rounds$encryptedPassword
	 */
	public static String encryptPassword(String password, int rounds) {

		return BcryptAlgorithm.hashpw(password, BcryptAlgorithm.gensalt(rounds));
	}

	/**
	 * Encrypts the given clear-text password using the specified salt.
	 *
	 * @param password
	 *            the clear-text password
	 * @param salt
	 *            the salt to use
	 * @return the hashed password in the form $2a$rounds$encryptedPassword
	 */
	public static String encryptPassword(String password, String salt) {

		return BcryptAlgorithm.hashpw(password, salt);
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

		return BcryptAlgorithm.checkpw(password, encryptedPassword);
	}
}
