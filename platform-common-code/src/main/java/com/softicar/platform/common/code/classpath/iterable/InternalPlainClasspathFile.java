package com.softicar.platform.common.code.classpath.iterable;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

class InternalPlainClasspathFile implements IClasspathFile {

	private final File classpathRoot;
	private final File file;

	public InternalPlainClasspathFile(File classpathRoot, File file) {

		this.classpathRoot = classpathRoot;
		this.file = file;
	}

	@Override
	public InputStream getInputStream() {

		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public String getName() {

		return file.getName();
	}

	@Override
	public String getRelativePath() {

		return classpathRoot.toPath().relativize(file.toPath()).toString();
	}

	@Override
	public boolean isDirectory() {

		return file.isDirectory();
	}

	@Override
	public String toString() {

		return String.format("%s (%s)", getRelativePath(), classpathRoot.getPath());
	}
}
