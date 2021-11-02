package com.softicar.platform.emf.page;

import com.softicar.platform.common.container.comparing.ContainerComparing;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.Imploder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the path of an {@link IEmfPage}.
 *
 * @author Oliver Richers
 */
public class EmfPagePath implements Comparable<EmfPagePath> {

	public static final EmfPagePath EMPTY_PATH = new EmfPagePath();
	private final EmfPagePath parent;
	private final String segment;

	public EmfPagePath() {

		this(null, null);
	}

	public EmfPagePath(EmfPagePath parent, String segment) {

		this.parent = parent;
		this.segment = segment;
	}

	public static EmfPagePath create(List<String> path) {

		int size = path.size();
		if (size > 0) {
			return create(path.subList(0, size - 1)).append(path.get(size - 1));
		} else {
			return EMPTY_PATH;
		}
	}

	public EmfPagePath append(String segment) {

		return new EmfPagePath(this, segment);
	}

	public EmfPagePath append(IDisplayString segment) {

		return new EmfPagePath(this, segment.toString());
	}

	public boolean isEmpty() {

		return parent == null && segment == null;
	}

	public EmfPagePath getParent() {

		if (parent != null) {
			return parent;
		} else {
			throw new UnsupportedOperationException("Cannot get parent path of empty path.");
		}
	}

	public String getFirstSegment() {

		if (parent != null) {
			return parent.getFirstSegment();
		} else if (segment != null) {
			return segment;
		} else {
			throw new UnsupportedOperationException("Cannot get first segment of empty path.");
		}
	}

	public String getFirstNotEmptySegment() {

		if (parent == null || parent.equals(EMPTY_PATH)) {
			if (segment == null) {
				throw new UnsupportedOperationException("Cannot get first segment of empty path.");
			}
			return segment;
		}

		return parent.getFirstNotEmptySegment();
	}

	public String getLastSegment() {

		if (segment != null) {
			return segment;
		} else {
			throw new UnsupportedOperationException("Cannot get last segment of empty path.");
		}
	}

	public List<String> getSegments() {

		return collectSegments(new ArrayList<>());
	}

	public String getCanonicalPath(String delimiter) {

		return Imploder.implode(getSegments(), delimiter);
	}

	@Override
	public int hashCode() {

		return Objects.hash(parent, segment);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof EmfPagePath) {
			EmfPagePath other = (EmfPagePath) object;
			return Objects.equals(other.parent, parent) && Objects.equals(other.segment, segment);
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(EmfPagePath other) {

		return ContainerComparing.compare(getSegments(), other.getSegments());
	}

	private List<String> collectSegments(List<String> segments) {

		if (parent != null) {
			parent.collectSegments(segments);
		}
		if (segment != null) {
			segments.add(segment);
		}
		return segments;
	}
}
