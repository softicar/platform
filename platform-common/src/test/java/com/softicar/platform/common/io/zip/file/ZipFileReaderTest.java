package com.softicar.platform.common.io.zip.file;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.io.buffer.ByteBuffer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ZipFileReaderTest extends Assert {

	private Path zipFile;
	private ZipFileReader zipReader;

	public ZipFileReaderTest() {

		this.zipFile = null;
		this.zipReader = null;
	}

	@Test
	public void testReadTextContentWithFileInRoot() {

		var content = zipReader.readTextContent(Path.of("foo.txt"));
		assertTrue(content.isPresent());
		assertEquals("foo content\n", content.get());
	}

	@Test
	public void testReadTextContentWithFileInSubDirectory() {

		var content = zipReader.readTextContent(Path.of("subdir/bar.txt"));
		assertTrue(content.isPresent());
		assertEquals("bar content\n", content.get());
	}

	@Test
	public void testReadTextContentWithFileInSubSubDirectory() {

		var content = zipReader.readTextContent(Path.of("subdir/subsubdir/baz.txt"));
		assertTrue(content.isPresent());
		assertEquals("baz content\n", content.get());
	}

	@Test
	public void testReadTextContentWithNonexistentFile() {

		var content = zipReader.readTextContent(Path.of("nonexistent.file"));
		assertFalse(content.isPresent());
	}

	@Test(expected = NullPointerException.class)
	public void testReadTextContentWithNull() {

		zipReader.readTextContent(null);
	}

	@Test
	public void testFindFirstPathByStringWithFileInRoot() {

		var path = zipReader.findFirstPath("foo.txt");
		assertTrue(path.isPresent());
		assertEquals("./foo.txt", path.get().toString());
	}

	@Test
	public void testFindFirstPathByStringWithFileInSubDirectory() {

		var path = zipReader.findFirstPath("bar.txt");
		assertTrue(path.isPresent());
		assertEquals("./subdir/bar.txt", path.get().toString());
	}

	@Test
	public void testFindFirstPathByStringWithFileInSubSubDirectory() {

		var path = zipReader.findFirstPath("baz.txt");
		assertTrue(path.isPresent());
		assertEquals("./subdir/subsubdir/baz.txt", path.get().toString());
	}

	@Test
	public void testFindFirstPathByStringWithNonexistentFile() {

		var path = zipReader.findFirstPath("nonexistent.file");
		assertFalse(path.isPresent());
	}

	@Test(expected = NullPointerException.class)
	public void testFindFirstPathByStringWithNull() {

		zipReader.findFirstPath((String) null);
	}

	@Test
	public void testFindFirstPathByPatternWithFileInRoot() {

		var path = zipReader.findFirstPath(Pattern.compile("f.o\\.txt"));
		assertTrue(path.isPresent());
		assertEquals("./foo.txt", path.get().toString());
	}

	@Test
	public void testFindFirstPathByPatternWithFileInSubDirectory() {

		var path = zipReader.findFirstPath(Pattern.compile("b.r\\.txt"));
		assertTrue(path.isPresent());
		assertEquals("./subdir/bar.txt", path.get().toString());
	}

	@Test
	public void testFindFirstPathByPatternWithFileInSubSubDirectory() {

		var path = zipReader.findFirstPath(Pattern.compile("b.z\\.txt"));
		assertTrue(path.isPresent());
		assertEquals("./subdir/subsubdir/baz.txt", path.get().toString());
	}

	@Test
	public void testFindFirstPathByPatternWithNonexistentFile() {

		var path = zipReader.findFirstPath(Pattern.compile("nonexistent\\.file"));
		assertFalse(path.isPresent());
	}

	@Test(expected = NullPointerException.class)
	public void testFindFirstPathByPatternWithNull() {

		zipReader.findFirstPath((Pattern) null);
	}

	@Test
	public void testFindPathsByStringWithFileInRoot() {

		var paths = zipReader.findPaths("foo.txt");
		assertEquals(2, paths.size());
		assertEquals("./foo.txt", paths.get(0).toString());
		assertEquals("./subdir/subsubdir/foo.txt", paths.get(1).toString());
	}

	@Test
	public void testFindPathsByStringWithFileInSubDirectory() {

		var paths = zipReader.findPaths("bar.txt");
		assertEquals(1, paths.size());
		assertEquals("./subdir/bar.txt", paths.get(0).toString());
	}

	@Test
	public void testFindPathsByStringWithFileInSubSubDirectory() {

		var paths = zipReader.findPaths("baz.txt");
		assertEquals(1, paths.size());
		assertEquals("./subdir/subsubdir/baz.txt", paths.get(0).toString());
	}

	@Test
	public void testFindPathsByStringWithNonexistentFile() {

		var paths = zipReader.findPaths("nonexistent.file");
		assertTrue(paths.isEmpty());
	}

	@Test(expected = NullPointerException.class)
	public void testFindPathsByStringWithNull() {

		zipReader.findPaths((String) null);
	}

	@Test
	public void testFindPathsByPatternWithFileInRoot() {

		var paths = zipReader.findPaths(Pattern.compile("f.o\\.txt"));
		assertEquals(2, paths.size());
		assertEquals("./foo.txt", paths.get(0).toString());
		assertEquals("./subdir/subsubdir/foo.txt", paths.get(1).toString());
	}

	@Test
	public void testFindPathsByPatternWithFileInSubDirectory() {

		var paths = zipReader.findPaths(Pattern.compile("b.r\\.txt"));
		assertEquals(1, paths.size());
		assertEquals("./subdir/bar.txt", paths.get(0).toString());
	}

	@Test
	public void testFindPathsByPatternWithFileInSubSubDirectory() {

		var paths = zipReader.findPaths(Pattern.compile("b.z\\.txt"));
		assertEquals(1, paths.size());
		assertEquals("./subdir/subsubdir/baz.txt", paths.get(0).toString());
	}

	@Test
	public void testFindPathsByPatternWithNonexistentFile() {

		var paths = zipReader.findPaths(Pattern.compile("nonexistent\\.file"));
		assertTrue(paths.isEmpty());
	}

	@Test(expected = NullPointerException.class)
	public void testFindPathsByPatternWithNull() {

		zipReader.findPaths((Pattern) null);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNull() {

		DevNull.swallow(new ZipFileReader(null));
	}

	// -------------------------------- setup and teardown -------------------------------- //

	@Before
	public void setup() {

		this.zipFile = createTemporaryZipFile();
		this.zipReader = new ZipFileReader(zipFile.toFile());
	}

	@After
	public void teardown() {

		deleteTemporaryZipFile();
	}

	private Path createTemporaryZipFile() {

		try {
			var byteBuffer = new ByteBuffer(ZipFileReaderTestResources.TEST_FILE.getResource()::getResourceAsStream);
			Path temporaryFile = Files.createTempFile(null, null);
			Files.write(temporaryFile, byteBuffer.getBytes());
			return temporaryFile;
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void deleteTemporaryZipFile() {

		Optional//
			.ofNullable(zipFile)
			.ifPresent(this::deleteFile);
	}

	private void deleteFile(Path path) {

		try {
			Files.deleteIfExists(path);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
