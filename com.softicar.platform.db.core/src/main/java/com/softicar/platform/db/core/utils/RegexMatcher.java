package com.softicar.platform.db.core.utils;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

// TODO move this to com.softicar.platform.common
public class RegexMatcher extends TypeSafeMatcher<String> {

	private final String regex;

	public RegexMatcher(String regex) {

		this.regex = regex;
	}

	@Override
	public void describeTo(Description description) {

		description.appendText("matches regex=`" + regex + "`");
	}

	@Override
	public boolean matchesSafely(String string) {

		return string.matches(regex);
	}

	public static RegexMatcher matchesRegex(String regex) {

		return new RegexMatcher(regex);
	}
}
