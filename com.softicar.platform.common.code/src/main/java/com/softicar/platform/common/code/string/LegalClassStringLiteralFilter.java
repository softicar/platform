package com.softicar.platform.common.code.string;

import java.util.function.Predicate;

public class LegalClassStringLiteralFilter implements Predicate<String> {

	private static final String INVALID_STRING_REGEX = ".*[\\p{L}0-9].*";
	private static final String VALID_WILDCARD_REGEX = "(%s)|(%((([1-9][0-9]*)?\\\\.)?[1-9][0-9]*)?f)";
	private static final String UUID_REGEX = "[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}";

	@Override
	public boolean test(String literal) {

		return literal.replaceAll(VALID_WILDCARD_REGEX, "").matches(INVALID_STRING_REGEX) && !literal.matches(UUID_REGEX);
	}
}
