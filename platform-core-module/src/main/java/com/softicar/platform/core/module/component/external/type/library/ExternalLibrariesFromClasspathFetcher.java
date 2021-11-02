package com.softicar.platform.core.module.component.external.type.library;

import com.softicar.platform.common.core.java.classpath.finder.JavaClasspathFinder;
import com.softicar.platform.core.module.component.external.IExternalComponent;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Creates {@link IExternalComponent} instances for all {@code ".jar"} libraries
 * in the class path.
 *
 * @author Alexander Schmidt
 */
public class ExternalLibrariesFromClasspathFetcher {

	public List<IExternalComponent> fetchAll() {

		return new JavaClasspathFinder()//
			.findAll()
			.stream()
			.filter(this::isJar)
			.map(this::createComponent)
			.collect(Collectors.toList());
	}

	private boolean isJar(File classPathRoot) {

		return classPathRoot.getName().endsWith(".jar");
	}

	private IExternalComponent createComponent(File jarFile) {

		return new ExternalLibraryFactory(jarFile).create();
	}
}
