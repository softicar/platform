package com.softicar.platform.common.core.java.identifier.key;

import java.util.Comparator;
import java.util.Objects;

/**
 * Identifies a Java construct via name and Java descriptor.
 * <p>
 * Can be used for to identify fields or methods alike.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class JavaIdentifierKey implements Comparable<JavaIdentifierKey> {

	private final String name;
	private final String descriptor;

	public JavaIdentifierKey(String name, String descriptor) {

		this.name = name;
		this.descriptor = descriptor;
	}

	public String getName() {

		return name;
	}

	public String getDescriptor() {

		return descriptor;
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof JavaIdentifierKey) {
			JavaIdentifierKey other = (JavaIdentifierKey) object;
			return Objects.equals(name, other.name) && Objects.equals(descriptor, other.descriptor);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(name, descriptor);
	}

	@Override
	public int compareTo(JavaIdentifierKey other) {

		return Comparator//
			.comparing(JavaIdentifierKey::getName)
			.thenComparing(JavaIdentifierKey::getDescriptor)
			.compare(this, other);
	}

	@Override
	public String toString() {

		return name + descriptor;
	}
}
