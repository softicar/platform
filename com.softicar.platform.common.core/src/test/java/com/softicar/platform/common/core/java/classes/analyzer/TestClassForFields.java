package com.softicar.platform.common.core.java.classes.analyzer;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

class TestClassForFields {

	@TestAnnotationWithClassValue(Number.class) private Set<List<BigDecimal>> fieldA;
	@TestAnnotationWithEnumValue(TestEnum.A) private Collection<String> fieldB;
}
