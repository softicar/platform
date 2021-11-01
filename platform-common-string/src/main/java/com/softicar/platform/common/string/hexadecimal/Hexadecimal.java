package com.softicar.platform.common.string.hexadecimal;

import com.softicar.platform.common.string.formatting.Formatting;

public class Hexadecimal {

	private static final String HEX_DIGITS_UC = "0123456789ABCDEF";
	private static final String HEX_DIGITS_LC = "0123456789abcdef";
	private static final int BITS_PER_NIBBLE = 4;
	private static final int VALUE_OF_NIBBLE = 16;
	private static final int VALUE_OF_BYTE = 256;
	private static final int VALUE_OF_HEX_DIGIT_A = 10;

	/**
	 * Converts the specified array of bytes into a hexadecimal string with
	 * upper-case letters.
	 * 
	 * @param bytes
	 *            an array of bytes
	 * @return the hexadecimal string
	 */
	public static String getHexStringUC(byte[] bytes) {

		return getHexString(bytes, HEX_DIGITS_UC);
	}

	/**
	 * Converts the specified array of bytes into a hexadecimal string with
	 * lower-case letters.
	 * 
	 * @param bytes
	 *            an array of bytes
	 * @return the hexadecimal string
	 */
	public static String getHexStringLC(byte[] bytes) {

		return getHexString(bytes, HEX_DIGITS_LC);
	}

	public static byte[] getBytesFromHexString(String hexString) {

		// check parameter
		int nibbleCount = hexString.length();
		if (nibbleCount % 2 != 0) {
			throw new IllegalArgumentException("Invalid hexadecimal string '%s'. Length must be a multiple of two.");
		}

		// convert nibbles into bytes
		int byteCount = nibbleCount / 2;
		byte[] output = new byte[byteCount];
		for (int i = 0; i < byteCount; ++i) {
			output[i] = decodeNibbles(hexString.charAt(i * 2), hexString.charAt(i * 2 + 1));
		}

		return output;
	}

	private static byte decodeNibbles(char nibble1, char nibble2) {

		return (byte) ((decodeNibble(nibble1) << BITS_PER_NIBBLE) + decodeNibble(nibble2));
	}

	private static int decodeNibble(char nibble) {

		if (nibble >= '0' && nibble <= '9') {
			return nibble - '0';
		}

		if (nibble >= 'A' && nibble <= 'F') {
			return VALUE_OF_HEX_DIGIT_A + nibble - 'A';
		}

		if (nibble >= 'a' && nibble <= 'f') {
			return VALUE_OF_HEX_DIGIT_A + nibble - 'a';
		}

		throw new IllegalArgumentException(Formatting.format("Invalid hexadecimal nibble '%s'.", nibble));
	}

	private static String getHexString(byte[] bytes, String charMap) {

		StringBuilder result = new StringBuilder(2 * bytes.length);
		for (int i = 0; i != bytes.length; ++i) {
			int integer = bytes[i];
			if (integer < 0) {
				integer += VALUE_OF_BYTE;
			}
			result.append(charMap.charAt(integer / VALUE_OF_NIBBLE));
			result.append(charMap.charAt(integer % VALUE_OF_NIBBLE));
		}
		return result.toString();
	}
}
