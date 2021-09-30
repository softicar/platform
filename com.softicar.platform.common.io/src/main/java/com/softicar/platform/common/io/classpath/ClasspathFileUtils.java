package com.softicar.platform.common.io.classpath;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility methods for file and package names.
 *
 * @author Alexander Schmidt
 */
public class ClasspathFileUtils {

	/**
	 * Fetches the extension of the given file name.
	 *
	 * @param fileName
	 * @return the extension of the given file name, in lower case and with a
	 *         leading dot. <i>empty</i> if the file is <i>null</i> or if it has
	 *         no extension.
	 */
	public static String getExtension(String fileName) {

		if (fileName != null) {
			int indexOfLastDot = fileName.lastIndexOf(".");
			int indexOfLastSlash = fileName.lastIndexOf("/");
			if (indexOfLastDot > -1 && indexOfLastDot < fileName.length() - 1 && indexOfLastDot > indexOfLastSlash) {
				return fileName.substring(indexOfLastDot).toLowerCase();
			}
		}
		return "";
	}

	/**
	 * Normalizes the given {@link Collection} of strings to be used in a file
	 * name extension filtering context.
	 *
	 * @param filter
	 * @return A normalized {@link Collection} of strings that can be used for
	 *         file name extension filtering. Never <i>null</i>. Elements are
	 *         guaranteed to be non-<i>null</i>, lower case and to start with a
	 *         dot.
	 */
	public static Collection<String> getNormalizedExtensionFilter(Collection<String> filter) {

		return Optional//
			.ofNullable(filter)
			.orElse(Collections.emptyList())
			.stream()
			.filter(Objects::nonNull)
			.map(it -> it.toLowerCase())
			.filter(it -> !it.equals("."))
			.map(ClasspathFileUtils::getWithLeadingDotIfNecessary)
			.collect(Collectors.toSet());
	}

	/**
	 * Converts the given {@link Collection} of packages into a {@link Set} of
	 * relative path strings.
	 *
	 * @param packages
	 * @return a {@link Set} of strings that represent package names converted
	 *         to relative paths
	 */
	public static Set<String> getRelativePaths(Collection<Package> packages) {

		return packages//
			.stream()
			.filter(Objects::nonNull)
			.map(Package::getName)
			.map(it -> it.replace(".", "/") + "/")
			.collect(Collectors.toSet());
	}

	private static String getWithLeadingDotIfNecessary(String input) {

		if (!input.isEmpty()) {
			if (!input.startsWith(".")) {
				return "." + input;
			}
		}
		return input;
	}
}
