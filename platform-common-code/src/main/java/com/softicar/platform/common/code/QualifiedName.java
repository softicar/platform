package com.softicar.platform.common.code;

import com.softicar.platform.common.container.comparing.ContainerComparing;
import com.softicar.platform.common.string.Imploder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a qualified name.
 * <p>
 * A qualified name is made up of name segments separated by delimiters. In
 * Java, for example, the segments of a qualified name are delimited by dots.
 * This is also the default delimiter for this class.
 * 
 * @author Oliver Richers
 */
public class QualifiedName implements Comparable<QualifiedName> {

	public static final String DEFAULT_DELIMITER = ".";
	public static final QualifiedName EMPTY_NAME = new QualifiedName(Collections.<String> emptyList());
	private final List<String> segments;

	public QualifiedName(List<String> segments) {

		this.segments = segments;
	}

	public QualifiedName(QualifiedName qualifiedName) {

		this.segments = qualifiedName.segments;
	}

	public QualifiedName(QualifiedName parentName, String simpleName) {

		this.segments = new ArrayList<>(parentName.getSegments());
		this.segments.add(simpleName);
	}

	public static QualifiedName parse(String canonicalName) {

		return new QualifiedNameParser().parse(canonicalName);
	}

	/**
	 * Returns the name segments of this qualified name.
	 * 
	 * @return an unmodifiable list with all name segments
	 */
	public List<String> getSegments() {

		return Collections.unmodifiableList(segments);
	}

	/**
	 * Returns all name segments joined with a dot as delimiter.
	 * 
	 * @return returns the fully qualified name as string
	 */
	public String getCanonicalName() {

		return getCanonicalName(DEFAULT_DELIMITER);
	}

	/**
	 * Returns all name segments joined with the specified delimiter.
	 * 
	 * @param delimiter
	 *            the delimiter to put between the name segments
	 * @return returns the fully qualified name as string
	 */
	public String getCanonicalName(String delimiter) {

		return Imploder.implode(getSegments(), delimiter);
	}

	/**
	 * Returns the last name segment of this qualified name.
	 * 
	 * @return the last segment
	 */
	protected String getLastSegment() {

		List<String> segments = getSegments();
		return segments.get(segments.size() - 1);
	}

	protected QualifiedName getBeginning(int size) {

		if (size < 0 || size > segments.size()) {
			throw new IndexOutOfBoundsException(String.format("Requested %d segments of qualified name with size %d.", size, segments.size()));
		}

		return new QualifiedName(segments.subList(0, size));
	}

	/**
	 * Returns a list with all segments, except the last segment.
	 * 
	 * @return the parent segments
	 */
	protected List<String> getParentSegments() {

		List<String> segments = getSegments();
		return segments.subList(0, segments.size() - 1);
	}

	/**
	 * Returns the canonical name of this qualified name.
	 */
	@Override
	public String toString() {

		return getCanonicalName();
	}

	/**
	 * Returns a hash code over the name segments.
	 */
	@Override
	public int hashCode() {

		return getSegments().hashCode();
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof QualifiedName) {
			QualifiedName other = (QualifiedName) object;
			return getSegments().equals(other.getSegments());
		} else {
			return false;
		}
	}

	/**
	 * Compares the name segments lexicographically.
	 */
	@Override
	public int compareTo(QualifiedName other) {

		return ContainerComparing.compare(getSegments(), other.getSegments());
	}
}
