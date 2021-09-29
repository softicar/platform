package com.softicar.platform.common.core.java.classpath.linking;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.method.reference.JavaMethodReference;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class JavaClasspathLinkingValidatorResult {

	private final Map<JavaClassName, Set<JavaClassName>> missingClasses;
	private final Map<JavaMethodReference, Set<JavaClassName>> unresolvedMethodReferences;

	public JavaClasspathLinkingValidatorResult() {

		this.missingClasses = new TreeMap<>();
		this.unresolvedMethodReferences = new TreeMap<>();
	}

	public Map<JavaClassName, Set<JavaClassName>> getMissingClasses() {

		return Collections.unmodifiableMap(missingClasses);
	}

	public Map<JavaMethodReference, Set<JavaClassName>> getUnresolvedMethodReferences() {

		return Collections.unmodifiableMap(unresolvedMethodReferences);
	}

	void addMissingClass(JavaClassName missingClass, AnalyzedJavaClass referencingClass) {

		missingClasses.computeIfAbsent(missingClass, dummy -> new TreeSet<>()).add(referencingClass.getClassName());
	}

	void addUnresolvedMethodReference(JavaMethodReference methodReference, AnalyzedJavaClass referencingClass) {

		unresolvedMethodReferences.computeIfAbsent(methodReference, dummy -> new TreeSet<>()).add(referencingClass.getClassName());
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		for (Entry<JavaClassName, Set<JavaClassName>> entry: missingClasses.entrySet()) {
			builder.append(String.format("missing class: %s\n", entry.getKey()));
			for (JavaClassName referencingClass: entry.getValue()) {
				builder.append(String.format("\t%s\n", referencingClass));
			}
		}

		for (Entry<JavaMethodReference, Set<JavaClassName>> entry: unresolvedMethodReferences.entrySet()) {
			builder.append(String.format("unresolved method reference: %s\n", entry.getKey()));
			for (JavaClassName referencingClass: entry.getValue()) {
				builder.append(String.format("\t%s\n", referencingClass));
			}
		}

		return builder.toString();
	}
}
