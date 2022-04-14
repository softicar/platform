package com.softicar.platform.common.core.java.classpath.finder;

import com.softicar.platform.common.testing.AbstractTest;
import java.io.File;
import java.util.Collection;
import org.junit.Test;

public class JavaClasspathFinderTest extends AbstractTest {

	@Test
	public void test() {

		Collection<File> classpaths = new JavaClasspathFinder().findAll();

		assertFalse(classpaths.isEmpty());
		assertTrue(
			classpaths//
				.stream()
				.map(it -> it.getPath())
				.filter(it -> it.contains("platform-common"))
				.findAny()
				.isPresent());
	}
}
