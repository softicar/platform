package com.softicar.platform.common.code.classpath.iterable;

import com.softicar.platform.common.container.file.tree.FileTreeIterable;
import com.softicar.platform.common.container.iterable.MappingIterable;
import com.softicar.platform.common.container.iterable.concat.ConcatIterable;
import com.softicar.platform.common.core.java.classpath.finder.JavaClasspathFinder;
import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * An {@link Iterable} over all files on the class path.
 *
 * @author Oliver Richers
 */
public class ClasspathFileIterable implements Iterable<IClasspathFile> {

	private Predicate<File> folderFilter;
	private Predicate<File> jarFileFilter;

	public ClasspathFileIterable() {

		this.folderFilter = file -> true;
		this.jarFileFilter = file -> true;
	}

	public ClasspathFileIterable setFolderFilter(Predicate<File> folderFilter) {

		this.folderFilter = folderFilter;
		return this;
	}

	public ClasspathFileIterable setJarFileFilter(Predicate<File> jarFileFilter) {

		this.jarFileFilter = jarFileFilter;
		return this;
	}

	@Override
	public Iterator<IClasspathFile> iterator() {

		ConcatIterable<IClasspathFile> iterable = new ConcatIterable<>();
		Collection<File> classpathEntries = new JavaClasspathFinder().findAll();
		for (File classpathEntry: classpathEntries) {
			if (!ClasspathFiles.isJarFile(classpathEntry) && folderFilter.test(classpathEntry)) {
				iterable.add(new MappingIterable<>(new FileTreeIterable(classpathEntry), file -> new InternalPlainClasspathFile(classpathEntry, file)));
			}
		}
		iterable.add(new MappingIterable<>(new InternalClasspathJarFileEntriesIterable(classpathEntries, jarFileFilter), InternalZipClasspathFile::new));
		return iterable.iterator();
	}

	public Stream<IClasspathFile> stream() {

		return StreamSupport.stream(spliterator(), false);
	}
}
