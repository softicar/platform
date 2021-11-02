package com.softicar.platform.common.core.java.classes.analyzer;

import java.util.ArrayList;
import java.util.Collection;

class TestClassForAnnotationsOnTypeUse {

	public Collection<@TestAnnotationForTypeUse String> foo() {

		Collection<@TestAnnotationForTypeUse String> localVariable = new ArrayList<>();
		localVariable.add("foo");
		return localVariable;
	}
}
