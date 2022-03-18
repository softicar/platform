package com.softicar.platform.common.io.classpath;

import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import com.softicar.platform.common.io.classpath.file.PlainClasspathFile;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Retrieves {@link IClasspathFile} instances that reference files from the
 * given class paths (either directories or .jar archives), using the provided
 * filters.
 *
 * @author Alexander Schmidt
 */
public class ClasspathFileFetcher {

	private final Collection<File> classpaths;
	private Collection<String> extensionFilter;
	private Collection<Package> packageFilter;

	public ClasspathFileFetcher(Collection<File> classpaths) {

		this.classpaths = classpaths;
		this.extensionFilter = Collections.emptyList();
		this.packageFilter = Collections.emptyList();
	}

	public ClasspathFileFetcher setExtensionFilter(Collection<String> filter) {

		this.extensionFilter = ClasspathFileUtils.getNormalizedExtensionFilter(filter);
		return this;
	}

	public ClasspathFileFetcher setPackageFilter(Package filter) {

		return setPackageFilter(Collections.singleton(filter));
	}

	public ClasspathFileFetcher setPackageFilter(Collection<Package> filter) {

		this.packageFilter = Objects.requireNonNull(filter);
		return this;
	}

	public List<IClasspathFile> getClasspathFiles() {

		List<IClasspathFile> classpathFiles = new ArrayList<>();
		for (PlainClasspathFile file: new PlainClasspathFileFetcher().getClasspathFiles(classpaths)) {
			if (file.isJarFile()) {
				ZipClasspathFileFetcher fetcher = new ZipClasspathFileFetcher(file.getFile(), extensionFilter, packageFilter);
				classpathFiles.addAll(fetcher.getClasspathFiles());
			} else {
				if (isValidPackage(file) && isValidExtension(file)) {
					classpathFiles.add(file);
				}
			}
		}
		return classpathFiles;
	}

	private boolean isValidPackage(PlainClasspathFile classpathFile) {

		if (packageFilter.isEmpty()) {
			return true;
		} else {
			String filePathInsideClassPath = classpathFile.getFilePathInsideClassPath();
			if (filePathInsideClassPath != null) {
				for (String pathPrefix: ClasspathFileUtils.getRelativePaths(packageFilter)) {
					if (filePathInsideClassPath.startsWith(pathPrefix)) {
						return true;
					}
				}
			}
			return false;
		}
	}

	private boolean isValidExtension(PlainClasspathFile classpathFile) {

		return extensionFilter.isEmpty() || extensionFilter.contains(classpathFile.getExtension());
	}
}
