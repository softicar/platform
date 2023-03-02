package com.softicar.platform.core.module.file.stored.content.store;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.testing.Asserts;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;

public class StoredFileFileSystemContentStoreTest extends Asserts {

	private final File storeRootDirectory;
	private final Path storeRootPath;
	private final DayTime startTime;

	public StoredFileFileSystemContentStoreTest() throws IOException {

		this.storeRootDirectory = Files.createTempDirectory(getClass().getSimpleName()).toFile();
		this.storeRootPath = storeRootDirectory.toPath();
		this.startTime = DayTime.now();
	}

	@After
	public void cleanup() throws IOException {

		FileUtils.deleteDirectory(storeRootDirectory);
	}

	@Test
	public void testGetLocation() {

		var store = createStore();
		assertTrue(store.getLocation().matches("/tmp/%s[0-9]+".formatted(getClass().getSimpleName())));
	}

	@Test
	public void testGetLocationWithNonexistentDirectory() {

		var store = createStore(new File("/nowhere"));
		assertEquals("/nowhere", store.getLocation());
	}

	@Test
	public void testIsEnabled() {

		var store = createStore();
		assertTrue(store.isEnabled());
	}

	@Test
	public void testIsReady() {

		var store = createStore();
		assertTrue(store.isReady());
	}

	@Test
	public void testIsReadyWithNonexistentDirectory() {

		var store = createStore(new File("/nowhere"));
		assertFalse(store.isReady());
	}

	@Test
	public void testIsReadyWithFileInsteadOfDirectory() {

		var file = createTempFile();
		var store = createStore(file);
		assertFalse(store.isReady());
	}

	@Test
	public void testGetFreeDiskSpace() {

		var store = createStore();
		assertTrue(store.getFreeDiskSpace() > 0);
	}

	@Test
	public void testCreateDirectoriesInStoreRoot() {

		var store = createStore();
		store.createDirectories("foo");
		assertTrue(storeRootPath.resolve("foo").toFile().isDirectory());
	}

	@Test
	public void testCreateDirectoriesWithExistingParentDirectory() {

		var store = createStore();
		store.createDirectories("foo");
		store.createDirectories("foo/bar");
		assertTrue(storeRootPath.resolve("foo/bar").toFile().isDirectory());
	}

	@Test
	public void testCreateDirectoriesWithNonExistingParentDirectory() {

		var store = createStore();
		store.createDirectories("foo/bar");
		assertTrue(storeRootPath.resolve("foo/bar").toFile().isDirectory());
	}

	@Test
	public void testCreateDirectoriesWithExistingDirectory() {

		var store = createStore();
		store.createDirectories("foo/bar");
		store.createDirectories("foo/bar");
		assertTrue(storeRootPath.resolve("foo/bar").toFile().isDirectory());
	}

	@Test
	public void testGetFileOutputStreamWithExistingDirectoryAndNonExistingFile() throws IOException {

		// setup
		var store = createStore();
		store.createDirectories("foo");

		// execute
		try (var stream = store.getFileOutputStream("foo/out.txt")) {
			stream.write("Hi there.".getBytes(StandardCharsets.UTF_8));
		}

		// assert
		assertEquals("Hi there.", Files.readString(storeRootPath.resolve("foo/out.txt")));
	}

	@Test
	public void testGetFileOutputStreamWithExistingDirectoryAndExistingFile() throws IOException {

		// setup
		var store = createStore();
		store.createDirectories("foo");
		writeFileContent("foo/out.txt", "Initial content.");

		// execute
		try (var stream = store.getFileOutputStream("foo/out.txt")) {
			stream.write("Hi there.".getBytes(StandardCharsets.UTF_8));
		}

		// assert
		assertEquals("Hi there.", Files.readString(storeRootPath.resolve("foo/out.txt")));
	}

	@Test(expected = SofticarIOException.class)
	public void testGetFileOutputStreamWithNonExistingDirectory() throws IOException {

		var store = createStore();
		try (var stream = store.getFileOutputStream("foo/out.txt")) {
			// we won't get here
		}
	}

	@Test
	public void testGetFileInputStreamWithExistingFile() throws IOException {

		// setup
		var store = createStore();
		store.createDirectories("foo");
		writeFileContent("foo/out.txt", "Hi there.");

		// execute
		try (var stream = store.getFileInputStream("foo/out.txt")) {
			// assert
			assertEquals("Hi there.", IOUtils.toString(stream, StandardCharsets.UTF_8));
		}

	}

