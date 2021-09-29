package com.softicar.platform.common.io.resource.default_;

import com.softicar.platform.common.io.resource.key.IResourceKey;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Facilitates handling and validation of a file name of a default resource, as
 * used in an {@link IResourceKey}.
 *
 * @author Alexander Schmidt
 */
public class DefaultResourceFilename {

	private static final Pattern VALIDATION_REGEX = Pattern.compile("[a-zA-Z0-9]((\\-)?[a-zA-Z0-9]+)*((\\.)?[a-zA-Z0-9]+)?");
	private final String filename;

	/**
	 * Constructs a new {@link DefaultResourceFilename} from the given literal
	 * default file name.
	 *
	 * @param filename
	 *            the default file name (never <i>null</i>)
	 * @throws NullPointerException
	 *             if the given {@link String} is <i>null</i>
	 */
	public DefaultResourceFilename(String filename) {

		this.filename = Objects.requireNonNull(filename);
	}

	@Override
	public String toString() {

		return filename;
	}

	/**
	 * Returns this {@link DefaultResourceFilename} as a {@link String}.
	 *
	 * @return this {@link DefaultResourceFilename} as a {@link String} (never
	 *         <i>null</i>)
	 */
	public String get() {

		return filename;
	}

	/**
	 * Checks whether the file name is valid for a default resource.
	 * <p>
	 * A file name is valid if all of the following conditions are met:
	 * <ul>
	 * <li>the {@link String} is non-empty</li>
	 * <li>all characters are either letters ("a-zA-Z"), or digits ("0-9"),
	 * dashes ("-") or dots (".")</li>
	 * <li>the first character is a letter or a digit</li>
	 * <li>the last character is a letter or a digit</li>
	 * <li>the {@link String} does not contain a dash or dot that is adjacent to
	 * another dash or dot</li>
	 * <li>the {@link String} contains at most one dot</li>
	 * </ul>
	 *
	 * @return <i>true</i> if the file name is valid; <i>false</i> otherwise
	 */
	public boolean isValid() {

		return VALIDATION_REGEX.asMatchPredicate().test(filename);
	}
}
