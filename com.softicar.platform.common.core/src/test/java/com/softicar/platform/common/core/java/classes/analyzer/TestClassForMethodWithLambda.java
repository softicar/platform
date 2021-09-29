package com.softicar.platform.common.core.java.classes.analyzer;

import java.util.function.Supplier;

@SuppressWarnings("unused")
class TestClassForMethodWithLambda {

	public void foo() {

		Supplier<Object> s = () -> {
			Number x = 42;
			return x;
		};
	}
}
