package com.softicar.platform.common.code.classpath.iterable;

import com.softicar.platform.common.container.iterable.concat.ConcatIterable;
import com.softicar.platform.common.io.zip.entry.ZipFileEntry;
import com.softicar.platform.common.io.zip.entry.ZipFileEntryIterable;
import java.io.File;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * An {@link Iterable} over all JAR file entries on the class path.
 *
 * @author Oliver Richers
 */
class InternalClasspathJarFileEntriesIterable extends ConcatIterable<ZipFileEntry> {

	public InternalClasspathJarFileEntriesIterable(Collection<File> classpathRoots, Predicate<File> filter) {

		super(
			classpathRoots//
				.stream()
				.filter(ClasspathFiles::isJarFile)
				.filter(filter)
				.map(file -> new ZipFileEntryIterable(file).setOnlyFiles(true))
				.collect(Collectors.toList()));
	}
}
