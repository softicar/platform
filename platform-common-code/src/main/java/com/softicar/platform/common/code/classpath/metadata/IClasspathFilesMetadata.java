package com.softicar.platform.common.code.classpath.metadata;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface IClasspathFilesMetadata {

	Collection<Class<?>> getDirectlyImplementingClasses(Class<?> interfaceClass);

	Collection<Class<?>> getDirectlyExtendingClasses(Class<?> baseClass);

	Collection<Class<?>> getDirectlyAnnotatedClasses(Class<? extends Annotation> annotation);

	Collection<Class<?>> getTransitivelyImplementingClasses(Class<?> interfaceClass);

	Collection<Class<?>> getTransitivelyExtendingClasses(Class<?> baseClass);

}
