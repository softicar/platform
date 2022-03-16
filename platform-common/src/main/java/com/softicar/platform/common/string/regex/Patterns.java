package com.softicar.platform.common.string.regex;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Provides auxiliary methods for patterns (as in {@link Pattern}).
 *
 * @author Alexander Schmidt
 */
public class Patterns {

	/**
	 * Determines whether the given {@link String} matches the given
	 * {@link Pattern}.
	 *
	 * @param input
	 *            the {@link String} to match (never <i>null</i>)
	 * @param pattern
	 *            the {@link Pattern} against which the given {@link String}
	 *            shall be matched (never <i>null</i>)
	 * @return <i>true</i> if the given {@link String} matches the given
	 *         {@link Pattern}; <i>false</i> otherwise
	 * @throws NullPointerException
	 *             if any of the given arguments is <i>null</i>
	 */
	public static boolean matches(String input, Pattern pattern) {

		Objects.requireNonNull(pattern);
		return anyMatch(input, pattern);
	}

	/**
	 * Determines whether the given {@link String} matches any of the given
	 * {@link Pattern} instances.
	 *
	 * @param input
	 *            the {@link String} to match (never <i>null</i>)
	 * @param patterns
	 *            the {@link Pattern} instances against which the given
	 *            {@link String} shall be matched (never <i>null</i>)
	 * @return <i>true</i> if the given {@link String} matches at least one of
	 *         the given {@link Pattern} instances; <i>false</i> otherwise, or
	 *         if no {@link Pattern} instances are given
	 * @throws NullPointerException
	 *             if any of the given arguments is <i>null</i>
	 */
	public static boolean anyMatch(String input, Pattern...patterns) {

		Objects.requireNonNull(input);
		Objects.requireNonNull(patterns);
		return anyMatch(input, Arrays.asList(patterns));
	}

	/**
	 * Determines whether the given {@link String} matches any of the given
	 * {@link Pattern} instances.
	 *
	 * @param input
	 *            the {@link String} to match (never <i>null</i>)
	 * @param patterns
	 *            the {@link Pattern} instances against which the given
	 *            {@link String} shall be matched (never <i>null</i>)
	 * @return <i>true</i> if the given {@link String} matches at least one of
	 *         the given {@link Pattern} instances; <i>false</i> otherwise, or
	 *         if no {@link Pattern} instances are given
	 * @throws NullPointerException
	 *             if any of the given arguments is <i>null</i>
	 */
	public static boolean anyMatch(String input, Collection<Pattern> patterns) {

		Objects.requireNonNull(input);
		Objects.requireNonNull(patterns);
		return patterns.stream().anyMatch(pattern -> pattern.matcher(input).matches());
	}

	/**
	 * Determines whether the given {@link String} matches none of the given
	 * {@link Pattern} instances.
	 *
	 * @param input
	 *            the {@link String} to match (never <i>null</i>)
	 * @param patterns
	 *            the {@link Pattern} instances against which the given
	 *            {@link String} shall be matched (never <i>null</i>)
	 * @return <i>true</i> if the given {@link String} does not match any of the
	 *         given {@link Pattern} instances, or if no {@link Pattern}
	 *         instances are given; <i>false</i> otherwise
	 * @throws NullPointerException
	 *             if any of the given arguments is <i>null</i>
	 */
	public static boolean noneMatch(String input, Pattern...patterns) {

		Objects.requireNonNull(input);
		Objects.requireNonNull(patterns);
		return noneMatch(input, Arrays.asList(patterns));
	}

	/**
	 * Determines whether the given {@link String} matches none of the given
	 * {@link Pattern} instances.
	 *
	 * @param input
	 *            the {@link String} to match (never <i>null</i>)
	 * @param patterns
	 *            the {@link Pattern} instances against which the given
	 *            {@link String} shall be matched (never <i>null</i>)
	 * @return <i>true</i> if the given {@link String} does not match any of the
	 *         given {@link Pattern} instances, or if no {@link Pattern}
	 *         instances are given; <i>false</i> otherwise
	 * @throws NullPointerException
	 *             if any of the given arguments is <i>null</i>
	 */
	public static boolean noneMatch(String input, Collection<Pattern> patterns) {

		Objects.requireNonNull(input);
		Objects.requireNonNull(patterns);
		return patterns.stream().noneMatch(pattern -> pattern.matcher(input).matches());
	}
}