	@Test(expected = SofticarIOException.class)
	public void testGetFileInputStreamWithNonExistingFile() throws IOException {

		var store = createStore();
		try (var stream = store.getFileInputStream("foo/out.txt")) {
			// we won't get here
		}
	}

	@Test
	public void testMoveFileWithExistingSourceFileAndNonExistingTargetFileAndExistingTargetDirectory() throws IOException {

		// setup
		var store = createStore();
		store.createDirectories("foo");
		store.createDirectories("bar");
		writeFileContent("foo/out.txt", "Hi there.");

		// execute
		store.moveFile("foo/out.txt", "bar/out2.txt");

		// assert
		try (var stream = store.getFileInputStream("bar/out2.txt")) {
			assertEquals("Hi there.", IOUtils.toString(stream, StandardCharsets.UTF_8));
		}
		assertFileDoesNotExist("foo/out.txt");
	}

	@Test
	public void testMoveFileWithExistingSourceFileAndNonExistingTargetFileAndNonExistingTargetDirectory() {

		// setup
		var store = createStore();
		store.createDirectories("foo");
		writeFileContent("foo/out.txt", "Hi there.");

		// execute and assert
		assertException(SofticarIOException.class, () -> store.moveFile("foo/out.txt", "bar/out2.txt"));
	}

	@Test
	public void testMoveFileWithExistingSourceFileAndExistingTargetFile() {

		// setup
		var store = createStore();
		store.createDirectories("foo");
		store.createDirectories("bar");
		writeFileContent("foo/out.txt", "Hi there.");
		writeFileContent("bar/out2.txt", "Hi again.");

		// execute and assert
		assertException(SofticarIOException.class, () -> store.moveFile("foo/out.txt", "bar/out2.txt"));
	}

	@Test
	public void testMoveFileWithNonExistingSourceFile() {

		// setup
		var store = createStore();
		store.createDirectories("foo");
		store.createDirectories("bar");

		// execute and assert
		assertException(SofticarIOException.class, () -> store.moveFile("foo/out.txt", "bar/out2.txt"));
	}

	@Test
	public void testDeleteFileWithExistingFile() {

		// setup
		var store = createStore();
		store.createDirectories("foo");
		writeFileContent("foo/out.txt", "Hi there.");

		// execute
		store.deleteFile("foo/out.txt");

		// assert
		assertFileDoesNotExist("foo/out.txt");
	}

	@Test
	public void testDeleteFileWithNonExistingFile() {

		// setup
		var store = createStore();
		store.createDirectories("foo");

		// execute and assert
		assertException(SofticarIOException.class, () -> store.deleteFile("foo/out.txt"));
	}

	@Test
	public void testExistsWithExistingFile() {

		// setup
		var store = createStore();
		store.createDirectories("foo");
		writeFileContent("foo/out.txt", "Hi there.");

		// execute and assert
		assertTrue(store.exists("foo/out.txt"));
	}

	@Test
	public void testExistsWithNonExistingFile() {

		// setup
		var store = createStore();
		store.createDirectories("foo");

		// execute and assert
		assertFalse(store.exists("foo/out.txt"));
	}

	@Test
	public void testExistsWithExistingDirectory() {

		// setup
		var store = createStore();
		store.createDirectories("foo");

		// execute and assert
		assertTrue(store.exists("foo"));
	}

	@Test
	public void testExistsWithNonExistingDirectory() {

		// setup
		var store = createStore();

		// execute and assert
		assertFalse(store.exists("foo"));
	}

	@Test
	public void testGetFileSizeWithExistingFile() {

		// setup
		var store = createStore();
		store.createDirectories("foo");
		writeFileContent("foo/out.txt", "Hi there.");

		// execute
		long size = store.getFileSize("foo/out.txt");

		// assert
		assertEquals("Hi there.".length(), size);
	}

	@Test
	public void testGetFileSizeWithNonExistingFile() {

		// setup
		var store = createStore();

		// execute
		long size = store.getFileSize("foo/out.txt");

		// assert
		assertEquals(0, size);
	}

	@Test
	public void testGetLastModifiedWithExistingFile() {

		// setup
		var store = createStore();
		store.createDirectories("foo");
		writeFileContent("foo/out.txt", "Hi there.");

		// execute
		DayTime lastModified = store.getLastModified("foo/out.txt");

		// assert
		assertTrue(lastModified.isAfterOrEqual(startTime));
	}

	@Test
	public void testGetLastModifiedWithNonExistingFile() {

		// setup
		var store = createStore();

		// execute
		DayTime lastModified = store.getLastModified("foo/out.txt");

		// assert
		assertEquals(DayTime.get1970().getDay(), lastModified.getDay());
	}

