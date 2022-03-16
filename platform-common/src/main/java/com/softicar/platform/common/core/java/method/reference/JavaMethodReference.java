package com.softicar.platform.common.core.java.method.reference;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.identifier.key.JavaIdentifierKey;
import java.util.Comparator;
import java.util.Objects;

/**
 * Represents a reference to a Java method.
 *
 * @author Oliver Richers
 */
public class JavaMethodReference implements Comparable<JavaMethodReference> {

	private final JavaClassName owner;
	private final JavaIdentifierKey methodKey;

	public JavaMethodReference(Class<?> owner, String methodName, String methodDescriptor) {

		this(new JavaClassName(owner), new JavaIdentifierKey(methodName, methodDescriptor));
	}

	public JavaMethodReference(JavaClassName owner, JavaIdentifierKey methodKey) {

		this.owner = owner;
		this.methodKey = methodKey;
	}

	public JavaClassName getOwner() {

		return owner;
	}

	public JavaIdentifierKey getMethodKey() {

		return methodKey;
	}

	public String getMethodName() {

		return methodKey.getName();
	}

	public String getMethodDescriptor() {

		return methodKey.getDescriptor();
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof JavaMethodReference) {
			return compareTo((JavaMethodReference) object) == 0;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(owner, methodKey);
	}

	@Override
	public String toString() {

		return String.format("%s.%s", owner, methodKey);
	}

	@Override
	public int compareTo(JavaMethodReference other) {

		return Comparator//
			.comparing(JavaMethodReference::getOwner)
			.thenComparing(JavaMethodReference::getMethodName)
			.thenComparing(JavaMethodReference::getMethodDescriptor)
			.compare(this, other);
	}
}
