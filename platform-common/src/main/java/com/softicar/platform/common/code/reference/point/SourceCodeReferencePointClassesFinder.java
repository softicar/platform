package com.softicar.platform.common.code.reference.point;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Finds all classes on the class path that have a
 * {@link SourceCodeReferencePointUuid} annotation.
 *
 * @author Oliver Richers
 */
class SourceCodeReferencePointClassesFinder {

	private static final String ANNOTATION_NAME = SourceCodeReferencePointUuid.class.getCanonicalName();

	public Collection<JavaClassName> findClasses() {

		try (ScanResult scanResult = new ClassGraph().enableAnnotationInfo().scan()) {
			return scanResult//
				.getClassesWithAnnotation(ANNOTATION_NAME)
				.stream()
				.map(classInfo -> new JavaClassName(classInfo.getName()))
				.collect(Collectors.toList());
		}
	}
}
