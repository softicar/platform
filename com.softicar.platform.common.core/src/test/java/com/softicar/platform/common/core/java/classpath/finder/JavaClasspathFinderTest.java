package com.softicar.platform.common.core.java.classpath.finder;

import java.io.File;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;

public class JavaClasspathFinderTest extends Assert {

	@Test
	public void test() {

		Collection<File> classpaths = new JavaClasspathFinder().findAll();

		assertFalse(classpaths.isEmpty());
		assertTrue(
			classpaths//
				.stream()
				.map(it -> it.getPath())
				.filter(it -> it.contains("com.softicar.platform.common.core"))
				.findAny()
				.isPresent());
	}
}
