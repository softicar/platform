package com.softicar.platform.common.core.java.classpath;

import com.softicar.platform.common.core.java.classpath.finder.JavaClasspathFinder;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents the class path of the JVM.
 *
 * @author Oliver Richers
 */
public class JavaClasspath {

	private static final JavaClasspath INSTANCE = new JavaClasspath();
	private final Collection<IJavaClasspathRoot> roots;
	private Predicate<IJavaClasspathRoot> payloadFilter;

	private JavaClasspath() {

		this.roots = loadRoots();
		this.payloadFilter = new JavaClasspathRootFilter();
	}

	public static JavaClasspath getInstance() {

		return INSTANCE;
	}

	public Collection<IJavaClasspathRoot> getAllRoots() {

		return Collections.unmodifiableCollection(roots);
	}

	public synchronized Collection<IJavaClasspathRoot> getPayloadRoots() {

		return roots//
			.stream()
			.filter(payloadFilter)
			.collect(Collectors.toList());
	}

	public synchronized void setPayloadFilter(Predicate<IJavaClasspathRoot> payloadFilter) {

		this.payloadFilter = payloadFilter;
	}

	private static Collection<IJavaClasspathRoot> loadRoots() {

		var roots = new ArrayList<IJavaClasspathRoot>();
		for (File classPathRoot: new JavaClasspathFinder().findAll()) {
			if (classPathRoot.isDirectory()) {
				roots.add(new JavaClasspathRootFolder(classPathRoot));
			} else if (classPathRoot.getName().endsWith(".jar")) {
				roots.add(new JavaClasspathJar(classPathRoot));
			} else {
				roots.add(new JavaClasspathUnknownRoot(classPathRoot));
			}
		}
		return roots;
	}
}
