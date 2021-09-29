package com.softicar.platform.common.code.classpath.iterable;

import com.softicar.platform.common.io.zip.entry.ZipFileEntry;
import java.util.zip.ZipEntry;
import org.junit.Assert;
import org.junit.Test;

public class InternalZipClasspathFileTest extends Assert {

	private final InternalZipClasspathFile file;

	public InternalZipClasspathFileTest() {

		this.file = new InternalZipClasspathFile(new ZipFileEntry(null, new ZipEntry("foo/bar/baz.class")));
	}

	@Test
	public void testGetName() {

		assertEquals("baz.class", file.getName());
	}

	@Test
	public void testGetNameWithTrailingSlash() {

		InternalZipClasspathFile file0 = new InternalZipClasspathFile(new ZipFileEntry(null, new ZipEntry("foo/bar")));
		InternalZipClasspathFile file1 = new InternalZipClasspathFile(new ZipFileEntry(null, new ZipEntry("foo/bar/")));
		InternalZipClasspathFile file2 = new InternalZipClasspathFile(new ZipFileEntry(null, new ZipEntry("foo/bar//")));

		assertEquals("bar", file0.getName());
		assertEquals("bar", file1.getName());
		assertEquals("bar", file2.getName());
	}

	@Test
	public void testGetRelativePath() {

		assertEquals("foo/bar/baz.class", file.getRelativePath());
	}

	// cannot test isDirectory() and getInputStream() method without assuming a file system
}
