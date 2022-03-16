package com.softicar.platform.common.io.resource.key;

import com.softicar.platform.common.core.utils.equals.Equals;
import com.softicar.platform.common.io.mime.MimeType;
import java.util.Comparator;
import java.util.Objects;

/**
 * Facilitates handling and validation of a "super mime type", as used in an
 * {@link IResourceKey}.
 * <p>
 * A super mime type is the left-hand token of a mime type string; e.g.
 * {@code "image"} from {@code "image/png"}.
 *
 * @author Alexander Schmidt
 */
public class ResourceKeySuperMimeType implements Comparable<ResourceKeySuperMimeType> {

	private final String superMimeType;

	/**
	 * Constructs a new {@link ResourceKeySuperMimeType} by deriving a super
	 * mime type from the given file name.
	 * <p>
	 * Example 1: file name {@code "foo.png"} implies {@code "image"}, because
	 * {@code ".png"} implies mime type {@code "image/png"}).
	 * <p>
	 * Example 2: file name {@code "foo.unknown"} implies {@code "application"},
	 * because {@code ".unknown"} does not imply a mime type, and
	 * {@code "application/octet-stream"} is assumed as default
	 *
	 * @param filename
	 *            the file name from which the super mime type shall be derived
	 *            (never <i>null</i>)
	 * @return a new {@link ResourceKeySuperMimeType}, derived from the given
	 *         file name (never <i>null</i>)
	 * @throws NullPointerException
	 *             if the given {@link String} is <i>null</i>
	 */
	public static ResourceKeySuperMimeType fromFilename(String filename) {

		return new ResourceKeySuperMimeType(determineSuperMimeType(filename));
	}

	private ResourceKeySuperMimeType(String superMimeType) {

		this.superMimeType = superMimeType.toLowerCase();
	}

	@Override
	public int hashCode() {

		return Objects.hash(superMimeType);
	}

	@Override
	public boolean equals(Object other) {

		return Equals//
			.comparing(ResourceKeySuperMimeType::get)
			.compareToObject(this, other);
	}

	@Override
	public int compareTo(ResourceKeySuperMimeType other) {

		return Comparator//
			.comparing(ResourceKeySuperMimeType::get)
			.compare(this, other);
	}

	@Override
	public String toString() {

		return superMimeType;
	}

	/**
	 * Returns this {@link ResourceKeySuperMimeType} as a {@link String}.
	 *
	 * @return this {@link ResourceKeySuperMimeType} as a {@link String} (never
	 *         <i>null</i>)
	 */
	public String get() {

		return superMimeType;
	}

	private static String determineSuperMimeType(String filename) {

		return MimeType//
			.getByFilenameOrDefault(filename)
			.getSuperType();
	}
}
