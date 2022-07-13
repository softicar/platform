package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbEntry;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbCredentials;
import com.softicar.platform.core.module.file.smb.SmbNoDirectoryException;
import com.softicar.platform.core.module.file.smb.SmbNoFileException;
import org.junit.Test;

public class JcifsNgSmbClientTest extends AbtractJcifsNgSmbTest {

	private static final SmbCredentials INVALID_CREDENTIALS = new SmbCredentials("x", "x", "x");
	private final JcifsNgSmbClient client;

	public JcifsNgSmbClientTest() {

		this.client = new JcifsNgSmbClient();
	}

	@Test
	public void testGetEntryWithExistingFile() {

		testGetEntryWithExistingFile(someFileUrl);
	}

	@Test
	public void testGetEntryWithExistingFileAndTailingSlash() {

		testGetEntryWithExistingFile(someFileUrlSlash);
	}

	private void testGetEntryWithExistingFile(String url) {

		touchFile(someFileUrl);

		ISmbEntry entry = client.getEntry(url, credentials);

		assertNotNull(entry);
		assertEquals("someFile", entry.getName());
		assertEquals(someFileUrl, entry.getUrl());
		assertTrue(entry.exists());
		assertTrue(startTime.compareTo(entry.getLastModifiedDate()) <= 0);
		assertEquals(shareUrlSlash, entry.getParentDirectory().getUrl());
		assertTrue(entry.getFreeDiskSpace() > 0);
		assertTrue(entry.isFile());
		assertFalse(entry.isDirectory());
		assertEquals(someFileUrl, entry.asFile().get().getUrl());
		assertEquals(someFileUrl, entry.asFileOrThrow().getUrl());
		assertTrue(entry.asDirectory().isEmpty());
		assertException(SmbNoDirectoryException.class, entry::asDirectoryOrThrow);
	}

	@Test
	public void testGetEntryWithExistingDirectory() {

		testGetEntryWithExistingDirectory(someDirUrl);
	}

	@Test
	public void testGetEntryWithExistingDirectoryAndTailingSlash() {

		testGetEntryWithExistingDirectory(someDirUrlSlash);
	}

	private void testGetEntryWithExistingDirectory(String url) {

		makeDirectory(someDirUrlSlash);

		ISmbEntry entry = client.getEntry(url, credentials);

		assertNotNull(entry);
		assertEquals("someDir", entry.getName());
		assertEquals(someDirUrlSlash, entry.getUrl());
		assertTrue(entry.exists());
		assertTrue(startTime.compareTo(entry.getLastModifiedDate()) <= 0);
		assertEquals(shareUrlSlash, entry.getParentDirectory().getUrl());
		assertTrue(entry.getFreeDiskSpace() > 0);
		assertFalse(entry.isFile());
		assertTrue(entry.isDirectory());
		assertTrue(entry.asFile().isEmpty());
		assertException(SmbNoFileException.class, entry::asFileOrThrow);
		assertEquals(someDirUrlSlash, entry.asDirectory().get().getUrl());
		assertEquals(someDirUrlSlash, entry.asDirectoryOrThrow().getUrl());
	}

