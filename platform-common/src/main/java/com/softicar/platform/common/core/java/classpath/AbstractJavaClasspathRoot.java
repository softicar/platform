package com.softicar.platform.common.core.java.classpath;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.analyzer.JavaClassAnalyzer;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

abstract class AbstractJavaClasspathRoot implements IJavaClasspathRoot {

	private final File file;
	private final Map<JavaClassName, AnalyzedJavaClass> classMap;
	private boolean initialized;

	public AbstractJavaClasspathRoot(File file) {

		this.file = file;
		this.classMap = new TreeMap<>();
		this.initialized = false;
	}

	@Override
	public final File getFile() {

		return file;
	}

	@Override
	public final Collection<IClasspathFile> getClassFiles() {

		return getAllFiles()//
			.stream()
			.filter(this::isClassFile)
			.collect(Collectors.toList());
	}

	@Override
	public final Collection<IClasspathFile> getResourceFiles() {

		return getAllFiles()//
			.stream()
			.filter(this::isNotClassFile)
			.collect(Collectors.toList());
	}

	@Override
	public final Collection<AnalyzedJavaClass> getAnalyzedClasses() {

		initializeIfNecessary();
		return Collections.unmodifiableCollection(classMap.values());
	}

	@Override
	public final Optional<AnalyzedJavaClass> getAnalyzedClass(JavaClassName className) {

		initializeIfNecessary();
		return Optional.ofNullable(classMap.get(className));
	}

	@Override
	public final boolean isFolder() {

		return file.isDirectory();
	}

	@Override
	public final boolean isJar() {

		return !file.isDirectory() && file.getName().endsWith(".jar");
	}

	// ------------------------------ private ------------------------------ //

	private boolean isClassFile(IClasspathFile file) {

		return file.getName().endsWith(".class");
	}

	private boolean isNotClassFile(IClasspathFile file) {

		return !isClassFile(file);
	}

	private synchronized void initializeIfNecessary() {

		if (!initialized) {
			analyzeClasses().forEach(javaClass -> classMap.put(javaClass.getClassName(), javaClass));
			this.initialized = true;
		}
	}

	private Collection<AnalyzedJavaClass> analyzeClasses() {

		return getClassFiles()//
			.stream()
			.map(this::analyzeClass)
			.collect(Collectors.toList());
	}

	private AnalyzedJavaClass analyzeClass(IClasspathFile file) {

		try {
			return new JavaClassAnalyzer(file::getInputStream).analyze();
		} catch (Exception exception) {
			System.err.printf("failed: %s\n", file.getName());
			throw exception;
		}
	}
}
