package com.softicar.platform.common.core.java.classes.name.matcher;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.ArrayList;
import java.util.Collection;

public class JavaClassNameMatcherCollection implements IJavaClassNameMatcher {

	private final Collection<IJavaClassNameMatcher> matchers;

	public JavaClassNameMatcherCollection() {

		this.matchers = new ArrayList<>();
	}

	public JavaClassNameMatcherCollection add(IJavaClassNameMatcher matcher) {

		matchers.add(matcher);
		return this;
	}

	public JavaClassNameMatcherCollection addAll(Collection<? extends IJavaClassNameMatcher> otherMatchers) {

		matchers.addAll(otherMatchers);
		return this;
	}

	@Override
	public boolean test(JavaClassName className) {

		return matchers.stream().anyMatch(matcher -> matcher.test(className));
	}

	@Override
	public String toString() {

		return matchers.toString();
	}
}
