package com.softicar.platform.common.core.java.classpath;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.analyzer.JavaClassAnalyzer;
import com.softicar.platform.common.core.java.jar.JarFile;
import com.softicar.platform.common.core.java.jar.JarFileEntry;
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
	protected Collection<AnalyzedJavaClass> analyzeClasses() {

		return new JarFile(getFile())//
			.getEntries()
			.stream()
			.filter(this::isClassFile)
			.map(this::analyzeClass)
			.collect(Collectors.toList());
	}

	private boolean isClassFile(JarFileEntry entry) {

		return entry.getPath().endsWith(".class");
	}

	private AnalyzedJavaClass analyzeClass(JarFileEntry jarFileEntry) {

		return new JavaClassAnalyzer(jarFileEntry::getInputStream).analyze();
	}
}
