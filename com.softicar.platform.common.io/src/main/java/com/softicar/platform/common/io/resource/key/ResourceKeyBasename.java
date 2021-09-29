package com.softicar.platform.common.io.resource.key;

import com.softicar.platform.common.core.utils.equals.Equals;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Facilitates extraction, handling and validation of a "base name", as used in
 * an {@link IResourceKey}.
 *
 * @author Alexander Schmidt
 */
public class ResourceKeyBasename implements Comparable<ResourceKeyBasename> {

	private static final Pattern VALIDATION_REGEX = Pattern.compile("[a-z0-9]((\\-)?[a-z0-9]+)*");
	private final String basename;

	/**
	 * Creates a new {@link ResourceKeyBasename} by deriving a base name from
	 * the given file name.
	 * <p>
	 * The base name comprises all characters to the left side of the first dot
	 * in the given file name.
	 * <p>
	 * Example 1: file name {@code "foo.bar.baz"} implies base name
	 * {@code "foo"}
	 * <p>
	 * Example 2: file name {@code "foo"} implies base name {@code "foo"}
	 * <p>
	 * Example 3: file name {@code ".foo"} implies base name {@code ""}
	 * <p>
	 * Example 3: file name {@code ""} implies base name {@code ""}
	 *
	 * @param filename
	 *            the file name from which the base name shall be extracted
	 *            (never <i>null</i>)
	 * @return a new {@link ResourceKeyBasename}, derived from the given file
	 *         name (never <i>null</i>)
	 * @throws NullPointerException
	 *             if the given {@link String} is <i>null</i>
	 */
	public static ResourceKeyBasename fromFilename(String filename) {

		return new ResourceKeyBasename(determineBasename(filename));
	}

	/**
	 * Creates a new {@link ResourceKeyBasename} from the given literal base
	 * name.
	 * <p>
	 * All characters of the given base name are retained, and no processing is
	 * applied.
	 *
	 * @param basename
	 *            the literal base name (never <i>null</i>)
	 * @return a {@link ResourceKeyBasename} that wraps the given base name
	 *         (never <i>null</i>)
	 * @throws NullPointerException
	 *             if the given {@link String} is <i>null</i>
	 */
	public static ResourceKeyBasename fromBasename(String basename) {

		return new ResourceKeyBasename(basename);
	}

	private ResourceKeyBasename(String basename) {

		this.basename = Objects.requireNonNull(basename).toLowerCase();
	}

	@Override
	public int hashCode() {

		return Objects.hash(basename);
	}

	@Override
	public boolean equals(Object other) {

		return Equals//
			.comparing(ResourceKeyBasename::get)
			.compareToObject(this, other);
	}

	@Override
	public int compareTo(ResourceKeyBasename other) {

		return Comparator//
			.comparing(ResourceKeyBasename::get)
			.compare(this, other);
	}

	@Override
	public String toString() {

		return basename;
	}

	/**
	 * Returns this {@link ResourceKeyBasename} as a {@link String}.
	 *
	 * @return this {@link ResourceKeyBasename} as a {@link String} (never
	 *         <i>null</i>)
	 */
	public String get() {

		return basename;
	}

	/**
	 * Checks whether the base name is valid.
	 * <p>
	 * A base name is valid if all of the following conditions are met:
	 * <ul>
	 * <li>the {@link String} is non-empty</li>
	 * <li>all characters are either lower-case letters ("a-z"), or digits
	 * ("0-9"), or dashes ("-")</li>
	 * <li>the first character is a letter or a digit</li>
	 * <li>the last character is a letter or a digit</li>
	 * <li>the {@link String} does not contain two or more adjacent dashes</li>
	 * </ul>
	 *
	 * @return <i>true</i> if the base name is valid; <i>false</i> otherwise
	 * @throws NullPointerException
	 *             if the given {@link String} is <i>null</i>
	 */
	public boolean isValid() {

		return VALIDATION_REGEX.asMatchPredicate().test(basename);
	}

	private static String determineBasename(String filename) {

		int pointIndex = filename.indexOf('.');
		if (pointIndex >= 0) {
			return filename.substring(0, pointIndex);
		} else {
			return filename;
		}
	}
}
