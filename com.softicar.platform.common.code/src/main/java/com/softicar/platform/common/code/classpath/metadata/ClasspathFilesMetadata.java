package com.softicar.platform.common.code.classpath.metadata;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.classfile.ClassFile;
import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Identify classes that annotate, implement, or extend certain classes or
 * interfaces
 */
public class ClasspathFilesMetadata implements IClasspathFilesMetadata {

	private static final String CLASS_FILENAME_ENDING = ".class";
	private static final String MODULE_INFO_CLASS_FILENAME = "module-info.class";
	private final Map<String, Set<String>> extendingClasses;
	private final Map<String, Set<String>> implementingClasses;
	private final Map<String, Set<String>> annotatedClasses;

	public ClasspathFilesMetadata(Iterable<IClasspathFile> iterable) {

		extendingClasses = new TreeMap<>();
		implementingClasses = new TreeMap<>();
		annotatedClasses = new TreeMap<>();
		for (IClasspathFile file: Objects.requireNonNull(iterable)) {
			if (file.getName().endsWith(CLASS_FILENAME_ENDING) && !file.getName().equals(MODULE_INFO_CLASS_FILENAME)) {
				try (InputStream inputStream = file.getInputStream()) {
					ClassFile classFile = new ClassFile(inputStream);

					parseMetadata(extendingClasses, classFile, Collections.singleton(classFile.getSuperClass()));
					parseMetadata(implementingClasses, classFile, classFile.getInterfaces());
					parseMetadata(annotatedClasses, classFile, parseAnnotationsFrom(classFile));
				} catch (IOException exception) {
					throw new SofticarIOException(exception);
				}
			}
		}
	}

	@Override
	public Collection<Class<?>> getDirectlyImplementingClasses(Class<?> interfaceClass) {

		return getClasses(interfaceClass, implementingClasses);
	}

	@Override
	public Collection<Class<?>> getDirectlyExtendingClasses(Class<?> baseClass) {

		return getClasses(baseClass, extendingClasses);
	}

	@Override
	public Collection<Class<?>> getDirectlyAnnotatedClasses(Class<? extends Annotation> annotation) {

		return getClasses(annotation, annotatedClasses);
	}

	@Override
	public Collection<Class<?>> getTransitivelyImplementingClasses(Class<?> interfaces) {

		return new TransitivelyImplementingClassesFinder(this).findAll(interfaces);
	}

	@Override
	public Collection<Class<?>> getTransitivelyExtendingClasses(Class<?> baseClass) {

		return new TransitivelyExtendingClassesFinder(this).findAll(baseClass);
	}

	// ------------------------------ private ------------------------------ //

	private Collection<Class<?>> getClasses(Class<?> interfaceClass, Map<String, Set<String>> classesMap) {

		return classesMap//
			.getOrDefault(interfaceClass.getName(), Collections.emptySet())
			.stream()
			.map(this::getClassByName)
			.collect(Collectors.toList());
	}

	private Class<?> getClassByName(String className) {

		try {
			return Class.forName(className);
		} catch (ClassNotFoundException exception) {
			throw new SofticarDeveloperException(exception);
		}
	}

	private void parseMetadata(Map<String, Set<String>> classMetadataMap, ClassFile classFile, Collection<String> metadataRaw) {

		for (String classname: metadataRaw) {
			classMetadataMap//
				.computeIfAbsent(classname.replace("/", "."), dummy -> new TreeSet<>())
				.add(classFile.getThisClass().replace("/", "."));
		}
	}

	private Collection<String> parseAnnotationsFrom(ClassFile classFile) {

		TreeSet<String> annotations = new TreeSet<>();
		classFile //
			.getAttributes()
			.stream()
			.filter(
				filteredAttribute -> filteredAttribute.getName().equals("RuntimeVisibleAnnotations")
						|| filteredAttribute.getName().equals("RuntimeInvisibleAnnotations"))
			.forEach(foundAttribute -> annotations.addAll(foundAttribute.parseAnnotationsFrom()));

		return annotations;
	}

}
