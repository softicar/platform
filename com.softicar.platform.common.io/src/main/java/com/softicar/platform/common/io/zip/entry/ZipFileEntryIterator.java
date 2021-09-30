package com.softicar.platform.common.io.zip.entry;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileEntryIterator implements Iterator<ZipFileEntry> {

	private final ZipFile zipFile;
	private final Enumeration<? extends ZipEntry> entries;
	private boolean onlyFiles;
	private ZipFileEntry next;

	public ZipFileEntryIterator(File file) {

		this.zipFile = openZipFile(file);
		this.entries = zipFile.entries();
		this.onlyFiles = false;
		this.next = fetchNext();
	}

	public ZipFileEntryIterator setOnlyFiles(boolean onlyFiles) {

		this.onlyFiles = onlyFiles;
		return this;
	}

	@Override
	public boolean hasNext() {

		if (next == null) {
			closeZipFile();
		}

		return next != null;
	}

	@Override
	public ZipFileEntry next() {

		ZipFileEntry entry = next;
		this.next = fetchNext();
		return entry;
	}

	private ZipFileEntry fetchNext() {

		ZipFileEntry current = null;
		while (entries.hasMoreElements()) {
			current = new ZipFileEntry(zipFile, entries.nextElement());
			if (isAccepted(current)) {
				return current;
			}
		}
		return null;
	}

	private boolean isAccepted(ZipFileEntry entry) {

		return !(onlyFiles && entry.isDirectory());
	}

	private static ZipFile openZipFile(File file) {

		try {
			return new ZipFile(file);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private void closeZipFile() {

		try {
			zipFile.close();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
