package com.softicar.platform.core.module.file.stored.content.store;

import com.softicar.platform.common.date.DayTime;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IStoredFileContentStore} that keeps all data in
 * memory.
 * <p>
 * This class is useful for unit tests.
 *
 * @author Oliver Richers
 */
public class StoredFileInMemoryContentStore implements IStoredFileContentStore {

	private final Set<String> folders = new TreeSet<>();
	private final Map<String, ByteArrayOutputStream> files = new TreeMap<>();

	@Override
	public String getLocation() {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAccessible() {

		return true;
	}

	@Override
	public long getFreeDiskSpace() {

		return Long.MAX_VALUE;
	}

	@Override
	public void createDirectories(String directoryPath) {

		folders.add(directoryPath);
	}

	@Override
	public OutputStream getFileOutputStream(String filePath) {

		var stream = new ByteArrayOutputStream();
		files.put(filePath, stream);
		return stream;
	}

	@Override
	public InputStream getFileInputStream(String filePath) {

		return new ByteArrayInputStream(files.getOrDefault(filePath, new ByteArrayOutputStream()).toByteArray());
	}

	@Override
	public void moveFile(String sourceFilePath, String targetFilePath) {

		files.put(targetFilePath, files.remove(sourceFilePath));
	}

	@Override
	public void deleteFile(String filePath) {

		files.remove(filePath);
	}

	@Override
	public boolean exists(String path) {

		return files.containsKey(path) || folders.contains(path);
	}

	@Override
	public long getFileSize(String filePath) {

		return Optional//
			.ofNullable(files.get(filePath))
			.map(ByteArrayOutputStream::size)
			.orElse(0);
	}

	@Override
	public DayTime getLastModified(String path) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> getAllFilePaths() {

		return files.keySet();
	}

	@Override
	public Collection<String> getAllFilePaths(String directoryPath) {

		return getAllFilePaths()//
			.stream()
			.filter(name -> name.startsWith(directoryPath + "/"))
			.collect(Collectors.toList());
	}

	public byte[] getFileContent(String filename) {

		return Optional//
			.ofNullable(files.get(filename))
			.map(ByteArrayOutputStream::toByteArray)
			.orElse(null);
	}
}
