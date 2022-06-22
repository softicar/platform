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
	public boolean isAvailable() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

	@Override
	public String getUrl() {

		throw new UnsupportedOperationException();
	}

	@Override
	public long getFreeDiskSpace() {

		return Long.MAX_VALUE;
	}

	@Override
	public void createFolderIfDoesNotExist(String folderName) {

		folders.add(folderName);
	}

	@Override
	public OutputStream createFile(String fileName) {

		var stream = new ByteArrayOutputStream();
		files.put(fileName, stream);
		return stream;
	}

	@Override
	public InputStream readFile(String fileName) {

		return new ByteArrayInputStream(files.getOrDefault(fileName, new ByteArrayOutputStream()).toByteArray());
	}

	@Override
	public void moveFile(String sourceName, String targetName) {

		files.put(targetName, files.remove(sourceName));
	}

	@Override
	public void removeFile(String fileName) {

		files.remove(fileName);
	}

	@Override
	public boolean exists(String name) {

		return files.containsKey(name) || folders.contains(name);
	}

	@Override
	public long getFileSize(String filename) {

		return Optional//
			.ofNullable(files.get(filename))
			.map(ByteArrayOutputStream::size)
			.orElse(0);
	}

	@Override
	public Collection<String> getAllFiles() {

		return files.keySet();
	}

	@Override
	public Collection<String> getAllFiles(String root) {

		return getAllFiles()//
			.stream()
			.filter(name -> name.startsWith(root + "/"))
			.collect(Collectors.toList());
	}

	@Override
	public DayTime getLastModified(String filename) {

		throw new UnsupportedOperationException();
	}

	public byte[] getFileContent(String filename) {

		return Optional//
			.ofNullable(files.get(filename))
			.map(ByteArrayOutputStream::toByteArray)
			.orElse(null);
	}
}
