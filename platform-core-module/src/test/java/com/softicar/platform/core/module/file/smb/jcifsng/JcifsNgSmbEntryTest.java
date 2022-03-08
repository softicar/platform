package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.core.module.file.smb.ISmbEntry;
import com.softicar.platform.core.module.file.smb.SmbIOException;
import com.softicar.platform.core.module.file.smb.SmbNoDirectoryException;
import com.softicar.platform.core.module.file.smb.SmbNoFileException;
import org.junit.Test;

public class JcifsNgSmbEntryTest extends AbtractJcifsNgSmbTest {

	@Test
	public void testConstructorWithUrlOfExistingFile() {

		testConstructorWithUrlOfExistingFile(someEntryUrl);
	}

	@Test
	public void testConstructorWithUrlOfExistingFileAndTailingSlash() {

		testConstructorWithUrlOfExistingFile(someEntryUrlSlash);
	}

	private void testConstructorWithUrlOfExistingFile(String url) {

		file(someEntryUrl).touch();
		DevNull.swallow(new JcifsNgSmbEntry(url, createContext()));
	}

	@Test
	public void testConstructorWithUrlOfExistingDirectory() {

		testConstructorWithUrlOfExistingDirectory(someEntryUrl);
	}

	@Test
	public void testConstructorWithUrlOfExistingDirectoryAndTailingSlash() {

		testConstructorWithUrlOfExistingDirectory(someEntryUrlSlash);
	}

	private void testConstructorWithUrlOfExistingDirectory(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		DevNull.swallow(new JcifsNgSmbEntry(url, createContext()));
	}

