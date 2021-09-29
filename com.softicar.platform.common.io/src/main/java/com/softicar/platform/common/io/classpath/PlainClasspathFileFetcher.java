package com.softicar.platform.common.io.classpath;

import com.softicar.platform.common.io.classpath.file.PlainClasspathFile;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Retrieves {@link PlainClasspathFile} instances that reference files from the
 * given class paths.
 *
 * @author Alexander Schmidt
 */
class PlainClasspathFileFetcher {

	/**
	 * @param classpaths
	 *            the {@link File} instances (either directories or .jar
	 *            archives) that represent the class paths to crawl
	 */
	public List<PlainClasspathFile> getClasspathFiles(Collection<File> classpaths) {

		Deque<PlainClasspathFile> stack = new LinkedList<>(toClasspathFiles(classpaths));
		List<PlainClasspathFile> result = new ArrayList<>();
		while (!stack.isEmpty()) {
			PlainClasspathFile current = stack.removeLast();
			if (current.getFile().isDirectory()) {
				stack.addAll(getFilesFromDirectory(current));
			} else {
				result.add(current);
			}
		}
		return result;
	}

	private List<PlainClasspathFile> getFilesFromDirectory(PlainClasspathFile classpathFile) {

		return Arrays//
			.asList(classpathFile.getFile().listFiles())
			.stream()
			.map(file -> new PlainClasspathFile(classpathFile.getClasspath(), file))
			.collect(Collectors.toList());
	}

	private List<PlainClasspathFile> toClasspathFiles(Collection<File> classpaths) {

		return classpaths//
			.stream()
			.map(this::toClasspathFile)
			.collect(Collectors.toList());
	}

	private PlainClasspathFile toClasspathFile(File classpath) {

		return new PlainClasspathFile(classpath, classpath);
	}
}
