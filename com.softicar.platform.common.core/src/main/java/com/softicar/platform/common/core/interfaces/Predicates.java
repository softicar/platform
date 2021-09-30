package com.softicar.platform.common.core.interfaces;

import java.util.function.Predicate;

public class Predicates {

	private static final Predicate<Object> ALWAYS = x -> true;
	private static final Predicate<Object> NEVER = x -> false;

	@SuppressWarnings("unchecked")
	public static <T> Predicate<T> always() {

		return (Predicate<T>) ALWAYS;
	}

	@SuppressWarnings("unchecked")
	public static <T> Predicate<T> never() {

		return (Predicate<T>) NEVER;
	}
}
