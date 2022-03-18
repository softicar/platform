package com.softicar.platform.common.string;

/**
 * Provides methods for padding strings.
 *
 * @author Oliver Richers
 */
public class Padding {

	/**
	 * Generates a string with the specified amount of padding characters.
	 *
	 * @param pad
	 *            the padding character to use
	 * @param length
	 *            the amount of characters
	 * @return the generated string
	 */
	public static String generate(char pad, int length) {

		// check length
		if (length < 0) {
			throw new IllegalArgumentException("Generation length may not be negative.");
		}

		// generate
		return generate(new StringBuilder(length), pad, length).toString();
	}

	/**
	 * Appends the given amount of padding characters to the specified string
	 * builder.
	 *
	 * @param builder
	 *            the string builder to append the characters to
	 * @param pad
	 *            the padding character to use
	 * @param length
	 *            the amount of characters
	 * @return the given string builder
	 */
	public static StringBuilder generate(StringBuilder builder, char pad, int length) {

		// check length
		if (length < 0) {
			throw new IllegalArgumentException("Generation length may not be negative.");
		}

		// pre-allocated memory
		builder.ensureCapacity(builder.length() + length);

		// generate
		for (int i = 0; i < length; ++i) {
			builder.append(pad);
		}

		return builder;
	}

	/**
	 * Adds padding characters to the specified string to fit the given length.
	 * <p>
	 * This method tries to add the same amount of padding characters at the
	 * begin and the end of the string. If the amount of padding characters is
	 * not divisible by two, an extra padding character is added at the end of
	 * the string and thus causing a tiny shift to the left.
	 * <p>
	 * If the given string already has the requested length, or is even longer,
	 * it will be returned unchanged.
	 *
	 * @param source
	 *            the source string, never null
	 * @param pad
	 *            the padding character to use
	 * @param length
	 *            the desired length of the padded string
	 * @return the padded string, with the guaranteed minimum length
	 */
	public static String padCenter(String source, char pad, int length) {

		int padding = length - source.length();

		// quick exit if no padding is necessary
		if (padding <= 0) {
			return source;
		}

		// compute left and right padding
		int left = padding / 2;
		int right = padding - left;

		// generate
		StringBuilder builder = new StringBuilder(length);
		generate(builder, pad, left);
		builder.append(source);
		generate(builder, pad, right);

		return builder.toString();
	}

	/**
	 * Prepends padding characters to the given source string to reach the
	 * desired length.
	 * <p>
	 * If the source string is longer than the desired length, the source string
	 * is returned unchanged.
	 *
	 * @param source
	 *            the source string
	 * @param pad
	 *            the padding character
	 * @param length
	 *            the desired length
	 * @return the padded string with the desired minimum length
	 */
	public static String padLeft(String source, char pad, int length) {

		int padding = length - source.length();

		// quick exit if no padding is necessary
		if (padding <= 0) {
			return source;
		}

		// generate
		StringBuilder builder = new StringBuilder(length);
		generate(builder, pad, padding);
		builder.append(source);

		return builder.toString();
	}

	/**
	 * Prepends padding strings to the given source string to reach the desired
	 * length.
	 *
	 * @param source
	 *            the source string
	 * @param pad
	 *            the padding string
	 * @param length
	 *            the desired minimum length
	 * @return the padded string with the desired minimum length
	 */
	public static String padLeft(String source, String pad, int length) {

		while (source.length() < length) {
			source = pad + source;
		}
		return source;
	}

	/**
	 * Appends padding characters to the given source string to reach the
	 * desired length.
	 * <p>
	 * If the source string is longer than the desired length, the source string
	 * is returned unchanged.
	 *
	 * @param source
	 *            the source string
	 * @param pad
	 *            the padding character
	 * @param length
	 *            the desired length
	 * @return the padded string with the desired minimum length
	 */
	public static String padRight(String source, char pad, int length) {

		int padding = length - source.length();

		// quick exit if no padding is necessary
		if (padding <= 0) {
			return source;
		}

		// generate
		StringBuilder builder = new StringBuilder(length);
		builder.append(source);
		generate(builder, pad, padding);

		return builder.toString();
	}

	/**
	 * Appends padding strings to the given source string to reach the desired
	 * length.
	 *
	 * @param source
	 *            the source string
	 * @param pad
	 *            the padding string
	 * @param length
	 *            the desired minimum length
	 * @return the padded string with the desired minimum length
	 */
	public static String padRight(String source, String pad, int length) {

		while (source.length() < length) {
			source += pad;
		}
		return source;
	}
}
