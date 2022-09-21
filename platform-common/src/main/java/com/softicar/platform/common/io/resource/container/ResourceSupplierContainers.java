package com.softicar.platform.common.io.resource.container;

import com.softicar.platform.common.core.java.classpath.IJavaClasspathRoot;
import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Utility methods for {@link ResourceSupplierContainer} classes.
 *
 * @author Oliver Richers
 */
public class ResourceSupplierContainers {

	public static Collection<Class<?>> findAll() {

		return JavaClasspath//
			.getInstance()
			.getPayloadRoots()
			.stream()
			.map(IJavaClasspathRoot::getAnalyzedClasses)
			.flatMap(Collection::stream)
			.filter(javaClass -> javaClass.hasAnnotation(ResourceSupplierContainer.class))
			.map(javaClass -> javaClass.loadClass())
			.collect(Collectors.toList());
	}
}
