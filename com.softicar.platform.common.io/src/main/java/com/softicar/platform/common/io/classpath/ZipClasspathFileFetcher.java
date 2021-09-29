package com.softicar.platform.common.io.classpath;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import com.softicar.platform.common.io.classpath.file.ZipClasspathFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Retrieves {@link IClasspathFile} instances that reference files from a given
 * zip-compressed file (e.g. a .jar archive), using the provided filters.
 *
 * @author Alexander Schmidt
 */
class ZipClasspathFileFetcher {

	private final File archive;
	private final Collection<String> extensionFilter;
	private final Collection<Package> packageFilter;

	public ZipClasspathFileFetcher(File archive, Collection<String> extensionFilter, Collection<Package> packageFilter) {

		this.archive = Objects.requireNonNull(archive);
		this.extensionFilter = ClasspathFileUtils.getNormalizedExtensionFilter(extensionFilter);
		this.packageFilter = Objects.requireNonNull(packageFilter);
	}

	public List<IClasspathFile> getClasspathFiles() {

		try (ZipFile zipFile = new ZipFile(archive)) {
			List<IClasspathFile> classpathFiles = new ArrayList<>();
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				if (isValidEntry(entry)) {
					classpathFiles.add(new ZipClasspathFile(archive, entry.getName(), entry.isDirectory()));
				}
			}
			return classpathFiles;
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private boolean isValidEntry(ZipEntry entry) {

		if (!entry.isDirectory()) {
			return isValidPackage(entry) && isValidExtension(entry);
		} else {
			return false;
		}
	}

	private boolean isValidExtension(ZipEntry entry) {

		if (extensionFilter.isEmpty()) {
			return true;
		}
		if (extensionFilter.contains(ClasspathFileUtils.getExtension(entry.getName()))) {
			return true;
		}
		return false;
	}

	private boolean isValidPackage(ZipEntry entry) {

		if (packageFilter.isEmpty()) {
			return true;
		} else {
			String entryName = entry.getName();
			for (String pathPrefix: ClasspathFileUtils.getRelativePaths(packageFilter)) {
				if (entryName.startsWith(pathPrefix)) {
					return true;
				}
			}
			return false;
		}
	}
}
