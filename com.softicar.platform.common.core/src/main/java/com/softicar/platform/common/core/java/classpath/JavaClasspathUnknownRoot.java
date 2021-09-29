package com.softicar.platform.common.core.java.classpath;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
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
	protected Collection<AnalyzedJavaClass> analyzeClasses() {

		return Collections.emptyList();
	}
}