	@Test
	public void testGetLastModifiedWithExistingDirectory() {

		// setup
		var store = createStore();
		store.createDirectories("foo");

		// execute
		DayTime lastModified = store.getLastModified("foo");

		// assert
		assertTrue(lastModified.isAfterOrEqual(startTime));
	}

	@Test
	public void testGetLastModifiedWithNonExistingDirectory() {

		// setup
		var store = createStore();

		// execute
		DayTime lastModified = store.getLastModified("foo");

		// assert
		assertEquals(DayTime.get1970().getDay(), lastModified.getDay());
	}

	@Test
	public void testGetAllFilePaths() throws IOException {

		// setup
		var store = createStore();
		createHierarchicalContent(store);

		// execute
		List<String> filePaths = new ArrayList<>(store.getAllFilePaths());

		// assert
		assertEquals("root1.txt", filePaths.get(0));
		assertEquals("bar/bar1.txt", filePaths.get(1));
		assertEquals("bar/bar2.txt", filePaths.get(2));
		assertEquals("bar/guz/guz1.txt", filePaths.get(3));
		assertEquals("bar/guz/guz2.txt", filePaths.get(4));
		assertEquals("foo/foo1.txt", filePaths.get(5));
		assertEquals("foo/foo2.txt", filePaths.get(6));
		assertEquals(7, filePaths.size());
	}

	@Test
	public void testGetAllFilePathsWithEmptyStore() {

		// setup
		var store = createStore();

		// execute
		List<String> filePaths = new ArrayList<>(store.getAllFilePaths());

		// assert
		assertEquals(0, filePaths.size());
	}

	@Test
	public void testGetAllFilePathsWithExistingSubDirectory() throws IOException {

		// setup
		var store = createStore();
		createHierarchicalContent(store);

		// execute
		List<String> filePaths = new ArrayList<>(store.getAllFilePaths("bar"));

		// assert
		assertEquals("bar/bar1.txt", filePaths.get(0));
		assertEquals("bar/bar2.txt", filePaths.get(1));
		assertEquals("bar/guz/guz1.txt", filePaths.get(2));
		assertEquals("bar/guz/guz2.txt", filePaths.get(3));
		assertEquals(4, filePaths.size());
	}

	@Test
	public void testGetAllFilePathsWithExistingSubSubDirectory() throws IOException {

		// setup
		var store = createStore();
		createHierarchicalContent(store);

		// execute
		List<String> filePaths = new ArrayList<>(store.getAllFilePaths("bar/guz"));

		// assert
		assertEquals("bar/guz/guz1.txt", filePaths.get(0));
		assertEquals("bar/guz/guz2.txt", filePaths.get(1));
		assertEquals(2, filePaths.size());
	}

	@Test
	public void testGetAllFilePathsWithNonExistingSubDirectory() throws IOException {

		// setup
		var store = createStore();
		createHierarchicalContent(store);

		// execute and assert
		assertException(SofticarIOException.class, () -> store.getAllFilePaths("xxx"));
	}

	private StoredFileFileSystemContentStore createStore() {

		return createStore(storeRootDirectory);
	}

	private StoredFileFileSystemContentStore createStore(File directory) {

		return new StoredFileFileSystemContentStore(directory);
	}

	private File createTempFile() {

		try {
			var file = Files.createTempFile(getClass().getSimpleName(), "").toFile();
			file.deleteOnExit();
			return file;
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private void writeFileContent(String relativeFilePath, String content) {

		try {
			Files.writeString(storeRootPath.resolve(relativeFilePath), content);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		assertFileExists(relativeFilePath);
	}

	private void assertFileExists(String relativeFilePath) {

		assertTrue(storeRootPath.resolve(relativeFilePath).toFile().exists());
	}

	private void assertFileDoesNotExist(String relativeFilePath) {

		assertFalse(storeRootPath.resolve(relativeFilePath).toFile().exists());
	}

	private void createHierarchicalContent(StoredFileFileSystemContentStore store) throws IOException {

		store.getFileOutputStream("root1.txt").close();
		store.createDirectories("foo");
		store.getFileOutputStream("foo/foo1.txt").close();
		store.getFileOutputStream("foo/foo2.txt").close();
		store.createDirectories("bar");
		store.getFileOutputStream("bar/bar1.txt").close();
		store.getFileOutputStream("bar/bar2.txt").close();
		store.createDirectories("bar/guz");
		store.getFileOutputStream("bar/guz/guz1.txt").close();
		store.getFileOutputStream("bar/guz/guz2.txt").close();
	}
}