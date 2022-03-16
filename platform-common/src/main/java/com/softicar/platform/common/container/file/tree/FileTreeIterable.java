package com.softicar.platform.common.container.file.tree;

import com.softicar.platform.common.container.iterable.recurse.RecurseIterator;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class FileTreeIterable implements Iterable<File> {

	private final Collection<File> roots;

	public FileTreeIterable(File root) {

		this(Collections.singleton(root));
	}

	public FileTreeIterable(Collection<File> roots) {

		this.roots = roots;
	}

	@Override
	public Iterator<File> iterator() {

		return new RecurseIterator<>(roots, FileTreeIterable::recurse);
	}

	private static Iterable<File> recurse(File file) {

		return file.isDirectory()? Arrays.asList(file.listFiles()) : null;
	}
}
