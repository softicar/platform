package com.softicar.platform.common.code.string;

import com.softicar.platform.common.core.annotations.Generated;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;
import org.objectweb.asm.ClassReader;

/**
 * Analyzes a given class and extracts all literal string values.
 *
 * @author Daniel Klose
 * @author Oliver Richers
 */
public class ClassStringLiteralAnalyzer {

	private final Collection<Class<?>> superTypeBlacklist;
	private final Collection<String> packagePathBlacklist;

	public ClassStringLiteralAnalyzer() {

		this.superTypeBlacklist = new HashSet<>();
		this.packagePathBlacklist = new TreeSet<>();
	}

	public ClassStringLiteralAnalyzer addBlacklistedSuperType(Class<?> superType) {

		this.superTypeBlacklist.add(superType);
		return this;
	}

	public ClassStringLiteralAnalyzer addBlacklistedPackagePath(String packagePath) {

		this.packagePathBlacklist.add(packagePath);
		return this;
	}

	public ClassStringLiteralAnalyzerResult analyze(Class<?> classToAnalyze) {

		ClassStringLiteralAnalyzerResult result = new ClassStringLiteralAnalyzerResult(classToAnalyze);
		if (isValidClass(classToAnalyze)) {
			String innerClassNamePrefix = classToAnalyze.getSimpleName() + "$";
			try (InputStream inputStream = getClassInputStream(classToAnalyze)) {
				analyze(inputStream, result);
			} catch (IOException exception) {
				throw new RuntimeException(exception);
			}
			for (Class<?> innerClass: classToAnalyze.getDeclaredClasses()) {
				analyzeInner(innerClass, innerClassNamePrefix, result);
			}
		}
		return result;
	}

	private boolean isValidClass(Class<?> classToAnalyze) {

		for (Class<?> interfaceClass: classToAnalyze.getInterfaces()) {
			if (superTypeBlacklist.contains(interfaceClass)) {
				return false;
			}
		}
		for (String packagePath: packagePathBlacklist) {
			if (classToAnalyze.getCanonicalName().startsWith(packagePath)) {
				return false;
			}
		}
		return classToAnalyze.getAnnotation(Generated.class) == null;
	}

	private void analyzeInner(Class<?> innerClass, String innerClassNamePrefix, ClassStringLiteralAnalyzerResult result) {

		try (InputStream inputStream = getInnerClassInputStream(innerClass, innerClassNamePrefix)) {
			analyze(inputStream, result);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		if (innerClass.getDeclaredClasses().length > 0) {
			innerClassNamePrefix += innerClass.getSimpleName() + "$";
		}
		for (Class<?> innerInnerClass: innerClass.getDeclaredClasses()) {
			analyzeInner(innerInnerClass, innerClassNamePrefix, result);
		}
	}

	private void analyze(InputStream inputStream, ClassStringLiteralAnalyzerResult result) {

		try {
			new ClassReader(inputStream).accept(new InternalClassStringLiteralAnalyzer(result), 0);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private InputStream getClassInputStream(Class<?> classToAnalyze) {

		return classToAnalyze.getResourceAsStream(classToAnalyze.getSimpleName() + ".class");
	}

	private InputStream getInnerClassInputStream(Class<?> innerClass, String innerClassNamePrefix) {

		return innerClass.getResourceAsStream(innerClassNamePrefix + innerClass.getSimpleName() + ".class");
	}
}
