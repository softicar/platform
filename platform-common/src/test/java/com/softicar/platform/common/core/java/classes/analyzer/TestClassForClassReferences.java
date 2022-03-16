package com.softicar.platform.common.core.java.classes.analyzer;

import java.math.BigDecimal;
import java.util.List;

@SuppressWarnings("unused")
class TestClassForClassReferences {

	private static final Class<?> STATIC_FIELD = List.class;
	private final Class<?> field = BigDecimal.class;

	private void foo() {

		bar(String.class);
	}

	private void bar(Class<?> parameter) {

		// nothing to do
	}
}
