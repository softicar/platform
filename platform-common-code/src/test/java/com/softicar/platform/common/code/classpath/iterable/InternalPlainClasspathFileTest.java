package com.softicar.platform.common.code.classpath.iterable;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;

public class InternalPlainClasspathFileTest extends Assert {

	private final InternalPlainClasspathFile classpathFile;

	public InternalPlainClasspathFileTest() {

		File classpathRoot = new File("/foo/");
		File file = new File("/foo/bar/baz.class");
		this.classpathFile = new InternalPlainClasspathFile(classpathRoot, file);
	}

	@Test
	public void testGetName() {

		assertEquals("baz.class", classpathFile.getName());
	}

	@Test
	public void testGetRelativePath() {

		assertEquals("bar/baz.class", classpathFile.getRelativePath());
	}

	// cannot test isDirectory() and getInputStream() method without assuming a file system
}
