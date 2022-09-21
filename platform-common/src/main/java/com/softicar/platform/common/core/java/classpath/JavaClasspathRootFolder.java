package com.softicar.platform.common.core.java.classpath;

import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import com.softicar.platform.common.io.classpath.file.PlainClasspathFile;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Represents a root folder on the class path.
 *
 * @author Oliver Richers
 */
public class JavaClasspathRootFolder extends AbstractJavaClasspathRoot {

	private final Collection<File> files;

	public JavaClasspathRootFolder(File file) {

		super(file);

		this.files = new ArrayList<>();
		addFilesDeep(file);
	}

	@Override
	public String toString() {

		return getFile().getAbsolutePath();
	}

	@Override
	public Collection<IClasspathFile> getAllFiles() {

		return files//
			.stream()
			.map(file -> new PlainClasspathFile(getFile(), file))
			.collect(Collectors.toList());
	}

	private void addFilesDeep(File currentFolder) {

		for (File file: currentFolder.listFiles()) {
			if (file.isDirectory()) {
				addFilesDeep(file);
			} else {
				files.add(file);
			}
		}
	}
}
