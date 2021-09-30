package com.softicar.platform.common.io.file;

import java.util.List;

public interface IPath {

	IPath getParent();

	String getName();

	String getAbsolutePath();

	List<String> getElements();
}
