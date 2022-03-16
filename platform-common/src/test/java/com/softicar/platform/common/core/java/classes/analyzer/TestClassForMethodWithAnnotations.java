package com.softicar.platform.common.core.java.classes.analyzer;

class TestClassForMethodWithAnnotations {

	@SuppressWarnings("unused")
	@TestAnnotationWithClassValue(Double.class)
	public @TestAnnotation void foo(@TestAnnotation Object parameter) {

		// nothing to add
	}
}
