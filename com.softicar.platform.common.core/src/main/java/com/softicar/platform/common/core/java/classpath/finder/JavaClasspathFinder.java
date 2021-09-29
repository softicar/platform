package com.softicar.platform.common.core.java.classpath.finder;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Fetches all class path {@link File} entries.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class JavaClasspathFinder {

	private boolean ignoreJarFiles;

	public JavaClasspathFinder() {

		this.ignoreJarFiles = false;
	}

	public JavaClasspathFinder setIgnoreJarFiles(boolean ignoreJarFiles) {

		this.ignoreJarFiles = ignoreJarFiles;
		return this;
	}

	/**
	 * Returns all classpath elements that represent existing {@link File}
	 * objects.
	 *
	 * @return all valid classpath elements (never <i>null</i>)
	 */
	public Collection<File> findAll() {

		return getAllRoots()//
			.stream()
			.filter(file -> !isIgnored(file))
			.collect(Collectors.toList());
	}

	private boolean isIgnored(File file) {

		return ignoreJarFiles && file.getName().endsWith(".jar");
	}

	private Collection<File> getAllRoots() {

		try (ScanResult scanResult = new ClassGraph().scan()) {
			return scanResult//
				.getClasspathURLs()
				.stream()
				.map(this::toUri)
				.filter(this::isFile)
				.map(File::new)
				.collect(Collectors.toList());
		}
	}

	private URI toUri(URL url) {

		try {
			return new URI(getUrlStringWithReplacedWindowsSeparators(url));
		} catch (URISyntaxException exception) {
			throw new RuntimeException(exception);
		}
	}

	private String getUrlStringWithReplacedWindowsSeparators(URL url) {

		return url.toString().replace('\\', '/');
	}

	private boolean isFile(URI uri) {

		return uri.isAbsolute() && !uri.isOpaque();
	}
}