	@Test
	public void testConstructorWithUrlOfNonexistentEntry() {

		testConstructorWithUrlOfNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testConstructorWithUrlOfNonexistentEntryAndTailingSlash() {

		testConstructorWithUrlOfNonexistentEntry(someEntryUrlSlash);
	}

	private void testConstructorWithUrlOfNonexistentEntry(String url) {

		DevNull.swallow(new JcifsNgSmbEntry(url, createContext()));
	}

	@Test
	public void testConstructorWithUrlOfShareRoot() {

		testConstructorWithUrlOfShareRoot(shareUrl);
	}

	@Test
	public void testConstructorWithUrlOfShareRootAndTailingSlash() {

		testConstructorWithUrlOfShareRoot(shareUrlSlash);
	}

	private void testConstructorWithUrlOfShareRoot(String url) {

		DevNull.swallow(new JcifsNgSmbEntry(url, createContext()));
	}

	@Test
	public void testConstructorWithUrlNull() {

		assertException(NullPointerException.class, () -> new JcifsNgSmbEntry(null, createContext()));
	}

	@Test
	public void testConstructorWithContextNull() {

		assertException(NullPointerException.class, () -> new JcifsNgSmbEntry(someEntryUrl, null));
	}

	@Test
	public void testConstructorWithUrlWithoutProtocol() {

		assertException(//
			IllegalArgumentException.class,
			() -> new JcifsNgSmbEntry("garbage-url", createContext()),
			"The URL must start with 'smb://'.");
	}

	@Test
	public void testConstructorWithUrlWithWrongProtocol() {

		assertException(//
			IllegalArgumentException.class,
			() -> new JcifsNgSmbEntry("http://garbage-url", createContext()),
			"The URL must start with 'smb://'.");
	}

	@Test
	public void testConstructorWithUrlWithAdjacentSlashes() {

		assertException(//
			IllegalArgumentException.class,
			() -> new JcifsNgSmbEntry("smb://somewhere//something", createContext()),
			"The URL must not contain adjacent slashes after the protocol prefix.");
	}

	@Test
	public void testGetNameWithExistingFile() {

		testGetNameWithExistingFile(someEntryUrl);
	}

	@Test
	public void testGetNameWithExistingFileAndTailingSlash() {

		testGetNameWithExistingFile(someEntryUrlSlash);
	}

	private void testGetNameWithExistingFile(String url) {

		file(someEntryUrl).touch();
		assertEquals("someEntry", entry(url).getName());
	}

	@Test
	public void testGetNameWithExistingDirectory() {

		testGetNameWithExistingDirectory(someEntryUrl);
	}

	@Test
	public void testGetNameWithExistingDirectoryAndTailingSlash() {

		testGetNameWithExistingDirectory(someEntryUrlSlash);
	}

	private void testGetNameWithExistingDirectory(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		assertEquals("someEntry", entry(url).getName());
	}

	@Test
	public void testGetNameWithNonexistentEntry() {

		testGetNameWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testGetNameWithNonexistentEntryAndTailingSlash() {

		testGetNameWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testGetNameWithNonexistentEntry(String url) {

		assertEquals("someEntry", entry(url).getName());
	}

	@Test
	public void testGetNameWithShareRoot() {

		testGetNameWithShareRoot(shareUrl);
	}

	@Test
	public void testGetNameWithShareRootAndTailingSlash() {

		testGetNameWithShareRoot(shareUrlSlash);
	}

	private void testGetNameWithShareRoot(String url) {

		assertEquals("testshare", entry(url).getName());
	}

	@Test
	public void testGetUrlWithExistingFile() {

		testGetUrlWithExistingFile(someEntryUrl);
	}

	@Test
	public void testGetUrlWithExistingFileAndTailingSlash() {

		testGetUrlWithExistingFile(someEntryUrlSlash);
	}

	private void testGetUrlWithExistingFile(String url) {

		file(someEntryUrl).touch();
		assertEquals(someEntryUrl, entry(url).getUrl());
	}

	@Test
	public void testGetUrlWithExistingDirectory() {

		testGetUrlWithExistingDirectory(someEntryUrl);
	}

	@Test
	public void testGetUrlWithExistingDirectoryAndTailingSlash() {

		testGetUrlWithExistingDirectory(someEntryUrlSlash);
	}

	private void testGetUrlWithExistingDirectory(String url) {

		directory(someEntryUrl).makeDirectories();
		assertEquals(someEntryUrlSlash, entry(url).getUrl());
	}

	@Test
	public void testGetUrlWithNonexistentEntry() {

		testGetUrlWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testGetUrlWithNonexistentEntryAndTailingSlash() {

		testGetUrlWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testGetUrlWithNonexistentEntry(String url) {

		assertEquals(url, entry(url).getUrl());
	}

	@Test
	public void testGetUrlWithShareRoot() {

		testGetUrlWithShareRoot(shareUrl);
	}

	@Test
	public void testGetUrlWithShareRootAndTailingSlash() {

		testGetUrlWithShareRoot(shareUrlSlash);
	}

	private void testGetUrlWithShareRoot(String url) {

		assertEquals(shareUrlSlash, entry(url).getUrl());
	}

	@Test
	public void testExistsWithExistingFile() {

		testExistsWithExistingFile(someEntryUrl);
	}

	@Test
	public void testExistsWithExistingFileAndTailingSlash() {

		testExistsWithExistingFile(someEntryUrlSlash);
	}

	private void testExistsWithExistingFile(String url) {

		file(someEntryUrl).touch();
		assertTrue(entry(url).exists());
	}

	@Test
	public void testExistsWithExistingDirectory() {

		testExistsWithExistingDirectory(someEntryUrl);
	}

	@Test
	public void testExistsWithExistingDirectoryAndTailingSlash() {

		testExistsWithExistingDirectory(someEntryUrlSlash);
	}

	private void testExistsWithExistingDirectory(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		assertTrue(entry(url).exists());
	}

	@Test
	public void testExistsWithNonexistentEntry() {

		testExistsWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testExistsWithNonexistentEntryAndTailingSlash() {

		testExistsWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testExistsWithNonexistentEntry(String url) {

		assertFalse(entry(url).exists());
	}

	@Test
	public void testExistsWithShareRoot() {

		testExistsWithShareRoot(shareUrl);
	}

	@Test
	public void testExistsWithShareRootAndTailingSlash() {

		testExistsWithShareRoot(shareUrlSlash);
	}

	private void testExistsWithShareRoot(String url) {

		assertTrue(entry(url).exists());
	}

	@Test
	public void testDeleteWithExistingFile() {

		testDeleteWithExistingFile(someEntryUrl);
	}

	@Test
	public void testDeleteWithExistingFileAndTailingSlash() {

		testDeleteWithExistingFile(someEntryUrlSlash);
	}

	private void testDeleteWithExistingFile(String url) {

		file(someEntryUrl).touch();
		ISmbEntry entry = entry(url);
		entry.delete();
		assertFalse(entry.exists());
	}

	@Test
	public void testDeleteWithExistingFileInDirectory() {

		testDeleteWithExistingFileInDirectory(someDirUrlSlash.concat("someFile"));
	}

	@Test
	public void testDeleteWithExistingFileInNestedDirectoryAndTailingSlash() {

		testDeleteWithExistingFileInDirectory(someDirUrlSlash.concat("someFile/"));
	}

	private void testDeleteWithExistingFileInDirectory(String url) {

		populateShareWithSimpleStructure();
		ISmbEntry entry = entry(url);
		entry.delete();
		assertFalse(entry.exists());
	}

	@Test
	public void testDeleteWithExistingDirectoryEmpty() {

		testDeleteWithExistingDirectoryEmpty(someEntryUrl);
	}

	@Test
	public void testDeleteWithExistingDirectoryEmptyAndTailingSlash() {

		testDeleteWithExistingDirectoryEmpty(someEntryUrlSlash);
	}

	private void testDeleteWithExistingDirectoryEmpty(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		ISmbEntry entry = entry(url);
		entry.delete();
		assertFalse(entry.exists());
	}

	@Test
	public void testDeleteWithExistingDirectoryNonEmpty() {

		testDeleteWithExistingDirectoryNonEmpty(someDirUrl);
	}

	@Test
	public void testDeleteWithExistingDirectoryNonEmptyAndTailingSlash() {

		testDeleteWithExistingDirectoryNonEmpty(someDirUrlSlash);
	}

	private void testDeleteWithExistingDirectoryNonEmpty(String url) {

		populateShareWithSimpleStructure();
		ISmbEntry entry = entry(url);
		entry.delete();
		assertFalse(entry.exists());
	}

	@Test
	public void testDeleteWithNonexistentEntry() {

		testDeleteWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testDeleteWithNonexistentEntryAndTailingSlash() {

		testDeleteWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testDeleteWithNonexistentEntry(String url) {

		ISmbEntry entry = entry(url);
		assertException(SmbIOException.class, entry::delete);
	}

	@Test
	public void testDeleteWithShareRoot() {

		testDeleteWithShareRoot(shareUrl);
	}

	@Test
	public void testDeleteWithShareRootAndTailingSlash() {

		testDeleteWithShareRoot(shareUrlSlash);
	}

	private void testDeleteWithShareRoot(String url) {

		ISmbEntry entry = entry(url);
		assertException(SmbIOException.class, entry::delete);
	}

	@Test
	public void testDeleteIfExistsWithExistingFile() {

		testDeleteIfExistsWithExistingFile(someEntryUrl);
	}

	@Test
	public void testDeleteIfExistsWithExistingFileAndTailingSlash() {

		testDeleteIfExistsWithExistingFile(someEntryUrlSlash);
	}

	private void testDeleteIfExistsWithExistingFile(String url) {

		file(someEntryUrl).touch();
		ISmbEntry entry = entry(url);
		assertTrue(entry.deleteIfExists());
		assertFalse(entry.exists());
	}

	@Test
	public void testDeleteIfExistsWithExistingDirectoryEmpty() {

		testDeleteIfExistsWithExistingDirectoryEmpty(someEntryUrl);
	}

	@Test
	public void testDeleteIfExistsWithExistingDirectoryEmptyAndTailingSlash() {

		testDeleteIfExistsWithExistingDirectoryEmpty(someEntryUrlSlash);
	}

	private void testDeleteIfExistsWithExistingDirectoryEmpty(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		ISmbEntry entry = entry(url);
		assertTrue(entry.deleteIfExists());
		assertFalse(entry.exists());
	}

	@Test
	public void testDeleteIfExistsWithExistingDirectoryNonEmpty() {

		testDeleteIfExistsWithExistingDirectoryNonEmpty(someDirUrl);
	}

	@Test
	public void testDeleteIfExistsWithExistingDirectoryNonEmptyAndTailingSlash() {

		testDeleteIfExistsWithExistingDirectoryNonEmpty(someDirUrlSlash);
	}

	private void testDeleteIfExistsWithExistingDirectoryNonEmpty(String url) {

		populateShareWithSimpleStructure();
		ISmbEntry entry = entry(url);
		assertTrue(entry.deleteIfExists());
		assertFalse(entry.exists());
	}

	@Test
	public void testDeleteIfExistsWithNonexistentEntry() {

		testDeleteIfExistsWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testDeleteIfExistsWithNonexistentEntryAndTailingSlash() {

		testDeleteIfExistsWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testDeleteIfExistsWithNonexistentEntry(String url) {

		ISmbEntry entry = entry(url);
		assertFalse(entry.deleteIfExists());
		assertFalse(entry.exists());
	}

	@Test
	public void testDeleteIfExistsWithShareRoot() {

		testDeleteIfExistsWithShareRoot(shareUrl);
	}

	@Test
	public void testDeleteIfExistsWithShareRootAndTailingSlash() {

		testDeleteIfExistsWithShareRoot(shareUrlSlash);
	}

	private void testDeleteIfExistsWithShareRoot(String url) {

		ISmbEntry entry = entry(url);
		assertException(SmbIOException.class, entry::deleteIfExists);
	}

	@Test
	public void testGetLastModifiedDateWithExistingFile() {

		testGetLastModifiedDateWithExistingFile(someEntryUrl);
	}

	@Test
	public void testGetLastModifiedDateWithExistingFileAndTailingSlash() {

		testGetLastModifiedDateWithExistingFile(someEntryUrlSlash);
	}

	private void testGetLastModifiedDateWithExistingFile(String url) {

		file(someEntryUrl).touch();
		assertTrue(entry(url).getLastModifiedDate().compareTo(startTime) >= 0);
	}

	@Test
	public void testGetLastModifiedDateWithExistingDirectory() {

		testGetLastModifiedDateWithExistingDirectory(someEntryUrl);
	}

	@Test
	public void testGetLastModifiedDateWithExistingDirectoryAndTailingSlash() {

		testGetLastModifiedDateWithExistingDirectory(someEntryUrlSlash);
	}

	private void testGetLastModifiedDateWithExistingDirectory(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		assertTrue(entry(url).getLastModifiedDate().compareTo(startTime) >= 0);
	}

	@Test
	public void testGetLastModifiedDateWithNonexistentEntry() {

		testGetLastModifiedDateWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testGetLastModifiedDateWithNonexistentEntryAndTailingSlash() {

		testGetLastModifiedDateWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testGetLastModifiedDateWithNonexistentEntry(String url) {

		assertYear1970(entry(url).getLastModifiedDate());
	}

	@Test
	public void testGetLastModifiedDateWithShareRoot() {

		testGetLastModifiedDateWithShareRoot(shareUrl);
	}

	@Test
	public void testGetLastModifiedDateWithShareRootAndTailingSlash() {

		testGetLastModifiedDateWithShareRoot(shareUrlSlash);
	}

	private void testGetLastModifiedDateWithShareRoot(String url) {

		assertYear1970(entry(url).getLastModifiedDate());
	}

	@Test
	public void testGetParentDirectoryWithExistingFileInShareRoot() {

		testGetParentDirectoryWithExistingFileInShareRoot(someEntryUrl);
	}

	@Test
	public void testGetParentDirectoryWithExistingFileInShareRootAndTailingSlash() {

		testGetParentDirectoryWithExistingFileInShareRoot(someEntryUrlSlash);
	}

	private void testGetParentDirectoryWithExistingFileInShareRoot(String url) {

		file(someEntryUrl).touch();
		assertEquals(shareUrlSlash, entry(url).getParentDirectory().getUrl());
	}

	@Test
	public void testGetParentDirectoryWithExistingFileInDirectory() {

		testGetParentDirectoryWithExistingFileInDirectory(shareUrlSlash.concat("someDir/subdir/deepFile"));
	}

	@Test
	public void testGetParentDirectoryWithExistingFileInDirectoryAndTailingSlash() {

		testGetParentDirectoryWithExistingFileInDirectory(shareUrlSlash.concat("someDir/subdir/deepFile/"));
	}

	private void testGetParentDirectoryWithExistingFileInDirectory(String url) {

		populateShareWithSimpleStructure();
		ISmbEntry entry = entry(url);
		assertTrue(entry.exists());
		assertEquals(shareUrlSlash.concat("someDir/subdir/"), entry.getParentDirectory().getUrl());
	}

	@Test
	public void testGetParentDirectoryWithExistingDirectory() {

		testGetParentDirectoryWithExistingDirectory(someEntryUrl);
	}

	@Test
	public void testGetParentDirectoryWithExistingDirectoryAndTailingSlash() {

		testGetParentDirectoryWithExistingDirectory(someEntryUrlSlash);
	}

	private void testGetParentDirectoryWithExistingDirectory(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		assertEquals(shareUrlSlash, entry(url).getParentDirectory().getUrl());
	}

	@Test
	public void testGetParentDirectoryWithNonexistentEntryInShareRoot() {

		testGetParentDirectoryWithNonexistentEntryInShareRoot(someEntryUrl);
	}

	@Test
	public void testGetParentDirectoryWithNonexistentEntryInShareRootAndTailingSlash() {

		testGetParentDirectoryWithNonexistentEntryInShareRoot(someEntryUrlSlash);
	}

	private void testGetParentDirectoryWithNonexistentEntryInShareRoot(String url) {

		assertEquals(shareUrlSlash, entry(url).getParentDirectory().getUrl());
	}

	@Test
	public void testGetParentDirectoryWithNonexistentEntryInExistingDirectory() {

		testGetParentDirectoryWithNonexistentEntryInExistingDirectory(shareUrlSlash.concat("someDir/emptySubDir/nonexistent"));
	}

	@Test
	public void testGetParentDirectoryWithNonexistentEntryInExistingDirectoryAndTailingSlash() {

		testGetParentDirectoryWithNonexistentEntryInExistingDirectory(shareUrlSlash.concat("someDir/emptySubDir/nonexistent/"));
	}

	private void testGetParentDirectoryWithNonexistentEntryInExistingDirectory(String url) {

		populateShareWithSimpleStructure();
		ISmbEntry entry = entry(url);
		assertFalse(entry.exists());
		assertEquals(shareUrlSlash.concat("someDir/emptySubDir/"), entry.getParentDirectory().getUrl());
	}

	@Test
	public void testGetParentDirectoryWithNonexistentEntryInNonExistentDirectory() {

		testGetParentDirectoryWithNonexistentEntryInNonExistentDirectory(shareUrlSlash.concat("foo/bar/baz"));
	}

	@Test
	public void testGetParentDirectoryWithNonexistentEntryInNonExistentDirectoryAndTailingSlash() {

		testGetParentDirectoryWithNonexistentEntryInNonExistentDirectory(shareUrlSlash.concat("foo/bar/baz/"));
	}

	private void testGetParentDirectoryWithNonexistentEntryInNonExistentDirectory(String url) {

		ISmbEntry entry = entry(url);
		assertEquals(shareUrlSlash.concat("foo/bar/"), entry.getParentDirectory().getUrl());
	}

	@Test
	public void testGetParentDirectoryWithShareRoot() {

		testGetParentDirectoryWithShareRoot(shareUrl);
	}

	@Test
	public void testGetParentDirectoryWithShareRootAndTailingSlash() {

		testGetParentDirectoryWithShareRoot(shareUrlSlash);
	}

	private void testGetParentDirectoryWithShareRoot(String url) {

		assertSmbServerUrl(entry(url).getParentDirectory().getUrl());
	}

	@Test
	public void testGetFreeDiskSpaceWithExistingFile() {

		testGetFreeDiskSpaceWithExistingFile(someEntryUrl);
	}

	@Test
	public void testGetFreeDiskSpaceWithExistingFileAndTailingSlash() {

		testGetFreeDiskSpaceWithExistingFile(someEntryUrlSlash);
	}

	private void testGetFreeDiskSpaceWithExistingFile(String url) {

		file(someEntryUrl).touch();
		assertTrue(entry(url).getFreeDiskSpace() > 0);
	}

	@Test
	public void testGetFreeDiskSpaceWithExistingDirectory() {

		testGetFreeDiskSpaceWithExistingDirectory(someEntryUrl);
	}

	@Test
	public void testGetFreeDiskSpaceWithExistingDirectoryAndTailingSlash() {

		testGetFreeDiskSpaceWithExistingDirectory(someEntryUrlSlash);
	}

	private void testGetFreeDiskSpaceWithExistingDirectory(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		assertTrue(entry(url).getFreeDiskSpace() > 0);
	}

	@Test
	public void testGetFreeDiskSpaceWithNonexistentEntry() {

		testGetFreeDiskSpaceWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testGetFreeDiskSpaceWithNonexistentEntryAndTailingSlash() {

		testGetFreeDiskSpaceWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testGetFreeDiskSpaceWithNonexistentEntry(String url) {

		assertEquals(0, entry(url).getFreeDiskSpace());
	}

	@Test
	public void testGetFreeDiskSpaceWithShareRoot() {

		testGetFreeDiskSpaceWithShareRoot(shareUrl);
	}

	@Test
	public void testGetFreeDiskSpaceWithShareRootAndTailingSlash() {

		testGetFreeDiskSpaceWithShareRoot(shareUrlSlash);
	}

	private void testGetFreeDiskSpaceWithShareRoot(String url) {

		assertTrue(entry(url).getFreeDiskSpace() > 0);
	}

	@Test
	public void testIsFileWithExistingFile() {

		testIsFileWithExistingFile(someEntryUrl);
	}

	@Test
	public void testIsFileWithExistingFileAndTailingSlash() {

		testIsFileWithExistingFile(someEntryUrlSlash);
	}

	private void testIsFileWithExistingFile(String url) {

		file(someEntryUrl).touch();
		assertTrue(entry(url).isFile());
	}

	@Test
	public void testIsFileWithExistingDirectory() {

		testIsFileWithExistingDirectory(someEntryUrl);
	}

	@Test
	public void testIsFileWithExistingDirectoryAndTailingSlash() {

		testIsFileWithExistingDirectory(someEntryUrlSlash);
	}

	private void testIsFileWithExistingDirectory(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		assertFalse(entry(url).isFile());
	}

	@Test
	public void testIsFileWithNonexistentEntry() {

		testIsFileWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testIsFileWithNonexistentEntryAndTailingSlash() {

		testIsFileWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testIsFileWithNonexistentEntry(String url) {

		assertFalse(entry(url).isFile());
	}

	@Test
	public void testIsFileWithShareRoot() {

		testIsFileWithShareRoot(shareUrl);
	}

	@Test
	public void testIsFileWithShareRootAndTailingSlash() {

		testIsFileWithShareRoot(shareUrlSlash);
	}

	private void testIsFileWithShareRoot(String url) {

		assertFalse(entry(url).isFile());
	}

	@Test
	public void testIsDirectoryWithExistingFile() {

		testIsDirectoryWithExistingFile(someEntryUrl);
	}

	@Test
	public void testIsDirectoryWithExistingFileAndTailingSlash() {

		testIsDirectoryWithExistingFile(someEntryUrlSlash);
	}

	private void testIsDirectoryWithExistingFile(String url) {

		file(someEntryUrl).touch();
		assertFalse(entry(url).isDirectory());
	}

	@Test
	public void testIsDirectoryWithExistingDirectory() {

		testIsDirectoryWithExistingDirectory(someEntryUrl);
	}

	@Test
	public void testIsDirectoryWithExistingDirectoryAndTailingSlash() {

		testIsDirectoryWithExistingDirectory(someEntryUrlSlash);
	}

	private void testIsDirectoryWithExistingDirectory(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		assertTrue(entry(url).isDirectory());
	}

	@Test
	public void testIsDirectoryWithNonexistentEntry() {

		testIsDirectoryWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testIsDirectoryWithNonexistentEntryAndTailingSlash() {

		testIsDirectoryWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testIsDirectoryWithNonexistentEntry(String url) {

		assertFalse(entry(url).isDirectory());
	}

	@Test
	public void testIsDirectoryWithShareRoot() {

		testIsDirectoryWithShareRoot(shareUrl);
	}

	@Test
	public void testIsDirectoryWithShareRootAndTailingSlash() {

		testIsDirectoryWithShareRoot(shareUrlSlash);
	}

	private void testIsDirectoryWithShareRoot(String url) {

		assertTrue(entry(url).isDirectory());
	}

	@Test
	public void testAsFileWithExistingFile() {

		testAsFileWithExistingFile(someEntryUrl);
	}

	@Test
	public void testAsFileWithExistingFileAndTailingSlash() {

		testAsFileWithExistingFile(someEntryUrlSlash);
	}

	private void testAsFileWithExistingFile(String url) {

		file(someEntryUrl).touch();
		assertEquals(someEntryUrl, entry(url).asFile().get().getUrl());
	}

	@Test
	public void testAsFileWithExistingDirectory() {

		testAsFileWithExistingDirectory(someEntryUrl);
	}

	@Test
	public void testAsFileWithExistingDirectoryAndTailingSlash() {

		testAsFileWithExistingDirectory(someEntryUrlSlash);
	}

	private void testAsFileWithExistingDirectory(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		assertTrue(entry(url).asFile().isEmpty());
	}

	@Test
	public void testAsFileWithNonexistentEntry() {

		testAsFileWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testAsFileWithNonexistentEntryAndTailingSlash() {

		testAsFileWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testAsFileWithNonexistentEntry(String url) {

		assertEquals(someEntryUrl, entry(url).asFile().get().getUrl());
	}

	@Test
	public void testAsFileWithShareRoot() {

		testAsFileWithShareRoot(shareUrl);
	}

	@Test
	public void testAsFileWithShareRootAndTailingSlash() {

		testAsFileWithShareRoot(shareUrlSlash);
	}

	private void testAsFileWithShareRoot(String url) {

		directory(shareUrlSlash).makeDirectories();
		assertTrue(entry(url).asFile().isEmpty());
	}

	@Test
	public void testAsFileOrThrowWithExistingFile() {

		testAsFileOrThrowWithExistingFile(someEntryUrl);
	}

	@Test
	public void testAsFileOrThrowWithExistingFileAndTailingSlash() {

		testAsFileOrThrowWithExistingFile(someEntryUrlSlash);
	}

	private void testAsFileOrThrowWithExistingFile(String url) {

		file(someEntryUrl).touch();
		assertEquals(someEntryUrl, entry(url).asFileOrThrow().getUrl());
	}

	@Test
	public void testAsFileOrThrowWithExistingDirectory() {

		testAsFileOrThrowWithExistingDirectory(someEntryUrl);
	}

	@Test
	public void testAsFileOrThrowWithExistingDirectoryAndTailingSlash() {

		testAsFileOrThrowWithExistingDirectory(someEntryUrlSlash);
	}

	private void testAsFileOrThrowWithExistingDirectory(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		ISmbEntry entry = entry(url);
		assertException(SmbNoFileException.class, entry::asFileOrThrow);
	}

	@Test
	public void testAsFileOrThrowWithNonexistentEntry() {

		testAsFileOrThrowWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testAsFileOrThrowWithNonexistentEntryAndTailingSlash() {

		testAsFileOrThrowWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testAsFileOrThrowWithNonexistentEntry(String url) {

		assertEquals(someEntryUrl, entry(url).asFileOrThrow().getUrl());
	}

	@Test
	public void testAsFileOrThrowWithShareRoot() {

		testAsFileOrThrowWithShareRoot(shareUrl);
	}

	@Test
	public void testAsFileOrThrowWithShareRootAndTailingSlash() {

		testAsFileOrThrowWithShareRoot(shareUrlSlash);
	}

	private void testAsFileOrThrowWithShareRoot(String url) {

		ISmbEntry entry = entry(url);
		assertException(SmbNoFileException.class, entry::asFileOrThrow);
	}

	@Test
	public void testAsDirectoryWithExistingFile() {

		testAsDirectoryWithExistingFile(someEntryUrl);
	}

	@Test
	public void testAsDirectoryWithExistingFileAndTailingSlash() {

		testAsDirectoryWithExistingFile(someEntryUrlSlash);
	}

	private void testAsDirectoryWithExistingFile(String url) {

		file(someEntryUrl).touch();
		assertTrue(entry(url).asDirectory().isEmpty());
	}

	@Test
	public void testAsDirectoryWithExistingDirectory() {

		testAsDirectoryWithExistingDirectory(someEntryUrl);
	}

	@Test
	public void testAsDirectoryWithExistingDirectoryAndTailingSlash() {

		testAsDirectoryWithExistingDirectory(someEntryUrlSlash);
	}

	private void testAsDirectoryWithExistingDirectory(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		assertEquals(someEntryUrlSlash, entry(url).asDirectory().get().getUrl());
	}

	@Test
	public void testAsDirectoryWithNonexistentEntry() {

		testAsDirectoryWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testAsDirectoryWithNonexistentEntryAndTailingSlash() {

		testAsDirectoryWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testAsDirectoryWithNonexistentEntry(String url) {

		assertEquals(someEntryUrlSlash, entry(url).asDirectory().get().getUrl());
	}

	@Test
	public void testAsDirectoryWithShareRoot() {

		testAsDirectoryWithShareRoot(shareUrl);
	}

	@Test
	public void testAsDirectoryWithShareRootAndTailingSlash() {

		testAsDirectoryWithShareRoot(shareUrlSlash);
	}

	private void testAsDirectoryWithShareRoot(String url) {

		ISmbEntry entry = entry(url);
		assertEquals(shareUrlSlash, entry.asDirectory().get().getUrl());
	}

	@Test
	public void testAsDirectoryOrThrowWithExistingFile() {

		testAsDirectoryOrThrowWithExistingFile(someEntryUrl);
	}

	@Test
	public void testAsDirectoryOrThrowWithExistingFileAndTailingSlash() {

		testAsDirectoryOrThrowWithExistingFile(someEntryUrlSlash);
	}

	private void testAsDirectoryOrThrowWithExistingFile(String url) {

		file(someEntryUrl).touch();
		ISmbEntry entry = entry(url);
		assertException(SmbNoDirectoryException.class, entry::asDirectoryOrThrow);
	}

	@Test
	public void testAsDirectoryOrThrowWithExistingDirectory() {

		testAsDirectoryOrThrowWithExistingDirectory(someEntryUrl);
	}

	@Test
	public void testAsDirectoryOrThrowWithExistingDirectoryAndTailingSlash() {

		testAsDirectoryOrThrowWithExistingDirectory(someEntryUrlSlash);
	}

	private void testAsDirectoryOrThrowWithExistingDirectory(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		assertEquals(someEntryUrlSlash, entry(url).asDirectoryOrThrow().getUrl());
	}

	@Test
	public void testAsDirectoryOrThrowWithNonexistentEntry() {

		testAsDirectoryOrThrowWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testAsDirectoryOrThrowWithNonexistentEntryAndTailingSlash() {

		testAsDirectoryOrThrowWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testAsDirectoryOrThrowWithNonexistentEntry(String url) {

		assertEquals(someEntryUrlSlash, entry(url).asDirectoryOrThrow().getUrl());
	}

	@Test
	public void testAsDirectoryOrThrowWithShareRoot() {

		testAsDirectoryOrThrowWithShareRoot(shareUrl);
	}

	@Test
	public void testAsDirectoryOrThrowWithShareRootAndTailingSlash() {

		testAsDirectoryOrThrowWithShareRoot(shareUrlSlash);
	}

	private void testAsDirectoryOrThrowWithShareRoot(String url) {

		assertEquals(shareUrlSlash, entry(url).asDirectoryOrThrow().getUrl());
	}
}
