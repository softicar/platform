package com.softicar.platform.common.core.java.jar;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JarFileEntry {

	private final ZipFile zipFile;
	private final ZipEntry entry;

	public JarFileEntry(ZipFile zipFile, ZipEntry entry) {

		this.zipFile = zipFile;
		this.entry = entry;
	}

	public String getPath() {

		return entry.getName();
	}

	public InputStream getInputStream() {

		try {
			return zipFile.getInputStream(entry);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public boolean isFolder() {

		return entry.isDirectory();
	}
}
