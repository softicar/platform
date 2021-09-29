package com.softicar.platform.common.core.java.classes.analyzer;

@SuppressWarnings("unused")
class TestClassForInnerClasses {

	private final A a = null;
	private final Object b = new B();

	public class A {

		// nothing to add
	}

	public class B {

		// nothing to add
	}
}
