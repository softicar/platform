package com.softicar.platform.common.io.zip.entry;

import java.io.File;
import java.util.Iterator;

public class ZipFileEntryIterable implements Iterable<ZipFileEntry> {

	private final File zipFile;
	private boolean onlyFiles;

	public ZipFileEntryIterable(File zipFile) {

		this.zipFile = zipFile;
		this.onlyFiles = false;
	}

	public ZipFileEntryIterable setOnlyFiles(boolean onlyFiles) {

		this.onlyFiles = onlyFiles;
		return this;
	}

	@Override
	public Iterator<ZipFileEntry> iterator() {

		return new ZipFileEntryIterator(zipFile).setOnlyFiles(onlyFiles);
	}
}
