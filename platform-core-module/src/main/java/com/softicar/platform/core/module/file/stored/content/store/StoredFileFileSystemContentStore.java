package com.softicar.platform.core.module.file.stored.content.store;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.Trim;
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
	 * Constructs a new {@link StoredFileFileSystemContentStore} for the given
	 * directory.
	 *
	 * @param absolutePath
	 *            the absolute path to an existing, writable directory (never
	 *            <i>null</i>)
	 */
	public StoredFileFileSystemContentStore(String absolutePath) {

		if (!absolutePath.startsWith("/")) {
			throw new IllegalArgumentException("Expected an absolute path but encountered: '%s'".formatted(absolutePath));
		}

		this.directory = new File(absolutePath);
	}

	@Override
	public String getLocation() {

		return directory.getPath();
	}

	@Override
	public boolean isAvailable() {

		return directory != null && directory.isDirectory() && directory.canWrite();
	}

	@Override
	public long getFreeDiskSpace() {

		return directory.getFreeSpace();
	}

	@Override
	public void createDirectories(String directoryPath) {

		resolveFile(directoryPath).mkdirs();
		if (!exists(directoryPath)) {
			throw new SofticarException("Failed to create directory '%s' in the content store.", directoryPath);
		}
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

		name = Trim.trimLeft(name, '/');
		return directory.toPath().resolve(name);
	}

	private File resolveFile(String name) {

		return resolvePath(name).toFile();
	}
}
