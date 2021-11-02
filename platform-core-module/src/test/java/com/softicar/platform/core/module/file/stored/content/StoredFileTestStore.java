package com.softicar.platform.core.module.file.stored.content;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.common.string.hexadecimal.Hexadecimal;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

class StoredFileTestStore implements IStoredFileContentStore {

	private final Map<String, byte[]> files;

	public StoredFileTestStore() {

		this.files = new TreeMap<>();
	}

	public void addFileContent(byte[] content) {

		byte[] digest = Hash.SHA1.createDigest().digest(content);
		String hash = Hexadecimal.getHexStringUC(digest);
		String filename = '/' + hash.substring(0, 2) + '/' + hash.substring(2);
		files.put(filename, content);
	}

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

		return "test://";
	}

	@Override
	public long getFreeDiskSpace() {

		return Long.MAX_VALUE;
	}

	@Override
	public void createFolderIfDoesNotExist(String folderName) {

		throw new UnsupportedOperationException();
	}

	@Override
	public OutputStream createFile(String fileName) {

		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream readFile(String filename) {

		return new ByteArrayInputStream(files.get(filename));
	}

	@Override
	public void moveFile(String sourceName, String targetName) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void removeFile(String filename) {

		files.remove(filename);
	}

	@Override
	public boolean exists(String filename) {

		return files.containsKey(filename);
	}

	@Override
	public long getFileSize(String fileName) {

		return files.get(fileName).length;
	}

	@Override
	public Collection<String> getAllFiles() {

		return files.keySet();
	}

	@Override
	public Collection<String> getAllFiles(String root) {

		throw new UnsupportedOperationException();
	}

	@Override
	public DayTime getLastModified(String filename) {

		throw new UnsupportedOperationException();
	}
}
