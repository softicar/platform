package com.softicar.platform.common.string.unicode;

import com.softicar.platform.common.string.charset.Charsets;

/**
 * Utility methods to convert between String and Utf8.
 *
 * @author Oliver Richers
 */
public class Utf8Convering {

	public static byte[] toUtf8(String text) {

		return text != null? text.getBytes(Charsets.UTF8) : null;
	}

	public static String fromUtf8(byte[] bytes) {

		return Utf8Convering.fromUtf8(bytes, 0, bytes.length);
	}

	public static String fromUtf8(byte[] bytes, int offest, int length) {

		return bytes != null? new String(bytes, offest, length, Charsets.UTF8) : null;
	}

	public static String fromUtf8_ZeroTerminated(byte[] bytes) {

		int i = 0;
		while (i < bytes.length && bytes[i] != 0) {
			++i;
		}
		return Utf8Convering.fromUtf8(bytes, 0, i);
	}
}
