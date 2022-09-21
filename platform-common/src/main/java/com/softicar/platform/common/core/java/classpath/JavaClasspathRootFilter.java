package com.softicar.platform.common.core.java.classpath;

import java.util.function.Predicate;

public class JavaClasspathRootFilter implements Predicate<IJavaClasspathRoot> {

	private boolean includeFolders;
	private boolean includeJars;
	private boolean includeUnknowns;
	private String jarRegex;

	public JavaClasspathRootFilter() {

		this.includeFolders = true;
		this.includeJars = true;
		this.includeUnknowns = false;
		this.jarRegex = ".*";
	}

	@Override
	public boolean test(IJavaClasspathRoot root) {

		if (root.isFolder()) {
			return includeFolders;
		} else if (root.isJar()) {
			return includeJars && root.getFile().getName().matches(jarRegex);
		} else {
			return includeUnknowns;
		}
	}

	public JavaClasspathRootFilter setIncludeFolders(boolean includeFolders) {

		this.includeFolders = includeFolders;
		return this;
	}

	public JavaClasspathRootFilter setIncludeJars(boolean includeJars) {

		this.includeJars = includeJars;
		return this;
	}

	public JavaClasspathRootFilter setIncludeUnknowns(boolean includeUnknowns) {

		this.includeUnknowns = includeUnknowns;
		return this;
	}

	public JavaClasspathRootFilter setJarRegex(String jarRegex) {

		this.jarRegex = jarRegex;
		return this;
	}
}