	@Test
	public void testGetEntryWithNonexistentEntry() {

		testGetEntryWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testGetEntryWithNonexistentEntryAndTailingSlash() {

		testGetEntryWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testGetEntryWithNonexistentEntry(String url) {

		ISmbEntry entry = client.getEntry(url, credentials);

		assertNotNull(entry);
		assertEquals("someEntry", entry.getName());
		assertEquals(url, entry.getUrl());
		assertFalse(entry.exists());
		assertYear1970(entry.getLastModifiedDate());
		assertEquals(shareUrlSlash, entry.getParentDirectory().getUrl());
		assertEquals(0, entry.getFreeDiskSpace());
		assertFalse(entry.isFile());
		assertFalse(entry.isDirectory());
		assertEquals(someEntryUrl, entry.asFile().get().getUrl());
		assertEquals(someEntryUrl, entry.asFileOrThrow().getUrl());
		assertEquals(someEntryUrlSlash, entry.asDirectory().get().getUrl());
		assertEquals(someEntryUrlSlash, entry.asDirectoryOrThrow().getUrl());
	}

	@Test
	public void testGetEntryWithNonexistentEntryAndInvalidCredentials() {

		assertExceptionMessageContains(//
			IDisplayString.create("Logon failure"),
			() -> client.getEntry(someEntryUrl, INVALID_CREDENTIALS));
	}

	@Test
	public void testGetEntryWithShareRoot() {

		testGetEntryWithShareRoot(shareUrl);
	}

	@Test
	public void testGetEntryWithShareRootAndTailingSlash() {

		testGetEntryWithShareRoot(shareUrlSlash);
	}

	private void testGetEntryWithShareRoot(String url) {

		ISmbEntry entry = client.getEntry(url, credentials);

		assertNotNull(entry);
		assertEquals("testshare", entry.getName());
		assertEquals(shareUrlSlash, entry.getUrl());
		assertTrue(entry.exists());
		assertYear1970(entry.getLastModifiedDate());
		assertSmbServerUrl(entry.getParentDirectory().getUrl());
		assertTrue(entry.getFreeDiskSpace() > 0);
		assertFalse(entry.isFile());
		assertTrue(entry.isDirectory());
		assertTrue(entry.asFile().isEmpty());
		assertException(SmbNoFileException.class, entry::asFileOrThrow);
		assertEquals(shareUrlSlash, entry.asDirectory().get().getUrl());
		assertEquals(shareUrlSlash, entry.asDirectoryOrThrow().getUrl());
	}

	@Test
	public void testGetEntryWithUrlWithoutProtocol() {

		assertException(//
			IllegalArgumentException.class,
			"The URL must start with 'smb://'.",
			() -> client.getEntry("garbage-url", credentials));
	}

	@Test
	public void testGetEntryWithUrlWithWrongProtocol() {

		assertException(//
			IllegalArgumentException.class,
			"The URL must start with 'smb://'.",
			() -> client.getEntry("http://garbage-url", credentials));
	}

	@Test
	public void testGetEntryWithUrlWithAdjacentSlashes() {

		assertException(//
			IllegalArgumentException.class,
			"The URL must not contain adjacent slashes after the protocol prefix.",
			() -> client.getEntry("smb://somewhere//something", credentials));
	}

	@Test(expected = NullPointerException.class)
	public void testGetEntryWithUrlNull() {

		client.getEntry(null, credentials);
	}

	@Test(expected = NullPointerException.class)
	public void testGetEntryWithCredentialsNull() {

		client.getEntry(someFileUrl, null);
	}

	@Test
	public void testGetFileWithExistingFile() {

		testGetFileWithExistingFile(someFileUrl);
	}

	@Test
	public void testGetFileWithExistingFileAndTailingSlash() {

		testGetFileWithExistingFile(someFileUrlSlash);
	}

	private void testGetFileWithExistingFile(String url) {

		touchFile(someFileUrl);

		ISmbFile file = client.getFile(url, credentials);

		assertNotNull(file);
		assertEquals("someFile", file.getName());
		assertEquals(someFileUrl, file.getUrl());
		assertTrue(file.exists());
		assertTrue(startTime.compareTo(file.getLastModifiedDate()) <= 0);
		assertEquals(shareUrlSlash, file.getParentDirectory().getUrl());
		assertTrue(file.getFreeDiskSpace() > 0);
		assertTrue(file.isFile());
		assertFalse(file.isDirectory());
		assertEquals(someFileUrl, file.asFile().get().getUrl());
		assertEquals(someFileUrl, file.asFileOrThrow().getUrl());
		assertTrue(file.asDirectory().isEmpty());
		assertException(SmbNoDirectoryException.class, file::asDirectoryOrThrow);
	}

	@Test
	public void testGetFileWithExistingDirectory() {

		makeDirectory(someDirUrl);
		assertException(SmbNoFileException.class, () -> client.getFile(someDirUrl, credentials));
	}

	@Test
	public void testGetFileWithExistingDirectoryAndTailingSlash() {

		makeDirectory(someDirUrlSlash);
		assertException(SmbNoFileException.class, () -> client.getFile(someDirUrlSlash, credentials));
	}

	@Test
	public void testGetFileWithNonexistentEntry() {

		testGetFileWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testGetFileWithNonexistentEntryAndTailingSlash() {

		testGetFileWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testGetFileWithNonexistentEntry(String url) {

		ISmbFile file = client.getFile(url, credentials);

		assertNotNull(file);
		assertEquals("someEntry", file.getName());
		assertEquals(someEntryUrl, file.getUrl());
		assertFalse(file.exists());
		assertYear1970(file.getLastModifiedDate());
		assertEquals(shareUrlSlash, file.getParentDirectory().getUrl());
		assertEquals(0, file.getFreeDiskSpace());
		assertFalse(file.isFile());
		assertFalse(file.isDirectory());
		assertEquals(someEntryUrl, file.asFile().get().getUrl());
		assertEquals(someEntryUrl, file.asFileOrThrow().getUrl());
		assertEquals(someEntryUrlSlash, file.asDirectory().get().getUrl());
		assertEquals(someEntryUrlSlash, file.asDirectoryOrThrow().getUrl());
	}

	@Test
	public void testGetFileWithNonexistentEntryAndInvalidCredentials() {

		assertExceptionMessageContains(//
			IDisplayString.create("Logon failure"),
			() -> client.getFile(someEntryUrl, INVALID_CREDENTIALS));
	}

	@Test
	public void testGetFileWithShareRoot() {

		assertException(SmbNoFileException.class, () -> client.getFile(shareUrl, credentials));
	}

	@Test
	public void testGetFileWithShareRootAndTailingSlash() {

		assertException(SmbNoFileException.class, () -> client.getFile(shareUrlSlash, credentials));
	}

	@Test
	public void testGetFileWithUrlWithoutProtocol() {

		assertException(//
			IllegalArgumentException.class,
			"The URL must start with 'smb://'.",
			() -> client.getFile("garbage-url", credentials));
	}

	@Test
	public void testGetFileWithUrlWithWrongProtocol() {

		assertException(//
			IllegalArgumentException.class,
			"The URL must start with 'smb://'.",
			() -> client.getFile("http://garbage-url", credentials));
	}

	@Test
	public void testGetFileWithUrlWithAdjacentSlashes() {

		assertException(//
			IllegalArgumentException.class,
			"The URL must not contain adjacent slashes after the protocol prefix.",
			() -> client.getFile("smb://somewhere//something", credentials));
	}

	@Test(expected = NullPointerException.class)
	public void testGetFileWithUrlNull() {

		client.getFile(null, credentials);
	}

	@Test(expected = NullPointerException.class)
	public void testGetFileWithCredentialsNull() {

		client.getFile(someFileUrl, null);
	}

	@Test
	public void testGetDirectoryWithExistingFile() {

		touchFile(someFileUrl);
		assertException(SmbNoDirectoryException.class, () -> client.getDirectory(someFileUrl, credentials));
	}

	@Test
	public void testGetDirectoryWithExistingFileAndTailingSlash() {

		touchFile(someFileUrl);
		assertException(SmbNoDirectoryException.class, () -> client.getDirectory(someFileUrlSlash, credentials));
	}

	@Test
	public void testGetDirectoryWithExistingDirectory() {

		testGetDirectoryWithExistingDirectory(someDirUrl);
	}

	@Test
	public void testGetDirectoryWithExistingDirectoryAndTailingSlash() {

		testGetDirectoryWithExistingDirectory(someDirUrlSlash);
	}

	private void testGetDirectoryWithExistingDirectory(String url) {

		makeDirectory(someDirUrlSlash);

		ISmbDirectory directory = client.getDirectory(url, credentials);

		assertNotNull(directory);
		assertEquals("someDir", directory.getName());
		assertEquals(someDirUrlSlash, directory.getUrl());
		assertTrue(directory.exists());
		assertTrue(startTime.compareTo(directory.getLastModifiedDate()) <= 0);
		assertEquals(shareUrlSlash, directory.getParentDirectory().getUrl());
		assertTrue(directory.getFreeDiskSpace() > 0);
		assertFalse(directory.isFile());
		assertTrue(directory.isDirectory());
		assertTrue(directory.asFile().isEmpty());
		assertException(SmbNoFileException.class, directory::asFileOrThrow);
		assertEquals(someDirUrlSlash, directory.asDirectory().get().getUrl());
		assertEquals(someDirUrlSlash, directory.asDirectoryOrThrow().getUrl());
	}

	@Test
	public void testGetDirectoryWithNonexistentEntry() {

		testGetDirectoryWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testGetDirectoryWithNonexistentEntryAndTailingSlash() {

		testGetDirectoryWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testGetDirectoryWithNonexistentEntry(String url) {

		ISmbDirectory directory = client.getDirectory(url, credentials);

		assertNotNull(directory);
		assertEquals("someEntry", directory.getName());
		assertEquals(someEntryUrlSlash, directory.getUrl());
		assertFalse(directory.exists());
		assertYear1970(directory.getLastModifiedDate());
		assertEquals(shareUrlSlash, directory.getParentDirectory().getUrl());
		assertEquals(0, directory.getFreeDiskSpace());
		assertFalse(directory.isFile());
		assertFalse(directory.isDirectory());
		assertEquals(someEntryUrl, directory.asFile().get().getUrl());
		assertEquals(someEntryUrl, directory.asFileOrThrow().getUrl());
		assertEquals(someEntryUrlSlash, directory.asDirectory().get().getUrl());
		assertEquals(someEntryUrlSlash, directory.asDirectoryOrThrow().getUrl());
	}

	@Test
	public void testGetDirectoryWithNonexistentEntryAndInvalidCredentials() {

		assertExceptionMessageContains(//
			IDisplayString.create("Logon failure"),
			() -> client.getDirectory(someEntryUrl, INVALID_CREDENTIALS));
	}

	@Test
	public void testGetDirectoryWithShareRoot() {

		testGetDirectoryWithShareRoot(shareUrl);
	}

	@Test
	public void testGetDirectoryWithShareRootAndTailingSlash() {

		testGetDirectoryWithShareRoot(shareUrlSlash);
	}

	private void testGetDirectoryWithShareRoot(String url) {

		ISmbDirectory directory = client.getDirectory(url, credentials);

		assertNotNull(directory);
		assertEquals("testshare", directory.getName());
		assertEquals(shareUrlSlash, directory.getUrl());
		assertTrue(directory.exists());
		assertYear1970(directory.getLastModifiedDate());
		assertSmbServerUrl(directory.getParentDirectory().getUrl());
		assertTrue(directory.getFreeDiskSpace() > 0);
		assertFalse(directory.isFile());
		assertTrue(directory.isDirectory());
		assertTrue(directory.asFile().isEmpty());
		assertException(SmbNoFileException.class, directory::asFileOrThrow);
		assertEquals(shareUrlSlash, directory.asDirectory().get().getUrl());
		assertEquals(shareUrlSlash, directory.asDirectoryOrThrow().getUrl());
	}

	@Test
	public void testGetDirectoryWithUrlWithoutProtocol() {

		assertException(//
			IllegalArgumentException.class,
			"The URL must start with 'smb://'.",
			() -> client.getDirectory("garbage-url", credentials));
	}

	@Test
	public void testGetDirectoryWithUrlWithWrongProtocol() {

		assertException(//
			IllegalArgumentException.class,
			"The URL must start with 'smb://'.",
			() -> client.getDirectory("http://garbage-url", credentials));
	}

	@Test
	public void testGetDirectoryWithUrlWithAdjacentSlashes() {

		assertException(//
			IllegalArgumentException.class,
			"The URL must not contain adjacent slashes after the protocol prefix.",
			() -> client.getDirectory("smb://somewhere//something", credentials));
	}

	@Test(expected = NullPointerException.class)
	public void testGetDirectoryWithUrlNull() {

		client.getDirectory(null, credentials);
	}

	@Test(expected = NullPointerException.class)
	public void testGetDirectoryWithCredentialsNull() {

		client.getDirectory(someFileUrl, null);
	}

	private ISmbFile touchFile(String url) {

		ISmbFile file = client.getFile(url, credentials);
		assertFalse(file.exists());
		file.touch();
		assertTrue(file.exists());
		return file;
	}

	private ISmbDirectory makeDirectory(String url) {

		ISmbDirectory directory = client.getDirectory(url, credentials);
		assertFalse(directory.exists());
		directory.makeDirectories();
		assertTrue(directory.exists());
		return directory;
	}
}
