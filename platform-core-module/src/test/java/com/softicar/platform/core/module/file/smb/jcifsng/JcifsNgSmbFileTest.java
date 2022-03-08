package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbIOException;
import com.softicar.platform.core.module.file.smb.SmbNoFileException;
import java.io.IOException;
import org.junit.Test;

public class JcifsNgSmbFileTest extends AbtractJcifsNgSmbTest {

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
		DevNull.swallow(new JcifsNgSmbFile(url, createContext()));
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
		assertException(SmbNoFileException.class, () -> new JcifsNgSmbFile(url, createContext()));
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

		DevNull.swallow(new JcifsNgSmbFile(url, createContext()));
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

		assertException(SmbNoFileException.class, () -> new JcifsNgSmbFile(url, createContext()));
	}

	@Test
	public void testConstructorWithUrlNull() {

		assertException(NullPointerException.class, () -> new JcifsNgSmbFile(null, createContext()));
	}

	@Test
	public void testConstructorWithContextNull() {

		assertException(NullPointerException.class, () -> new JcifsNgSmbFile(someEntryUrl, null));
	}

	@Test
	public void testConstructorWithUrlWithoutProtocol() {

		assertException(//
			IllegalArgumentException.class,
			() -> new JcifsNgSmbFile("garbage-url", createContext()),
			"The URL must start with 'smb://'.");
	}

	@Test
	public void testConstructorWithUrlWithWrongProtocol() {

		assertException(//
			IllegalArgumentException.class,
			() -> new JcifsNgSmbFile("http://garbage-url", createContext()),
			"The URL must start with 'smb://'.");
	}

	@Test
	public void testConstructorWithUrlWithAdjacentSlashes() {

		assertException(//
			IllegalArgumentException.class,
			() -> new JcifsNgSmbFile("smb://somewhere//something", createContext()),
			"The URL must not contain adjacent slashes after the protocol prefix.");
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
		assertEquals(someEntryUrl, file(url).getUrl());
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

		assertEquals(someEntryUrl, file(url).getUrl());
	}

	@Test
	public void testGetSizeWithExistingEmptyFile() {

		testGetSizeWithExistingEmptyFile(someEntryUrl);
	}

	@Test
	public void testGetSizeWithExistingEmptyFileAndTailingSlash() {

		testGetSizeWithExistingEmptyFile(someEntryUrlSlash);
	}

	private void testGetSizeWithExistingEmptyFile(String url) {

		file(someEntryUrl).touch();
		assertEquals(0, file(url).getSize());
	}

	@Test
	public void testGetSizeWithExistingNonEmptyFile() {

		testGetSizeWithExistingNonEmptyFile(someEntryUrl);
	}

	@Test
	public void testGetSizeWithExistingNonEmptyFileAndTailingSlash() {

		testGetSizeWithExistingNonEmptyFile(someEntryUrlSlash);
	}

	private void testGetSizeWithExistingNonEmptyFile(String url) {

		writeFile(someEntryUrl, "foo");
		assertEquals("foo".length(), file(url).getSize());
	}

	@Test
	public void testGetSizeWithNonexistentEntry() {

		testGetSizeWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testGetSizeWithNonexistentEntryAndTailingSlash() {

		testGetSizeWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testGetSizeWithNonexistentEntry(String url) {

		ISmbFile file = file(url);
		assertException(SmbIOException.class, file::getSize);
	}

	@Test
	public void testCopyToFileWithExistingTargetInSameDirectory() {

		ISmbFile source = writeFile(someFileUrl, "foo");
		ISmbFile target = writeFile(someEntryUrl, "bar");

		ISmbFile copiedFile = source.copyTo(target);

		assertTrue(source.exists());
		assertEquals(target.getUrl(), copiedFile.getUrl());
		assertEquals(someEntryUrl, copiedFile.getUrl());
		assertEquals("foo", readFile(copiedFile));
	}

	@Test
	public void testCopyToFileWithExistingTargetInOtherDirectory() {

		directory(someDirUrlSlash).makeDirectories();
		ISmbFile source = writeFile(someFileUrl, "foo");
		ISmbFile target = writeFile(someDirUrlSlash.concat("target"), "bar");

		ISmbFile copiedFile = source.copyTo(target);

		assertTrue(source.exists());
		assertEquals(target.getUrl(), copiedFile.getUrl());
		assertEquals(someDirUrlSlash.concat("target"), copiedFile.getUrl());
		assertEquals("foo", readFile(copiedFile));
	}

	@Test
	public void testCopyToFileWithNonexistentTargetInSameDirectory() {

		ISmbFile source = writeFile(someFileUrl, "foo");
		ISmbFile target = file(someEntryUrl);

		ISmbFile copiedFile = source.copyTo(target);

		assertTrue(source.exists());
		assertEquals(target.getUrl(), copiedFile.getUrl());
		assertEquals(someEntryUrl, copiedFile.getUrl());
		assertEquals("foo", readFile(copiedFile));
	}

	@Test
	public void testCopyToFileWithNonexistentTargetInOtherDirectory() {

		directory(someDirUrlSlash).makeDirectories();
		ISmbFile source = writeFile(someFileUrl, "foo");
		ISmbFile target = file(someDirUrlSlash.concat("target"));

		ISmbFile copiedFile = source.copyTo(target);

		assertTrue(source.exists());
		assertEquals(target.getUrl(), copiedFile.getUrl());
		assertEquals(someDirUrlSlash.concat("target"), copiedFile.getUrl());
		assertEquals("foo", readFile(copiedFile));
	}

	@Test
	public void testCopyToFileWithNonexistentSource() {

		ISmbFile target = file(someEntryUrl);
		ISmbFile source = file(someFileUrl);
		assertException(SmbIOException.class, () -> source.copyTo(target));
		assertFalse(source.exists());
	}

	@Test
	public void testCopyToDirectoryWithExistingTarget() {

		directory(someDirUrlSlash).makeDirectories();
		ISmbFile source = writeFile(someFileUrl, "foo");
		ISmbDirectory target = directory(someDirUrlSlash);

		ISmbFile copiedFile = source.copyTo(target);

		assertTrue(source.exists());
		assertEquals(someDirUrlSlash.concat("someFile"), copiedFile.getUrl());
		assertEquals("foo", readFile(copiedFile));
	}

	@Test
	public void testCopyToDirectoryWithNonexistentTarget() {

		ISmbFile source = writeFile(someFileUrl, "foo");
		ISmbDirectory target = directory(someDirUrlSlash);
		assertException(SmbIOException.class, () -> source.copyTo(target));
		assertTrue(source.exists());
	}

	@Test
	public void testCopyToDirectoryWithNonexistentSource() {

		ISmbDirectory target = directory(someDirUrlSlash);
		ISmbFile source = file(someFileUrl);
		assertException(SmbIOException.class, () -> source.copyTo(target));
		assertFalse(source.exists());
	}

	@Test
	public void testMoveToFileWithExistingTargetInSameDirectory() {

		ISmbFile source = writeFile(someFileUrl, "foo");
		ISmbFile target = writeFile(someEntryUrl, "bar");
		assertException(SmbIOException.class, () -> source.moveTo(target));
		assertTrue(source.exists());
	}

	@Test
	public void testMoveToFileWithExistingTargetInOtherDirectory() {

		directory(someDirUrlSlash).makeDirectories();
		ISmbFile source = writeFile(someFileUrl, "foo");
		ISmbFile target = writeFile(someDirUrlSlash.concat("target"), "bar");
		assertException(SmbIOException.class, () -> source.moveTo(target));
		assertTrue(source.exists());
	}

	@Test
	public void testMoveToFileWithNonexistentTargetInSameDirectory() {

		ISmbFile source = writeFile(someFileUrl, "foo");
		ISmbFile target = file(someEntryUrl);

		ISmbFile copiedFile = source.moveTo(target);

		assertFalse(source.exists());
		assertEquals(target.getUrl(), copiedFile.getUrl());
		assertEquals(someEntryUrl, copiedFile.getUrl());
		assertEquals("foo", readFile(copiedFile));
	}

	@Test
	public void testMoveToFileWithNonexistentTargetInOtherDirectory() {

		directory(someDirUrlSlash).makeDirectories();
		ISmbFile source = writeFile(someFileUrl, "foo");
		ISmbFile target = file(someDirUrlSlash.concat("target"));

		ISmbFile copiedFile = source.moveTo(target);

		assertFalse(source.exists());
		assertEquals(target.getUrl(), copiedFile.getUrl());
		assertEquals(someDirUrlSlash.concat("target"), copiedFile.getUrl());
		assertEquals("foo", readFile(copiedFile));
	}

	@Test
	public void testMoveToFileWithNonexistentSource() {

		ISmbFile target = file(someEntryUrl);
		ISmbFile source = file(someFileUrl);
		assertException(SmbIOException.class, () -> source.moveTo(target));
		assertFalse(source.exists());
	}

	@Test
	public void testMoveToDirectoryWithExistingTarget() {

		testMoveToDirectoryWithExistingTarget(someDirUrl);
	}

	@Test
	public void testMoveToDirectoryWithExistingTargetAndTailingSlash() {

		testMoveToDirectoryWithExistingTarget(someDirUrlSlash);
	}

	private void testMoveToDirectoryWithExistingTarget(String url) {

		directory(someDirUrlSlash).makeDirectories();
		ISmbFile source = writeFile(someFileUrl, "foo");
		ISmbDirectory target = directory(url);

		ISmbFile movedFile = source.moveTo(target);
		assertFalse(source.exists());
		assertEquals(target.getUrl(), movedFile.getParentDirectory().getUrl());
		assertEquals(someDirUrlSlash.concat("someFile"), movedFile.getUrl());
		assertEquals("foo", readFile(movedFile));
	}

	@Test
	public void testMoveToDirectoryWithNonExistentTarget() {

		ISmbFile source = writeFile(someFileUrl, "foo");
		ISmbDirectory target = directory(someDirUrlSlash);
		assertException(SmbIOException.class, () -> source.moveTo(target));
	}

	@Test
	public void testMoveToDirectoryWithNonExistentSource() {

		directory(someDirUrlSlash).makeDirectories();
		ISmbFile source = file(someFileUrl);
		ISmbDirectory target = directory(someDirUrlSlash);
		assertException(SmbIOException.class, () -> source.moveTo(target));
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

		ISmbFile source = writeFile(someFileUrl, "foo");
		writeFile(someEntryUrl, "bar");
		assertException(SmbIOException.class, () -> source.renameTo(name));
		assertTrue(source.exists());
	}

	@Test
	public void testRenameToWithExistingTargetInOtherDirectory() {

		testRenameToWithExistingTargetInOtherDirectory("someDir/someEntry");
	}

	@Test
	public void testRenameToWithExistingTargetInOtherDirectoryAndTailingSlash() {

		testRenameToWithExistingTargetInOtherDirectory("someDir/someEntry/");
	}

	private void testRenameToWithExistingTargetInOtherDirectory(String name) {

		ISmbFile source = writeFile(someFileUrl, "foo");
		directory(someDirUrlSlash).makeDirectories();
		writeFile(someDirUrlSlash.concat("someEntry"), "foo");
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

		ISmbFile source = writeFile(someFileUrl, "foo");
		ISmbFile renamedFile = source.renameTo(name);
		assertFalse(source.exists());
		assertEquals(shareUrlSlash.concat("someEntry"), renamedFile.getUrl());
		assertEquals("foo", readFile(renamedFile));
	}

	@Test
	public void testRenameToWithNonexistentTargetInOtherExistingDirectory() {

		testRenameToWithNonexistentTargetInOtherExistingDirectory("someDir/someEntry");
	}

	@Test
	public void testRenameToWithNonexistentTargetInOtherExistingDirectoryAndTailingSlash() {

		testRenameToWithNonexistentTargetInOtherExistingDirectory("someDir/someEntry/");
	}

	private void testRenameToWithNonexistentTargetInOtherExistingDirectory(String name) {

		ISmbFile source = writeFile(someFileUrl, "foo");
		directory(someDirUrlSlash).makeDirectories();
		ISmbFile renamedFile = source.renameTo(name);
		assertFalse(source.exists());
		assertEquals(someDirUrlSlash.concat("someEntry"), renamedFile.getUrl());
		assertEquals("foo", readFile(renamedFile));
	}

	@Test
	public void testRenameToWithNonexistentTargetInOtherNonexistentDirectory() {

		testRenameToWithNonexistentTargetInOtherNonexistentDirectory("someDir/someEntry");
	}

	@Test
	public void testRenameToWithNonexistentTargetInOtherNonexistentDirectoryAndTailingSlash() {

		testRenameToWithNonexistentTargetInOtherNonexistentDirectory("someDir/someEntry/");
	}

	private void testRenameToWithNonexistentTargetInOtherNonexistentDirectory(String name) {

		ISmbFile source = writeFile(someFileUrl, "foo");
		assertException(SmbIOException.class, () -> source.renameTo(name));
		assertTrue(source.exists());
	}

	@Test
	public void testRenameToWithNonexistentSource() {

		testRenameToWithNonexistentSource("someEntry");
	}

	@Test
	public void testRenameToWithNonexistentSourceAndTailingSlash() {

		testRenameToWithNonexistentSource("someEntry/");
	}

	private void testRenameToWithNonexistentSource(String name) {

		ISmbFile source = file(someFileUrl);
		assertException(SmbIOException.class, () -> source.renameTo(name));
		assertFalse(source.exists());
	}

	@Test
	public void testRenameToWithAdjacentSlashesInName() {

		ISmbFile source = writeFile(someFileUrl, "foo");
		assertException(//
			IllegalArgumentException.class,
			() -> source.renameTo("someEntry//"),
			"The URL must not contain adjacent slashes after the protocol prefix.");
		assertTrue(source.exists());
	}

	@Test
	public void testTouchWithExistingFile() {

		testTouchWithExistingFile(someEntryUrl);
	}

	@Test
	public void testTouchWithExistingFileAndTailingSlash() {

		testTouchWithExistingFile(someEntryUrlSlash);
	}

	private void testTouchWithExistingFile(String url) {

		assertFalse(file(someEntryUrl).exists());
		file(someEntryUrl).touch();

		file(url).touch();
		assertTrue(file(someEntryUrl).exists());
	}

	@Test
	public void testTouchWithNonexistentEntry() {

		testTouchWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testTouchWithNonexistentEntryAndTailingSlash() {

		testTouchWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testTouchWithNonexistentEntry(String url) {

		assertFalse(file(someEntryUrl).exists());

		file(url).touch();
		assertTrue(file(someEntryUrl).exists());
	}

	@Test
	public void testCreateInputStreamWithExistingFile() {

		testCreateInputStreamWithExistingFile(someEntryUrl);
	}

	@Test
	public void testCreateInputStreamWithExistingFileAndTailingSlash() {

		testCreateInputStreamWithExistingFile(someEntryUrlSlash);
	}

	private void testCreateInputStreamWithExistingFile(String url) {

		writeFile(someEntryUrl, "foo");
		try (var inputStream = file(url).createInputStream()) {
			assertEquals("foo", StreamUtils.toString(inputStream, Charsets.UTF8));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Test
	public void testCreateInputStreamWithNonexistentEntry() {

		testCreateInputStreamWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testCreateInputStreamWithNonexistentEntryAndTailingSlash() {

		testCreateInputStreamWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testCreateInputStreamWithNonexistentEntry(String url) {

		assertException(SmbIOException.class, () -> {
			try (var inputStream = file(url).createInputStream()) {
				// nothing to do
			} catch (IOException exception) {
				throw new RuntimeException(exception);
			}
		});
	}

	@Test
	public void testCreateOutputStreamWithExistingEmptyFile() {

		testCreateOutputStreamWithExistingEmptyFile(someEntryUrl);
	}

	@Test
	public void testCreateOutputStreamWithExistingEmptyFileAndTailingSlash() {

		testCreateOutputStreamWithExistingEmptyFile(someEntryUrlSlash);
	}

	private void testCreateOutputStreamWithExistingEmptyFile(String url) {

		file(someEntryUrl).touch();
		try (var outputStream = file(url).createOutputStream()) {
			outputStream.write("foo".getBytes(Charsets.UTF8));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		assertEquals("foo", readFile(someEntryUrl));
	}

	@Test
	public void testCreateOutputStreamWithExistingNonEmptyFile() {

		testCreateOutputStreamWithExistingNonEmptyFile(someEntryUrl);
	}

	@Test
	public void testCreateOutputStreamWithExistingNonEmptyFileAndTailingSlash() {

		testCreateOutputStreamWithExistingNonEmptyFile(someEntryUrlSlash);
	}

	private void testCreateOutputStreamWithExistingNonEmptyFile(String url) {

		writeFile(someEntryUrl, "foo");
		try (var outputStream = file(url).createOutputStream()) {
			outputStream.write("bar".getBytes(Charsets.UTF8));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		assertEquals("bar", readFile(someEntryUrl));
	}

	@Test
	public void testCreateOutputStreamWithNonexistentEntry() {

		testCreateOutputStreamWithNonexistentEntry(someEntryUrl);
	}

	@Test
	public void testCreateOutputStreamWithNonexistentEntryAndTailingSlash() {

		testCreateOutputStreamWithNonexistentEntry(someEntryUrlSlash);
	}

	private void testCreateOutputStreamWithNonexistentEntry(String url) {

		try (var outputStream = file(url).createOutputStream()) {
			outputStream.write("foo".getBytes(Charsets.UTF8));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		assertEquals("foo", readFile(someEntryUrl));
	}
}
