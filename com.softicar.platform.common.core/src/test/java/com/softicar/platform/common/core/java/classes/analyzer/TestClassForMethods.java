package com.softicar.platform.common.core.java.classes.analyzer;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class TestClassForMethods {

	@SuppressWarnings("unused")
	public Double foo(List<String> input) throws RuntimeException {

		// local variable declaration using Set and BgiDecimal
		Set<BigDecimal> decimals;

		// constructor invocation of TreeSet
		decimals = new TreeSet<>();

		// implicit use of Stream and cast to Long
		Number size = input.stream().count();

		// implicit cast to Integer
		Number value = 42;

		// implicit use of Map
		Object map = Collections.EMPTY_MAP;

		// instance of with Float
		boolean isFloat = value instanceof Float;

		// multi-dimensional array instantiation
		Object multiArray = new Byte[3][3];

		// reference to inner class of TestClassForInnerClasses
		TestClassForInnerClasses.A innerA = null;

		return null;
	}
}
