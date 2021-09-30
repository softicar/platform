package com.softicar.platform.common.io.zip.entry;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileEntry {

	private final ZipFile zipFile;
	private final ZipEntry entry;

	public ZipFileEntry(ZipFile zipFile, ZipEntry entry) {

		this.zipFile = zipFile;
		this.entry = entry;
	}

	public ZipFile getZipFile() {

		return zipFile;
	}

	public String getZipFilename() {

		return zipFile.getName();
	}

	public ZipEntry getEntry() {

		return entry;
	}

	public boolean isDirectory() {

		return entry.isDirectory();
	}

	public String getName() {

		return entry.getName();
	}

	public InputStream getInputStream() {

		try {
			return zipFile.getInputStream(entry);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
