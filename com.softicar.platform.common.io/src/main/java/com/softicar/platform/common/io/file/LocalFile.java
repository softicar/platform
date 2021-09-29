package com.softicar.platform.common.io.file;

import java.io.File;
import java.io.InputStream;

public class LocalFile implements IFile {

	private final File file;
	private final IPath path;

	public LocalFile(File file) {

		this.file = file;
		this.path = Path.parse(file.getAbsolutePath());
	}

	@Override
	public String getName() {

		return file.getName();
	}

	@Override
	public IPath getPath() {

		return path;
	}

	@Override
	public InputStream getInputStream() {

		return FileInputStreamFactory.create(file);
	}

}
