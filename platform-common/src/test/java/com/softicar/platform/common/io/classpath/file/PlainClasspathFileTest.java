package com.softicar.platform.common.io.classpath.file;

import com.softicar.platform.common.testing.AbstractTest;
import java.io.File;
import org.junit.Test;

public class PlainClasspathFileTest extends AbstractTest {

	private final PlainClasspathFile classpathFile;

	public PlainClasspathFileTest() {

		this.classpathFile = new PlainClasspathFile(new File("/foo"), new File("/foo/bar/baz.class"));
	}

	@Test
	public void testGetName() {

		assertEquals("baz.class", classpathFile.getName());
	}

	@Test
	public void testGetRelativePath() {

		assertEquals("bar/baz.class", classpathFile.getRelativePath());
	}
}
