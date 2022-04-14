package com.softicar.platform.common.io.classpath.file;

import com.softicar.platform.common.testing.AbstractTest;
import java.io.File;
import org.junit.Test;

public class ZipClasspathFileTest extends AbstractTest {

	private final ZipClasspathFile classpathFile;
	private final ZipClasspathFile classpathFolder;

	public ZipClasspathFileTest() {

		this.classpathFile = new ZipClasspathFile(new File("/foo.jar"), "bar/baz.class", false);
		this.classpathFolder = new ZipClasspathFile(new File("/foo.jar"), "bar/dir", true);
	}

	@Test
	public void testGetName() {

		assertEquals("baz.class", classpathFile.getName());
		assertEquals("dir", classpathFolder.getName());
	}

	@Test
	public void testGetRelativePath() {

		assertEquals("bar/baz.class", classpathFile.getRelativePath());
		assertEquals("bar/dir", classpathFolder.getRelativePath());
	}

	@Test
	public void testIsDirectory() {

		assertFalse(classpathFile.isDirectory());
		assertTrue(classpathFolder.isDirectory());
	}
}
