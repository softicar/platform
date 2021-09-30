package com.softicar.platform.common.core.java.classes.analyzer;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;

public class AnalyzedJavaClassAnnotation {

	private final JavaClassName className;

	public AnalyzedJavaClassAnnotation(String descriptor) {

		if (descriptor.startsWith("L") && descriptor.endsWith(";")) {
			this.className = new JavaClassName(descriptor.substring(1, descriptor.length() - 1));
		} else {
			throw new RuntimeException(String.format("Illegal annotation descriptor '%s'.", descriptor));
		}
	}

	public JavaClassName getClassName() {

		return className;
	}

	public boolean is(Class<?> annotationClass) {

		return is(new JavaClassName(annotationClass));
	}

	public boolean is(JavaClassName className) {

		return className.equals(this.className);
	}
}
