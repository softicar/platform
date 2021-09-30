package com.softicar.platform.core.module.file.stored.content.store;

import com.softicar.platform.common.date.DayTime;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

public interface IStoredFileContentStore {

	boolean isAvailable();

	boolean isEnabled();

	String getUrl();

	long getFreeDiskSpace();

	void createFolderIfDoesNotExist(String folderName);

	OutputStream createFile(String fileName);

	InputStream readFile(String fileName);

	void moveFile(String sourceName, String targetName);

	void removeFile(String fileName);

	boolean exists(String name);

	long getFileSize(String fileName);

	Collection<String> getAllFiles();

	Collection<String> getAllFiles(String root);

	DayTime getLastModified(String filename);
}
