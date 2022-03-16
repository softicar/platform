package com.softicar.platform.common.core.java.classpath;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.Map;
import java.util.TreeMap;

/**
 * Loads all classes on the class path as {@link AnalyzedJavaClass}.
 *
 * @author Oliver Richers
 */
public class JavaClasspathAnalyzedClassesLoader {

	private Map<JavaClassName, AnalyzedJavaClass> javaClasses;

	public Map<JavaClassName, AnalyzedJavaClass> loadClasses() {

		this.javaClasses = new TreeMap<>();
		new JavaClasspathLoader()//
			.load()
			.getAllRoots()
			.stream()
			.forEach(this::addJavaClasses);
		return javaClasses;
	}

	private void addJavaClasses(IJavaClasspathRoot root) {

		root//
			.getAnalyzedClasses()
			.forEach(this::addJavaClass);
	}

	private void addJavaClass(AnalyzedJavaClass javaClass) {

		javaClasses.putIfAbsent(javaClass.getClassName(), javaClass);
	}
}
