package com.softicar.platform.common.io.classpath;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.java.classpath.finder.JavaClasspathFinder;
import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Uses an anchor class to find files, make their content accessible as
 * {@link InputStream} instances, and feeds those streams to a {@link Consumer}
 * (see {@link #findAndConsumeAll(Consumer)}).
 * <p>
 * The anchor class is used to identify relevant class paths, and to define the
 * relevant source code package (including sub packages).
 * <p>
 * Files are retrieved from inside .jar archives, and plain file system alike,
 * if their respective class paths and packages match those provided by the
 * anchor class.
 *
 * @author Alexander Schmidt
 */
public class ClasspathFileConsumer {

	private final Class<?> anchorClass;
	private boolean ignoreJarFiles;
	private Collection<String> extensionFilter;

	public ClasspathFileConsumer(Class<?> anchorClass) {

		this.anchorClass = Objects.requireNonNull(anchorClass);
		this.ignoreJarFiles = false;
		this.extensionFilter = Collections.emptyList();
	}

	public ClasspathFileConsumer setIgnoreJarFiles(boolean ignoreJarFiles) {

		this.ignoreJarFiles = ignoreJarFiles;
		return this;
	}

	public ClasspathFileConsumer setExtensionFilter(String filter) {

		return setExtensionFilter(Collections.singleton(filter));
	}

	public ClasspathFileConsumer setExtensionFilter(Collection<String> filter) {

		this.extensionFilter = ClasspathFileUtils.getNormalizedExtensionFilter(filter);
		return this;
	}

	public void findAndConsumeAll(Consumer<InputStream> consumer) {

		Collection<File> classpathFinder = new JavaClasspathFinder()//
			.setIgnoreJarFiles(ignoreJarFiles)
			.findAll();
		ClasspathFileFetcher fetcher = new ClasspathFileFetcher(classpathFinder)//
			.setExtensionFilter(extensionFilter)
			.setPackageFilter(anchorClass.getPackage());
		for (IClasspathFile proxy: fetcher.getClasspathFiles()) {
			try (InputStream inputStream = proxy.getInputStream()) {
				consumer.accept(inputStream);
			} catch (IOException exception) {
				throw new SofticarIOException(exception);
			}
		}
	}
}
