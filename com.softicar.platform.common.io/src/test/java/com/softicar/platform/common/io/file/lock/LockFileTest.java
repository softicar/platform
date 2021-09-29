package com.softicar.platform.common.io.file.lock;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class LockFileTest extends Assert {

	private final String baseDirectory;
	private final Path basePath;

	public LockFileTest() {

		this.baseDirectory = System.getProperty("java.io.tmpdir");
		this.basePath = Paths.get(baseDirectory);
	}

	@Test
	public void testLock() {

		String fileName = generateRandomName();
		LockFile lockFile = new LockFile(basePath, fileName);

		boolean created = lockFile.lock();

		Path filePath = basePath.resolve(fileName);
		assertTrue(created);
		assertTrue(filePath.toFile().exists());

		// Cleanup: Remove created entries from temporary directory.
		filePath.toFile().delete();
	}

	@Test
	public void testLockAgain() {

		String fileName = generateRandomName();
		LockFile lockFile = new LockFile(basePath, fileName);

		lockFile.lock();
		boolean createdAgain = lockFile.lock();

		Path filePath = basePath.resolve(fileName);
		assertFalse(createdAgain);
		assertTrue(filePath.toFile().exists());

		// Cleanup: Remove created entries from temporary directory.
		filePath.toFile().delete();
	}

	@Test
	public void testLockWithSubDirectory() {

		String fileName = generateRandomName() + ".lock";
		String subDirectoryName = generateRandomName();
		LockFile lockFile = new LockFile(Paths.get(baseDirectory, subDirectoryName), fileName);

		boolean created = lockFile.lock();

		Path subDirectoryPath = basePath.resolve(subDirectoryName);
		Path filePath = subDirectoryPath.resolve(fileName);
		assertTrue(created);
		assertTrue(filePath.toFile().exists());

		// Cleanup: Remove created entries from temporary directory.
		filePath.toFile().delete();
		subDirectoryPath.toFile().delete();
	}

	@Test
	public void testUnlock() {

		String fileName = generateRandomName();
		LockFile lockFile = new LockFile(basePath, fileName);
		lockFile.lock();

		boolean deleted = lockFile.unlock();

		Path filePath = basePath.resolve(fileName);
		assertTrue(deleted);
		assertFalse(filePath.toFile().exists());
	}

	@Test
	public void testUnlockAgain() {

		String fileName = generateRandomName();
		LockFile lockFile = new LockFile(basePath, fileName);
		lockFile.lock();

		lockFile.unlock();
		boolean deletedAgain = lockFile.unlock();

		Path filePath = basePath.resolve(fileName);
		assertFalse(deletedAgain);
		assertFalse(filePath.toFile().exists());
	}

	@Test
	public void testUnlockWithSubDirectory() {

		String fileName = generateRandomName() + ".lock";
		String subDirectoryName = generateRandomName();
		LockFile lockFile = new LockFile(Paths.get(baseDirectory, subDirectoryName), fileName);
		lockFile.lock();

		boolean deleted = lockFile.unlock();

		Path subDirectoryPath = basePath.resolve(subDirectoryName);
		Path filePath = subDirectoryPath.resolve(fileName);
		assertTrue(deleted);
		assertFalse(filePath.toFile().exists());
		assertTrue(subDirectoryPath.toFile().exists());

		// Cleanup: Remove created entries from temporary directory.
		subDirectoryPath.toFile().delete();
	}

	@Test
	public void testIsLocked() {

		String fileName = generateRandomName();
		LockFile lockFile = new LockFile(basePath, fileName);

		assertFalse(lockFile.isLocked());
	}

	@Test
	public void testIsLockedAfterCreate() {

		String fileName = generateRandomName();
		LockFile lockFile = new LockFile(basePath, fileName);
		lockFile.lock();

		assertTrue(lockFile.isLocked());

		// Cleanup: Remove created entries from temporary directory.
		basePath.resolve(fileName).toFile().delete();
	}

	@Test
	public void testIsLockedAfterDelete() {

		String fileName = generateRandomName();
		LockFile lockFile = new LockFile(basePath, fileName);
		lockFile.lock();
		lockFile.unlock();

		assertFalse(lockFile.isLocked());
	}

	@SuppressWarnings("unused")
	@Test(expected = NullPointerException.class)
	public void testWithNullFile() {

		LockFile lockFile = new LockFile((File) null, "foo");
	}

	@SuppressWarnings("unused")
	@Test(expected = NullPointerException.class)
	public void testWithNullPath() {

		LockFile lockFile = new LockFile((Path) null, "foo");
	}

	@SuppressWarnings("unused")
	@Test(expected = NullPointerException.class)
	public void testWithNullFileName() {

		LockFile lockFile = new LockFile(basePath, null);
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testWithEmptyFileName() {

		LockFile lockFile = new LockFile(basePath, "");
	}

	private String generateRandomName() {

		return LockFileTest.class.getSimpleName() + "-" + UUID.randomUUID().toString();
	}
}
