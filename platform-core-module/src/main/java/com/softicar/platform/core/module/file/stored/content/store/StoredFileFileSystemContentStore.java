package com.softicar.platform.core.module.file.stored.content.store;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.date.DayTime;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An implementation of {@link IStoredFileContentStore} that stores files in the
 * local file system.
 *
 * @author Alexander Schmidt
 */
public class StoredFileFileSystemContentStore implements IStoredFileContentStore {

	private final File directory;

	/**
	 * Constructs a new {@link StoredFileFileSystemContentStore}.
	 *
	 * @param directory
	 *            an existing, writable directory (never <i>null</i>)
	 */
	public StoredFileFileSystemContentStore(File directory) {

		this.directory = Objects.requireNonNull(directory);
	}

	@Override
	public String getLocation() {

		try {
			return directory.getCanonicalPath();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

	@Override
	public boolean isReady() {

		return directory != null && directory.isDirectory() && directory.canWrite();
	}

	@Override
	public long getFreeDiskSpace() {

		return directory.getFreeSpace();
	}

	@Override
	public void createDirectories(String directoryPath) {

		resolveFile(directoryPath).mkdirs();
	}

	@Override
	public OutputStream getFileOutputStream(String filePath) {

		try {
			return new FileOutputStream(resolveFile(filePath));
		} catch (FileNotFoundException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public InputStream getFileInputStream(String filePath) {

		try {
			return new FileInputStream(resolveFile(filePath));
		} catch (FileNotFoundException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public void moveFile(String sourceFilePath, String targetFilePath) {

		try {
			Files.move(resolvePath(sourceFilePath), resolvePath(targetFilePath));
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public void deleteFile(String filePath) {

		try {
			Files.delete(resolvePath(filePath));
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public boolean exists(String path) {

		return resolveFile(path).exists();
	}

	@Override
	public long getFileSize(String filePath) {

		return resolveFile(filePath).length();
	}

	@Override
	public DayTime getLastModified(String path) {

		return DayTime.fromMillis(resolveFile(path).lastModified());
	}

	@Override
	public Collection<String> getAllFilePaths() {

		return getAllFilePaths("");
	}

	@Override
	public Collection<String> getAllFilePaths(String directoryPath) {

		Path path = directory.toPath();
		try (Stream<Path> paths = Files.walk(path.resolve(directoryPath))) {
			return paths//
				.filter(Files::isRegularFile)
				.map(path::relativize)
				.map(Path::toString)
				.collect(Collectors.toList());
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private Path resolvePath(String name) {

		return directory.toPath().resolve(name);
	}

	private File resolveFile(String name) {

		return resolvePath(name).toFile();
	}
}
