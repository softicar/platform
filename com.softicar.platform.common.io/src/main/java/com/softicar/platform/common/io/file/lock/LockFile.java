package com.softicar.platform.common.io.file.lock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Facilitates handling of a lock file.
 * <p>
 * Provides operations to create, delete, and observe a lock file.
 *
 * @author Alexander Schmidt
 */
public class LockFile {

	private final File file;

	/**
	 * Constructs a new {@link LockFile} for the given {@link File}.
	 *
	 * @param file
	 *            the {@link File} which represents the lock (never <i>null</i>)
	 */
	public LockFile(File file) {

		this.file = requireValidFile(file);
	}

	/**
	 * Constructs a new {@link LockFile} with the given name, in the directory
	 * identified by the given {@link File}.
	 *
	 * @param directory
	 *            the directory in which the lock file shall be created (never
	 *            <i>null</i>)
	 * @param name
	 *            the non-empty name of the lock file to be created (never
	 *            <i>null</i>)
	 */
	public LockFile(File directory, String name) {

		this(new File(requireValidDirectory(directory), name));
	}

	/**
	 * Constructs a new {@link LockFile} with the given name, in the directory
	 * identified by the given {@link Path}.
	 *
	 * @param path
	 *            the path in which the lock file shall be created (never
	 *            <i>null</i>)
	 * @param name
	 *            the non-empty name of the lock file to be created (never
	 *            <i>null</i>)
	 */
	public LockFile(Path path, String name) {

		this(requireValidPath(path).resolve(requireValidName(name)).toFile());
	}

	/**
	 * Determines whether the lock is currently held, by determining whether the
	 * lock file exists in the file system.
	 *
	 * @return <i>true</i> if the lock file exists; <i>false</i> otherwise
	 */
	public boolean isLocked() {

		return file.exists();
	}

	/**
	 * Acquires the lock by creating the lock file in the file system.
	 * <p>
	 * Creates parent directories as well, if necessary.
	 * <p>
	 * If the lock file was created as a result of this method call, <i>true</i>
	 * is returned. If the file already existed, <i>false</i> is returned.
	 *
	 * @return <i>true</i> if the lock file was created; <i>false</i> otherwise
	 */
	public boolean lock() {

		try {
			file.getParentFile().mkdirs();
			return file.createNewFile();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Releases the lock by deleting the lock file from the file system.
	 * <p>
	 * Does <b>not</b> delete the parent directory (even if it was created in a
	 * previous {@link #lock()} call).
	 * <p>
	 * If the lock file was deleted as a result of this method call, <i>true</i>
	 * is returned. Otherwise, <i>false</i> is returned.
	 *
	 * @return <i>true</i> if the lock file was deleted; <i>false</i> otherwise
	 */
	public boolean unlock() {

		return file.delete();
	}

	/**
	 * Returns a reference to the {@link File} which serves as a lock.
	 *
	 * @return the lock {@link File} (never <i>null</i>)
	 */
	public File getFile() {

		return file;
	}

	private static File requireValidFile(File file) {

		return Objects.requireNonNull(file);
	}

	private static File requireValidDirectory(File directory) {

		Objects.requireNonNull(directory);
		if (directory.exists() && !directory.isDirectory()) {
			throw new IllegalArgumentException();
		}
		return directory;
	}

	private static Path requireValidPath(Path path) {

		return Objects.requireNonNull(path);
	}

	private static String requireValidName(String name) {

		Objects.requireNonNull(name);
		if (name.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return name;
	}
}
