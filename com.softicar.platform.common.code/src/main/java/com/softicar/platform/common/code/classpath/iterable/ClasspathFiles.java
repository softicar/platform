package com.softicar.platform.common.code.classpath.iterable;

import java.io.File;

/**
 * Utility methods when working with files on the class path.
 *
 * @author Oliver Richers
 */
class ClasspathFiles {

	static boolean isJarFile(File file) {

		return file.isFile() && endsWithIgnoreCase(file.getName(), ".jar");
	}

	static boolean endsWithIgnoreCase(String filename, String suffix) {

		int n = suffix.length();
		return filename.regionMatches(true, filename.length() - n, suffix, 0, n);
	}
}
