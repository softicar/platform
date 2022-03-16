package com.softicar.platform.common.core.java.jar;

import java.io.File;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JarFileEntryIterable implements Iterable<JarFileEntry> {

	private final File file;

	public JarFileEntryIterable(File file) {

		this.file = file;
	}

	@Override
	public Iterator<JarFileEntry> iterator() {

		return new JarFileEntryIterator(file);
	}

	public Stream<JarFileEntry> stream() {

		return StreamSupport.stream(spliterator(), false);
	}
}
