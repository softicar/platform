package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbEntry;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbIOException;
import com.softicar.platform.core.module.file.smb.SmbNoDirectoryException;
import java.util.List;
import org.junit.Test;

public class JcifsNgSmbDirectoryTest extends AbtractJcifsNgSmbTest {

	@Test
	public void testConstructorWithUrlOfExistingDirectory() {

		testConstructorWithUrlOfExistingDirectory(someEntryUrl);
	}

	@Test
	public void testConstructorWithUrlOfExistingDirectoryAndTailingSlash() {

		testConstructorWithUrlOfExistingDirectory(someEntryUrlSlash);
	}

	private void testConstructorWithUrlOfExistingDirectory(String url) {

		directory(someEntryUrl).makeDirectories();
		DevNull.swallow(new JcifsNgSmbDirectory(url, createContext()));
	}

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
		assertException(SmbNoDirectoryException.class, () -> new JcifsNgSmbDirectory(url, createContext()));
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

		DevNull.swallow(new JcifsNgSmbDirectory(url, createContext()));
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

		DevNull.swallow(new JcifsNgSmbDirectory(url, createContext()));
	}

	@Test
	public void testConstructorWithUrlNull() {

		assertException(NullPointerException.class, () -> new JcifsNgSmbDirectory(null, createContext()));
	}

	@Test
	public void testConstructorWithContextNull() {

		assertException(NullPointerException.class, () -> new JcifsNgSmbDirectory(someEntryUrl, null));
	}

	@Test
	public void testConstructorWithUrlWithoutProtocol() {

		assertException(//
			IllegalArgumentException.class,
			"The URL must start with 'smb://'.",
			() -> new JcifsNgSmbDirectory("garbage-url", createContext()));
	}

	@Test
	public void testConstructorWithUrlWithWrongProtocol() {

		assertException(//
			IllegalArgumentException.class,
			"The URL must start with 'smb://'.",
			() -> new JcifsNgSmbDirectory("http://garbage-url", createContext()));
	}

	@Test
	public void testConstructorWithUrlWithAdjacentSlashes() {

		assertException(//
			IllegalArgumentException.class,
			"The URL must not contain adjacent slashes after the protocol prefix.",
			() -> new JcifsNgSmbDirectory("smb://somewhere//something", createContext()));
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

		directory(someEntryUrlSlash).makeDirectories();
		assertEquals(someEntryUrlSlash, directory(url).getUrl());
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

		assertEquals(someEntryUrlSlash, directory(url).getUrl());
	}

	@Test
	public void testMakeDirectoriesWithExistingDirectory() {

		testMakeDirectoriesWithExistingDirectory(someEntryUrl);
	}

	@Test
	public void testMakeDirectoriesWithExistingDirectoryAndTailingSlash() {

		testMakeDirectoriesWithExistingDirectory(someEntryUrlSlash);
	}

	private void testMakeDirectoriesWithExistingDirectory(String url) {

		directory(someEntryUrlSlash).makeDirectories();
		ISmbDirectory directory = directory(url);
		ISmbDirectory returnedDirectory = directory.makeDirectories();
		assertTrue(directory.exists());
		assertEquals(someEntryUrlSlash, returnedDirectory.getUrl());
	}

	@Test
	public void testMakeDirectoriesWithNonexistentEntry() {

		testMakeDirectoriesWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testMakeDirectoriesWithNonexistentEntryAndTailingSlash() {

		testMakeDirectoriesWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testMakeDirectoriesWithNonexistentEntry(String url) {

		ISmbDirectory directory = directory(url);
		ISmbDirectory returnedDirectory = directory.makeDirectories();
		assertTrue(directory.exists());
		assertEquals(someEntryUrlSlash, returnedDirectory.getUrl());
	}

	@Test
	public void testMakeDirectoriesWithNonexistentDeeperEntry() {

		ISmbDirectory directory = directory(someEntryUrlSlash.concat("deeper/"));
		ISmbDirectory returnedDirectory = directory.makeDirectories();
		assertTrue(directory.exists());
		assertEquals(someEntryUrlSlash.concat("deeper/"), returnedDirectory.getUrl());
	}

	@Test
	public void testMakeDirectoriesWithNonexistentDeeperEntryAndPartiallyExistingParents() {

		directory(someEntryUrlSlash).makeDirectories();
		ISmbDirectory directory = directory(someEntryUrlSlash.concat("deeper/evendeeper"));
		ISmbDirectory returnedDirectory = directory.makeDirectories();
		assertTrue(directory.exists());
		assertEquals(someEntryUrlSlash.concat("deeper/evendeeper/"), returnedDirectory.getUrl());
	}

	@Test
	public void testListFilesWithExistingEmptyDirectory() {

		testListFilesWithExistingEmptyDirectory(someDirUrl);
	}

	@Test
	public void testListFilesWithExistingEmptyDirectoryAndTailingSlash() {

		testListFilesWithExistingEmptyDirectory(someDirUrlSlash);
	}

	private void testListFilesWithExistingEmptyDirectory(String url) {

		directory(someDirUrlSlash).makeDirectories();
		List<ISmbFile> files = directory(url).listFiles();
		assertTrue(files.isEmpty());
	}

	@Test
	public void testListFilesWithExistingNonEmptyDirectory() {

		testListFilesWithExistingNonEmptyDirectory(someDirUrl);
	}

	@Test
	public void testListFilesWithExistingNonEmptyDirectoryAndTailingSlash() {

		testListFilesWithExistingNonEmptyDirectory(someDirUrlSlash);
	}

	private void testListFilesWithExistingNonEmptyDirectory(String url) {

		populateShareWithSimpleStructure();
		List<ISmbFile> files = directory(url).listFiles();
		assertEquals(2, files.size());
		assertEquals(shareUrlSlash.concat("someDir/otherFile"), files.get(0).getUrl());
		assertEquals(shareUrlSlash.concat("someDir/someFile"), files.get(1).getUrl());
	}

	@Test
	public void testListFilesWithEmptyShareRoot() {

		testListFilesWithEmptyShareRoot(shareUrl);
	}

	@Test
	public void testListFilesWithEmptyShareRootAndTailingSlash() {

		testListFilesWithEmptyShareRoot(shareUrlSlash);
	}

	private void testListFilesWithEmptyShareRoot(String url) {

		List<ISmbFile> files = directory(url).listFiles();
		assertTrue(files.isEmpty());
	}

	@Test
	public void testListFilesWithNonEmptyShareRoot() {

		testListFilesWithNonEmptyShareRoot(shareUrl);
	}

	@Test
	public void testListFilesWithNonEmptyShareRootAndTailingSlash() {

		testListFilesWithNonEmptyShareRoot(shareUrlSlash);
	}

	private void testListFilesWithNonEmptyShareRoot(String url) {

		file(someFileUrl).touch();
		file(someEntryUrl).touch();
		List<ISmbFile> files = directory(url).listFiles();
		assertEquals(2, files.size());
		assertEquals(shareUrlSlash.concat("someEntry"), files.get(0).getUrl());
		assertEquals(shareUrlSlash.concat("someFile"), files.get(1).getUrl());
	}

	@Test
	public void testListFilesWithNonexistentEntry() {

		testListFilesWithNonexistentEntry(someDirUrl);
	}

	@Test
	public void testListFilesWithNonexistentEntryAndTailingSlash() {

		testListFilesWithNonexistentEntry(someDirUrlSlash);
	}

	private void testListFilesWithNonexistentEntry(String url) {

		assertException(SmbIOException.class, directory(url)::listFiles);
	}

	@Test
	public void testListFilesRecursivelyWithExistingEmptyDirectory() {

		testListFilesRecursivelyWithExistingEmptyDirectory(someDirUrl);
	}

	@Test
	public void testListFilesRecursivelyWithExistingEmptyDirectoryAndTailingSlash() {

		testListFilesRecursivelyWithExistingEmptyDirectory(someDirUrlSlash);
	}

	private void testListFilesRecursivelyWithExistingEmptyDirectory(String url) {

		directory(someDirUrl).makeDirectories();
		List<ISmbFile> files = directory(url).listFilesRecursively();
		assertTrue(files.isEmpty());
	}

	@Test
	public void testListFilesRecursivelyWithExistingNonEmptyDirectory() {

		testListFilesRecursivelyWithExistingNonEmptyDirectory(someDirUrl);
	}

	@Test
	public void testListFilesRecursivelyWithExistingNonEmptyDirectoryAndTailingSlash() {

		testListFilesRecursivelyWithExistingNonEmptyDirectory(someDirUrlSlash);
	}

	private void testListFilesRecursivelyWithExistingNonEmptyDirectory(String url) {

		populateShareWithSimpleStructure();
		List<ISmbFile> files = directory(url).listFilesRecursively();
		assertEquals(3, files.size());
		assertEquals(someDirUrlSlash.concat("otherFile"), files.get(0).getUrl());
		assertEquals(someDirUrlSlash.concat("someFile"), files.get(1).getUrl());
		assertEquals(someDirUrlSlash.concat("subDir/deepFile"), files.get(2).getUrl());
	}

	@Test
	public void testListFilesRecursivelyWithEmptyShareRoot() {

		testListFilesRecursivelyWithEmptyShareRoot(shareUrl);
	}

	@Test
	public void testListFilesRecursivelyWithEmptyShareRootAndTailingSlash() {

		testListFilesRecursivelyWithEmptyShareRoot(shareUrlSlash);
	}

	private void testListFilesRecursivelyWithEmptyShareRoot(String url) {

		List<ISmbFile> files = directory(url).listFilesRecursively();
		assertTrue(files.isEmpty());
	}

	@Test
	public void testListFilesRecursivelyWithNonEmptyShareRoot() {

		testListFilesRecursivelyWithNonEmptyShareRoot(shareUrl);
	}

	@Test
	public void testListFilesRecursivelyWithNonEmptyShareRootAndTailingSlash() {

		testListFilesRecursivelyWithNonEmptyShareRoot(shareUrlSlash);
	}

	private void testListFilesRecursivelyWithNonEmptyShareRoot(String url) {

		file(someFileUrl).touch();
		file(someEntryUrl).touch();
		List<ISmbFile> files = directory(url).listFilesRecursively();
		assertEquals(shareUrlSlash.concat("someEntry"), files.get(0).getUrl());
		assertEquals(shareUrlSlash.concat("someFile"), files.get(1).getUrl());
	}

	@Test
	public void testListFilesRecursivelyWithNonexistentEntry() {

		testListFilesRecursivelyWithNonexistentEntry(someDirUrl);
	}

	@Test
	public void testListFilesRecursivelyWithNonexistentEntryAndTailingSlash() {

		testListFilesRecursivelyWithNonexistentEntry(someDirUrlSlash);
	}

	private void testListFilesRecursivelyWithNonexistentEntry(String url) {

		assertException(SmbIOException.class, directory(url)::listFilesRecursively);
	}

	@Test
	public void testListDirectoriesWithExistingEmptyDirectory() {

		testListDirectoriesWithExistingEmptyDirectory(someDirUrl);
	}

	@Test
	public void testListDirectoriesWithExistingEmptyDirectoryAndTailingSlash() {

		testListDirectoriesWithExistingEmptyDirectory(someDirUrlSlash);
	}

	private void testListDirectoriesWithExistingEmptyDirectory(String url) {

		directory(someDirUrlSlash).makeDirectories();
		List<ISmbDirectory> directories = directory(url).listDirectories();
		assertTrue(directories.isEmpty());
	}

	@Test
	public void testListDirectoriesWithExistingNonEmptyDirectory() {

		testListDirectoriesWithExistingNonEmptyDirectory(someDirUrl);
	}

	@Test
	public void testListDirectoriesWithExistingNonEmptyDirectoryAndTailingSlash() {

		testListDirectoriesWithExistingNonEmptyDirectory(someDirUrlSlash);
	}

	private void testListDirectoriesWithExistingNonEmptyDirectory(String url) {

		populateShareWithSimpleStructure();
		List<ISmbDirectory> directories = directory(url).listDirectories();
		assertEquals(2, directories.size());
		assertEquals(someDirUrlSlash.concat("emptySubDir/"), directories.get(0).getUrl());
		assertEquals(someDirUrlSlash.concat("subDir/"), directories.get(1).getUrl());
	}

	@Test
	public void testListDirectoriesWithEmptyShareRoot() {

		testListDirectoriesWithEmptyShareRoot(shareUrl);
	}

	@Test
	public void testListDirectoriesWithEmptyShareRootAndTailingSlash() {

		testListDirectoriesWithEmptyShareRoot(shareUrlSlash);
	}

	private void testListDirectoriesWithEmptyShareRoot(String url) {

		List<ISmbDirectory> directories = directory(url).listDirectories();
		assertTrue(directories.isEmpty());
	}

	@Test
	public void testListDirectoriesWithNonEmptyShareRoot() {

		testListDirectoriesWithNonEmptyShareRoot(shareUrl);
	}

	@Test
	public void testListDirectoriesWithNonEmptyShareRootAndTailingSlash() {

		testListDirectoriesWithNonEmptyShareRoot(shareUrlSlash);
	}

	private void testListDirectoriesWithNonEmptyShareRoot(String url) {

		directory(someEntryUrl).makeDirectories();
		directory(someDirUrl).makeDirectories();
		List<ISmbDirectory> directories = directory(url).listDirectories();
		assertEquals(2, directories.size());
		assertEquals(someDirUrlSlash, directories.get(0).getUrl());
		assertEquals(someEntryUrlSlash, directories.get(1).getUrl());
	}

	@Test
	public void testListDirectoriesWithNonexistentEntry() {

		testListDirectoriesWithNonexistentEntry(someDirUrl);
	}

	@Test
	public void testListDirectoriesWithNonexistentEntryAndTailingSlash() {

		testListDirectoriesWithNonexistentEntry(someDirUrlSlash);
	}

	private void testListDirectoriesWithNonexistentEntry(String url) {

		assertException(SmbIOException.class, directory(url)::listDirectories);
	}

	@Test
	public void testListEntriesWithExistingEmptyDirectory() {

		testListEntriesWithExistingEmptyDirectory(someDirUrl);
	}

	@Test
	public void testListEntriesWithExistingEmptyDirectoryAndTailingSlash() {

		testListEntriesWithExistingEmptyDirectory(someDirUrlSlash);
	}

	private void testListEntriesWithExistingEmptyDirectory(String url) {

		directory(someDirUrl).makeDirectories();
		List<ISmbEntry> entries = directory(url).listEntries();
		assertTrue(entries.isEmpty());
	}

	@Test
	public void testListEntriesWithExistingNonEmptyDirectory() {

		testListEntriesWithExistingNonEmptyDirectory(someDirUrl);
	}

	@Test
	public void testListEntriesWithExistingNonEmptyDirectoryAndTailingSlash() {

		testListEntriesWithExistingNonEmptyDirectory(someDirUrlSlash);
	}

	private void testListEntriesWithExistingNonEmptyDirectory(String url) {

		populateShareWithSimpleStructure();
		List<ISmbEntry> entries = directory(url).listEntries();
		assertEquals(4, entries.size());
		assertEquals(someDirUrlSlash.concat("emptySubDir/"), entries.get(0).getUrl());
		assertEquals(someDirUrlSlash.concat("otherFile"), entries.get(1).getUrl());
		assertEquals(someDirUrlSlash.concat("someFile"), entries.get(2).getUrl());
		assertEquals(someDirUrlSlash.concat("subDir/"), entries.get(3).getUrl());
	}

	@Test
	public void testListEntriesWithEmptyShareRoot() {

		testListEntriesWithEmptyShareRoot(shareUrl);
	}

	@Test
	public void testListEntriesWithEmptyShareRootAndTailingSlash() {

		testListEntriesWithEmptyShareRoot(shareUrlSlash);
	}

	private void testListEntriesWithEmptyShareRoot(String url) {

		List<ISmbEntry> entries = directory(url).listEntries();
		assertTrue(entries.isEmpty());
	}

	@Test
	public void testListEntriesWithNonEmptyShareRoot() {

		testListEntriesWithNonEmptyShareRoot(shareUrl);
	}

	@Test
	public void testListEntriesWithNonEmptyShareRootAndTailingSlash() {

		testListEntriesWithNonEmptyShareRoot(shareUrlSlash);
	}

	private void testListEntriesWithNonEmptyShareRoot(String url) {

		directory(someEntryUrl).makeDirectories();
		directory(someDirUrl).makeDirectories();
		file(someFileUrl).touch();
		List<ISmbEntry> entries = directory(url).listEntries();
		assertEquals(3, entries.size());
		assertEquals(someDirUrlSlash, entries.get(0).getUrl());
		assertEquals(someEntryUrlSlash, entries.get(1).getUrl());
		assertEquals(someFileUrl, entries.get(2).getUrl());
	}

	@Test
	public void testListEntriesWithNonexistentEntry() {

		testListEntriesWithNonexistentEntry(someDirUrl);
	}

	@Test
	public void testListEntriesWithNonexistentEntryAndTailingSlash() {

		testListEntriesWithNonexistentEntry(someDirUrlSlash);
	}

	private void testListEntriesWithNonexistentEntry(String url) {

		assertException(SmbIOException.class, directory(url)::listEntries);
	}

	@Test
	public void testGetFileWithExistingFileInExistingDirectory() {

		directory(someDirUrlSlash).makeDirectories();
		file(someDirUrlSlash.concat("someFile")).touch();
		ISmbFile file = directory(someDirUrlSlash).getFile("someFile");
		assertTrue(file.exists());
	}

	@Test
	public void testGetFileWithNonexistentFileInExistingDirectory() {

		directory(someDirUrlSlash).makeDirectories();
		ISmbFile file = directory(someDirUrlSlash).getFile("someFile");
		assertFalse(file.exists());
	}

	@Test
	public void testGetFileWithFileInNonexistentDirectory() {

		ISmbFile file = directory(someDirUrlSlash).getFile("someFile");
		assertFalse(file.exists());
	}

	@Test
	public void testGetFileWithExistingFileInExistingSubDirectory() {

		directory(someDirUrlSlash.concat("subDir/")).makeDirectories();
		file(someDirUrlSlash.concat("subDir/someFile")).touch();
		ISmbFile file = directory(someDirUrlSlash).getFile("subDir/someFile");
		assertTrue(file.exists());
	}

	@Test
	public void testGetFileWithNonexistentFileInExistingSubDirectory() {

		directory(someDirUrlSlash.concat("subDir/")).makeDirectories();
		ISmbFile file = directory(someDirUrlSlash).getFile("subDir/someFile");
		assertFalse(file.exists());
	}

	@Test
	public void testGetFileWithFileInNonexistentSubDirectory() {

		ISmbFile file = directory(someDirUrlSlash).getFile("subDir/someFile");
		assertFalse(file.exists());
	}

	@Test
	public void testGetFileWithExistingFileInShareRoot() {

		file(someFileUrl).touch();
		ISmbDirectory directory = directory(shareUrlSlash);
		ISmbFile file = directory.getFile("someFile");
		assertTrue(file.exists());
	}

	@Test
	public void testGetFileWithNonexistentFileInShareRoot() {

		ISmbDirectory directory = directory(shareUrlSlash);
		ISmbFile file = directory.getFile("someFile");
		assertFalse(file.exists());
	}

	@Test
	public void testGetFileWithAdjacentSlashesInName() {

		ISmbDirectory directory = directory(shareUrlSlash);
		assertException(//
			IllegalArgumentException.class,
			"The URL must not contain adjacent slashes after the protocol prefix.",
			() -> directory.getFile("someFile//"));
	}

	@Test
	public void testGetDirectoryWithExistingDirectoryInExistingDirectory() {

		testGetDirectoryWithExistingDirectoryInExistingDirectory("subDir");
	}

	@Test
	public void testGetDirectoryWithExistingDirectoryInExistingDirectoryAndTailingSlash() {

		testGetDirectoryWithExistingDirectoryInExistingDirectory("subDir/");
	}

	private void testGetDirectoryWithExistingDirectoryInExistingDirectory(String name) {

		directory(someDirUrlSlash).makeDirectories();
		directory(someDirUrlSlash.concat("subDir/")).makeDirectories();
		ISmbDirectory directory = directory(someDirUrlSlash).getDirectory(name);
		assertTrue(directory.exists());
	}

	@Test
	public void testGetDirectoryWithNonexistentDirectoryInExistingDirectory() {

		testGetDirectoryWithNonexistentDirectoryInExistingDirectory("subDir");
	}

	@Test
	public void testGetDirectoryWithNonexistentDirectoryInExistingDirectoryAndTailingSlash() {

		testGetDirectoryWithNonexistentDirectoryInExistingDirectory("subDir/");
	}

	private void testGetDirectoryWithNonexistentDirectoryInExistingDirectory(String name) {

		directory(someDirUrlSlash).makeDirectories();
		ISmbDirectory directory = directory(someDirUrlSlash).getDirectory(name);
		assertFalse(directory.exists());
	}

	@Test
	public void testGetDirectoryWithDirectoryInNonexistentDirectory() {

		testGetDirectoryWithDirectoryInNonexistentDirectory("subDir");
	}

	@Test
	public void testGetDirectoryWithDirectoryInNonexistentDirectoryAndTailingSlash() {

		testGetDirectoryWithDirectoryInNonexistentDirectory("subDir/");
	}

	private void testGetDirectoryWithDirectoryInNonexistentDirectory(String name) {

		ISmbDirectory directory = directory(someDirUrlSlash).getDirectory(name);
		assertFalse(directory.exists());
	}

	@Test
	public void testGetDirectoryWithExistingDirectoryInShareRoot() {

		testGetDirectoryWithExistingDirectoryInShareRoot("someDir");
	}

	@Test
	public void testGetDirectoryWithExistingDirectoryInShareRootAndTailingSlash() {

		testGetDirectoryWithExistingDirectoryInShareRoot("someDir/");
	}

	private void testGetDirectoryWithExistingDirectoryInShareRoot(String name) {

		ISmbDirectory parent = directory(shareUrlSlash);
		directory(someDirUrlSlash).makeDirectories();
		ISmbDirectory directory = parent.getDirectory(name);
		assertTrue(directory.exists());
	}

	@Test
	public void testGetDirectoryWithNonexistentDirectoryInShareRoot() {

		testGetDirectoryWithNonexistentDirectoryInShareRoot("someDir");
	}

	@Test
	public void testGetDirectoryWithNonexistentDirectoryInShareRootAndTailingSlash() {

		testGetDirectoryWithNonexistentDirectoryInShareRoot("someDir/");
	}

	private void testGetDirectoryWithNonexistentDirectoryInShareRoot(String name) {

		ISmbDirectory parent = directory(shareUrlSlash);
		ISmbDirectory directory = parent.getDirectory(name);
		assertFalse(directory.exists());
	}

	@Test
	public void testGetDirectoryWithAdjacentSlashesInName() {

		ISmbDirectory directory = directory(shareUrlSlash);
		ISmbDirectory subDirectory = directory.getDirectory("someDir//");
		assertEquals(shareUrlSlash.concat("someDir/"), subDirectory.getUrl());
	}

	@Test
	public void testCopyContentIntoWithExistingTargetDirectory() {

		testCopyContentIntoWithExistingTargetDirectory("target");
	}

	@Test
	public void testCopyContentIntoWithExistingTargetDirectoryAndTailingSlash() {

		testCopyContentIntoWithExistingTargetDirectory("target/");
	}

	private void testCopyContentIntoWithExistingTargetDirectory(String name) {

		populateShareWithSimpleStructure();
		ISmbDirectory source = directory(someDirUrlSlash);
		ISmbDirectory target = directory(shareUrlSlash.concat(name));
		writeFile(shareUrlSlash.concat("someDir/subDir/deepFile"), "foo");
		directory(shareUrlSlash.concat("target/subDir/")).makeDirectories();
		writeFile(shareUrlSlash.concat("target/subDir/deepFile"), "bar");

		assertEquals("bar", readFile(shareUrlSlash.concat("target/subDir/deepFile")));
		ISmbDirectory copiedDirectory = source.copyContentInto(target);
		assertEquals("foo", readFile(shareUrlSlash.concat("target/subDir/deepFile")));

		assertTrue(source.exists());
		assertTrue(copiedDirectory.exists());
		assertEquals(target.getUrl(), copiedDirectory.getUrl());
		assertTrue(directory(shareUrlSlash.concat("target/subDir/")).exists());
		assertTrue(directory(shareUrlSlash.concat("target/emptySubDir/")).exists());
		assertTrue(file(shareUrlSlash.concat("target/otherFile")).exists());
		assertTrue(file(shareUrlSlash.concat("target/someFile")).exists());
		assertTrue(file(shareUrlSlash.concat("target/subDir/deepFile")).exists());
	}

	@Test
	public void testCopyContentIntoWithNonexistentTargetDirectory() {

		testCopyContentIntoWithNonexistentTargetDirectory("target");
	}

	@Test
	public void testCopyContentIntoWithNonexistentTargetDirectoryAndTailingSlash() {

		testCopyContentIntoWithNonexistentTargetDirectory("target/");
	}

	private void testCopyContentIntoWithNonexistentTargetDirectory(String name) {

		populateShareWithSimpleStructure();
		ISmbDirectory source = directory(someDirUrlSlash);
		ISmbDirectory target = directory(shareUrlSlash.concat(name));

		ISmbDirectory copiedDirectory = source.copyContentInto(target);

		assertTrue(source.exists());
		assertTrue(copiedDirectory.exists());
		assertEquals(target.getUrl(), copiedDirectory.getUrl());
		assertTrue(directory(shareUrlSlash.concat("target/subDir/")).exists());
		assertTrue(directory(shareUrlSlash.concat("target/emptySubDir/")).exists());
		assertTrue(file(shareUrlSlash.concat("target/otherFile")).exists());
		assertTrue(file(shareUrlSlash.concat("target/someFile")).exists());
		assertTrue(file(shareUrlSlash.concat("target/subDir/deepFile")).exists());
	}

	@Test
	public void testCopyContentIntoWithNonexistentSourceDirectory() {

		testCopyContentIntoWithNonexistentSourceDirectory(someEntryUrl);
	}

	@Test
	public void testCopyContentIntoWithNonexistentSourceDirectoryAndTailingSlash() {

		testCopyContentIntoWithNonexistentSourceDirectory(someEntryUrlSlash);
	}

	private void testCopyContentIntoWithNonexistentSourceDirectory(String url) {

		ISmbDirectory source = directory(someDirUrlSlash);
		ISmbDirectory target = directory(url);
		target.makeDirectories();
		assertException(SmbIOException.class, () -> source.copyContentInto(target));
		assertFalse(source.exists());
	}

	@Test
	public void testMoveIntoWithExistingTargetDirectory() {

		testMoveIntoWithExistingTargetDirectory("target");
	}

	@Test
	public void testMoveIntoWithExistingTargetDirectoryAndTailingSlash() {

		testMoveIntoWithExistingTargetDirectory("target/");
	}

	private void testMoveIntoWithExistingTargetDirectory(String name) {

		populateShareWithSimpleStructure();
		ISmbDirectory source = directory(someDirUrlSlash);
		ISmbDirectory target = directory(shareUrlSlash.concat(name));
		target.makeDirectories();

		ISmbDirectory copiedDirectory = source.moveInto(target);

		assertFalse(source.exists());
		assertTrue(copiedDirectory.exists());
		assertEquals(shareUrlSlash.concat("target/someDir/"), copiedDirectory.getUrl());
		assertTrue(directory(shareUrlSlash.concat("target/someDir/subDir/")).exists());
		assertTrue(directory(shareUrlSlash.concat("target/someDir/emptySubDir/")).exists());
		assertTrue(file(shareUrlSlash.concat("target/someDir/otherFile")).exists());
		assertTrue(file(shareUrlSlash.concat("target/someDir/someFile")).exists());
		assertTrue(file(shareUrlSlash.concat("target/someDir/subDir/deepFile")).exists());
	}

	@Test
	public void testMoveIntoWithExistingTargetDirectoryAndNameClash() {

		testMoveIntoWithExistingTargetDirectoryAndNameClash("target");
	}

	@Test
	public void testMoveIntoWithExistingTargetDirectoryAndNameClashAndTailingSlash() {

		testMoveIntoWithExistingTargetDirectoryAndNameClash("target/");
	}

	private void testMoveIntoWithExistingTargetDirectoryAndNameClash(String name) {

		populateShareWithSimpleStructure();
		ISmbDirectory source = directory(someDirUrlSlash);
		ISmbDirectory target = directory(shareUrlSlash.concat(name));
		target.getDirectory("someDir").makeDirectories();

		assertException(SmbIOException.class, () -> source.moveInto(target));
		assertTrue(source.exists());
	}

	@Test
	public void testMoveIntoWithNonexistentTargetDirectory() {

		testMoveIntoWithNonexistentTargetDirectory("target");
	}

	@Test
	public void testMoveIntoWithNonexistentTargetDirectoryAndTailingSlash() {

		testMoveIntoWithNonexistentTargetDirectory("target/");
	}

	private void testMoveIntoWithNonexistentTargetDirectory(String name) {

		populateShareWithSimpleStructure();
		ISmbDirectory source = directory(someDirUrlSlash);
		ISmbDirectory target = directory(shareUrlSlash.concat(name));

		assertException(SmbIOException.class, () -> source.moveInto(target));
		assertTrue(source.exists());
	}

	@Test
	public void testMoveIntoWithNonexistentSourceDirectory() {

		testMoveIntoWithNonexistentSourceDirectory("target");
	}

	@Test
	public void testMoveIntoWithNonexistentSourceDirectoryAndTailingSlash() {

		testMoveIntoWithNonexistentSourceDirectory("target/");
	}

	private void testMoveIntoWithNonexistentSourceDirectory(String name) {

		ISmbDirectory source = directory(someDirUrlSlash);
		ISmbDirectory target = directory(shareUrlSlash.concat(name));
		target.makeDirectories();

		assertException(SmbIOException.class, () -> source.moveInto(target));
		assertFalse(source.exists());
	}

	@Test
	public void testRenameToWithExistingTargetInSameDirectory() {

		testRenameToWithExistingTargetInSameDirectory("someEntry");
	}

	@Test
	public void testRenameToWithExistingTargetInSameDirectoryAndTailingSlash() {

		testRenameToWithExistingTargetInSameDirectory("someEntry/");
	}

	private void testRenameToWithExistingTargetInSameDirectory(String name) {

		ISmbDirectory source = directory(someDirUrlSlash);
		source.makeDirectories();
		ISmbDirectory target = directory(someEntryUrlSlash);
		target.makeDirectories();

		assertException(SmbIOException.class, () -> source.renameTo(name));
		assertTrue(source.exists());
	}

	@Test
	public void testRenameToWithExistingTargetInOtherDirectory() {

		testRenameToWithExistingTargetInOtherDirectory("someEntry/sub");
	}

	@Test
	public void testRenameToWithExistingTargetInOtherDirectoryAndTailingSlash() {

		testRenameToWithExistingTargetInOtherDirectory("someEntry/sub/");
	}

	private void testRenameToWithExistingTargetInOtherDirectory(String name) {

		ISmbDirectory source = directory(someDirUrlSlash);
		source.makeDirectories();
		ISmbDirectory target = directory(someEntryUrlSlash.concat("sub/"));
		target.makeDirectories();

		assertException(SmbIOException.class, () -> source.renameTo(name));
		assertTrue(source.exists());
	}

	@Test
	public void testRenameToWithNonexistentTargetInSameDirectory() {

		testRenameToWithNonexistentTargetInSameDirectory("someEntry");
	}

	@Test
	public void testRenameToWithNonexistentTargetInSameDirectoryAndTailingSlash() {

		testRenameToWithNonexistentTargetInSameDirectory("someEntry/");
	}

	private void testRenameToWithNonexistentTargetInSameDirectory(String name) {

		populateShareWithSimpleStructure();
		ISmbDirectory source = directory(someDirUrlSlash);
		ISmbDirectory target = directory(someEntryUrlSlash);

		ISmbDirectory renamedDirectory = source.renameTo(name);

		assertFalse(source.exists());
		assertTrue(renamedDirectory.exists());
		assertEquals(target.getUrl(), renamedDirectory.getUrl());
		assertTrue(directory(shareUrlSlash.concat("someEntry/subDir/")).exists());
		assertTrue(directory(shareUrlSlash.concat("someEntry/emptySubDir/")).exists());
		assertTrue(file(shareUrlSlash.concat("someEntry/otherFile")).exists());
		assertTrue(file(shareUrlSlash.concat("someEntry/someFile")).exists());
		assertTrue(file(shareUrlSlash.concat("someEntry/subDir/deepFile")).exists());
	}

	@Test
	public void testRenameToWithNonexistentTargetInOtherExistingDirectory() {

		testRenameToWithNonexistentTargetInOtherExistingDirectory("someEntry/sub");
	}

	@Test
	public void testRenameToWithNonexistentTargetInOtherExistingDirectoryAndTailingSlash() {

		testRenameToWithNonexistentTargetInOtherExistingDirectory("someEntry/sub/");
	}

	private void testRenameToWithNonexistentTargetInOtherExistingDirectory(String name) {

		populateShareWithSimpleStructure();
		ISmbDirectory source = directory(someDirUrlSlash);
		ISmbDirectory target = directory(someEntryUrlSlash);
		target.makeDirectories();

		ISmbDirectory renamedDirectory = source.renameTo(name);

		assertFalse(source.exists());
		assertTrue(renamedDirectory.exists());
		assertEquals(someEntryUrlSlash.concat("sub/"), renamedDirectory.getUrl());
		assertTrue(directory(shareUrlSlash.concat("someEntry/sub/subDir/")).exists());
		assertTrue(directory(shareUrlSlash.concat("someEntry/sub/emptySubDir/")).exists());
		assertTrue(file(shareUrlSlash.concat("someEntry/sub/otherFile")).exists());
		assertTrue(file(shareUrlSlash.concat("someEntry/sub/someFile")).exists());
		assertTrue(file(shareUrlSlash.concat("someEntry/sub/subDir/deepFile")).exists());
	}

	@Test
	public void testRenameToWithNonexistentTargetInOtherNonexistentDirectory() {

		testRenameToWithNonexistentTargetInOtherNonexistentDirectory("someEntry/sub");
	}

	@Test
	public void testRenameToWithNonexistentTargetInOtherNonexistentDirectoryAndTailingSlash() {

		testRenameToWithNonexistentTargetInOtherNonexistentDirectory("someEntry/sub/");
	}

	private void testRenameToWithNonexistentTargetInOtherNonexistentDirectory(String name) {

		populateShareWithSimpleStructure();
		ISmbDirectory source = directory(someDirUrlSlash);

		assertException(SmbIOException.class, () -> source.renameTo(name));
		assertTrue(source.exists());
	}

	@Test
	public void testRenameToWithNonexistentSource() {

		testRenameToWithNonexistentSource("target");
	}

	@Test
	public void testRenameToWithNonexistentSourceAndTailingSlash() {

		testRenameToWithNonexistentSource("target/");
	}

	private void testRenameToWithNonexistentSource(String name) {

		ISmbDirectory source = directory(someDirUrlSlash);
		ISmbDirectory target = directory(shareUrlSlash.concat("target/"));
		target.makeDirectories();

		assertException(SmbIOException.class, () -> source.renameTo(name));
		assertFalse(source.exists());
	}

	@Test
	public void testRenameToWithAdjacentSlashesInName() {

		ISmbDirectory source = directory(someDirUrlSlash);
		source.makeDirectories();
		assertException(//
			IllegalArgumentException.class,
			"The URL must not contain adjacent slashes after the protocol prefix.",
			() -> source.renameTo("target//"));
		assertTrue(source.exists());
	}
}
