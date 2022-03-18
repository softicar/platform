package com.softicar.platform.common.io.file;

import java.io.InputStream;

public interface IFile {

	String getName();

	IPath getPath();

	InputStream getInputStream();
}
