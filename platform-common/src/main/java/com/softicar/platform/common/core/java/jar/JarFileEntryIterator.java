package com.softicar.platform.common.core.java.jar;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JarFileEntryIterator implements Iterator<JarFileEntry>, AutoCloseable {

	private final ZipFile zipFile;
	private Enumeration<? extends ZipEntry> enumeration;

	public JarFileEntryIterator(File file) {

		try {
			this.zipFile = new ZipFile(file);
			this.enumeration = zipFile.entries();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void close() {

		try {
			zipFile.close();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public boolean hasNext() {

		boolean hasMoreElements = enumeration.hasMoreElements();
		if (hasMoreElements) {
			return true;
		} else {
			close();
			return false;
		}
	}

	@Override
	public JarFileEntry next() {

		return new JarFileEntry(zipFile, enumeration.nextElement());
	}
}
