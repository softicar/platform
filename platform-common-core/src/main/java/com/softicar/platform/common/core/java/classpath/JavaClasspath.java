package com.softicar.platform.common.core.java.classpath;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents the class path of the JVM.
 *
 * @author Oliver Richers
 */
public class JavaClasspath {

	private final Collection<IJavaClasspathRoot> allRoots;
	private final Collection<JavaClasspathRootFolder> rootFolders;
	private final Collection<JavaClasspathJar> jars;

	public JavaClasspath() {

		this.allRoots = new ArrayList<>();
		this.rootFolders = new ArrayList<>();
		this.jars = new ArrayList<>();
	}

	public Collection<IJavaClasspathRoot> getAllRoots() {

		return allRoots;
	}

	public Collection<JavaClasspathRootFolder> getRootFolders() {

		return rootFolders;
	}

	public Collection<JavaClasspathJar> getJars() {

		return jars;
	}

	void addRootFolder(JavaClasspathRootFolder rootFolder) {

		addRoot(rootFolder);
		this.rootFolders.add(rootFolder);
	}

	void addJar(JavaClasspathJar jar) {

		addRoot(jar);
		this.jars.add(jar);
	}

	void addRoot(IJavaClasspathRoot root) {

		this.allRoots.add(root);
	}
}
