package com.softicar.platform.common.core.java.classpath;

import com.softicar.platform.common.core.java.classpath.finder.JavaClasspathFinder;
import java.io.File;

public class JavaClasspathLoader {

	private final JavaClasspath classPath;

	public JavaClasspathLoader() {

		this.classPath = new JavaClasspath();
	}

	public JavaClasspath load() {

		for (File classPathRoot: new JavaClasspathFinder().findAll()) {
			if (classPathRoot.isDirectory()) {
				classPath.addRootFolder(new JavaClasspathRootFolder(classPathRoot));
			} else if (classPathRoot.getName().endsWith(".jar")) {
				classPath.addJar(new JavaClasspathJar(classPathRoot));
			} else {
				classPath.addRoot(new JavaClasspathUnknownRoot(classPathRoot));
			}
		}
		return classPath;
	}
}
