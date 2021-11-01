package com.softicar.platform.common.code.classpath.iterable;

import java.io.File;

public class SofticarClasspathFileIterable extends ClasspathFileIterable {

	public SofticarClasspathFileIterable() {

		setJarFileFilter(this::filterJarFile);
	}

	private boolean filterJarFile(File jarFile) {

		return jarFile.getName().startsWith("com.softicar.");
	}
}
