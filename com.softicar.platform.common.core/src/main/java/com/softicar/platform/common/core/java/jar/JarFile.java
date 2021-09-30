package com.softicar.platform.common.core.java.jar;

import java.io.File;

public class JarFile {

	private final File file;

	public JarFile(File file) {

		this.file = file;
	}

	public File getFile() {

		return file;
	}

	public JarFileEntryIterable getEntries() {

		return new JarFileEntryIterable(file);
	}
}
