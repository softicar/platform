package com.softicar.platform.common.core.java.classpath;

import com.softicar.platform.common.core.java.jar.JarFile;
import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import com.softicar.platform.common.io.classpath.file.ZipClasspathFile;
import java.io.File;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Represents a JAR file on the class path.
 *
 * @author Oliver Richers
 */
public class JavaClasspathJar extends AbstractJavaClasspathRoot {

	public JavaClasspathJar(File file) {

		super(file);
	}

	@Override
	public String toString() {

		return getFile().getName();
	}

	@Override
	public Collection<IClasspathFile> getAllFiles() {

		return new JarFile(getFile())//
			.getEntries()
			.stream()
			.filter(entry -> !entry.isFolder())
			.map(entry -> new ZipClasspathFile(getFile(), entry.getPath(), false))
			.collect(Collectors.toList());
	}
}
