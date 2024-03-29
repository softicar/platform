package com.softicar.platform.common.core.java.classpath;

import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import java.io.File;
import java.util.Collection;
import java.util.Collections;

/**
 * Represents an {@link IJavaClasspathRoot} that is neither a folder nor a JAR
 * file.
 *
 * @author Oliver Richers
 */
public class JavaClasspathUnknownRoot extends AbstractJavaClasspathRoot {

	public JavaClasspathUnknownRoot(File file) {

		super(file);
	}

	@Override
	public String toString() {

		return getFile().getAbsolutePath();
	}

	@Override
	public Collection<IClasspathFile> getAllFiles() {

		return Collections.emptyList();
	}
}
