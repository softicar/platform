package com.softicar.platform.common.core.java.classpath;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

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
	public File getFile() {

		return file;
	}

	@Override
	public Collection<AnalyzedJavaClass> getAnalyzedClasses() {

		initializeIfNecessary();
		return classMap.values();
	}

	@Override
	public Optional<AnalyzedJavaClass> getAnalyzedClass(JavaClassName className) {

		initializeIfNecessary();
		return Optional.ofNullable(classMap.get(className));
	}

	@Override
	public boolean isFolder() {

		return file.isDirectory();
	}

	@Override
	public boolean isJar() {

		return !file.isDirectory() && file.getName().endsWith(".jar");
	}

	protected abstract Collection<AnalyzedJavaClass> analyzeClasses();

	private void initializeIfNecessary() {

		if (!initialized) {
			analyzeClasses().forEach(javaClass -> classMap.put(javaClass.getClassName(), javaClass));
			this.initialized = true;
		}
	}
}
