package com.softicar.platform.common.core.java.classpath.linking;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.analyzer.JavaClassAnalyzer;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.identifier.declaration.JavaIdentifierDeclaration;
import com.softicar.platform.common.core.java.identifier.key.JavaIdentifierKey;
import com.softicar.platform.common.core.java.method.reference.JavaMethodReference;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Validates referential integrity of the classes on the class path.
 * <p>
 * Known limitations:
 * <ul>
 * <li>Only validates method references.</li>
 * <li>No validation of class references is done.</li>
 * </ul>
 *
 * @author Oliver Richers
 */
public class JavaClasspathLinkingValidator {

	private final Map<JavaClassName, AnalyzedJavaClass> classesOnClassPath;
	private Predicate<AnalyzedJavaClass> classFilter;
	private JavaClasspathLinkingValidatorResult validatorResult;

	public JavaClasspathLinkingValidator(Map<JavaClassName, AnalyzedJavaClass> classesOnClassPath) {

		this.classesOnClassPath = classesOnClassPath;
		this.classFilter = dummy -> true;
		this.validatorResult = null;
	}

	/**
	 * Defines a filter for classes to validate.
	 *
	 * @param classFilter
	 *            the class filter (never null)
	 * @return this object
	 */
	public JavaClasspathLinkingValidator setClassFilter(Predicate<AnalyzedJavaClass> classFilter) {

		this.classFilter = classFilter;
		return this;
	}

	public JavaClasspathLinkingValidatorResult validate() {

		this.validatorResult = new JavaClasspathLinkingValidatorResult();
		validateClasses();
		return validatorResult;
	}

	private void validateClasses() {

		classesOnClassPath//
			.values()
			.stream()
			.filter(classFilter)
			.collect(Collectors.toList())
			.forEach(this::validateClass);
	}

	private void validateClass(AnalyzedJavaClass javaClass) {

		ClassLinkingValidator linkingValidator = new ClassLinkingValidator(javaClass);
		javaClass//
			.getReferencedMethods(this::isRelevantMethodReference)
			.forEach(methodReference -> linkingValidator.validate(methodReference));
	}

	private boolean isRelevantMethodReference(JavaMethodReference methodReference) {

		return !methodReference.getOwner().isArray();
	}

	private class ClassLinkingValidator {

		private final AnalyzedJavaClass referencingClass;

		public ClassLinkingValidator(AnalyzedJavaClass referencingClass) {

			this.referencingClass = referencingClass;
		}

		public void validate(JavaMethodReference methodReference) {

			Optional<JavaIdentifierDeclaration> methodDeclaration = findMethodDeclaration(methodReference.getOwner(), methodReference.getMethodKey());
			if (!methodDeclaration.isPresent()) {
				validatorResult.addUnresolvedMethodReference(methodReference, referencingClass);
			}
		}

		private Optional<JavaIdentifierDeclaration> findMethodDeclaration(JavaClassName targetClassName, JavaIdentifierKey methodKey) {

			return getAnalyzedJavaClass(targetClassName)//
				.flatMap(targetClass -> findMethodDeclaration(targetClass, methodKey));
		}

		private Optional<JavaIdentifierDeclaration> findMethodDeclaration(AnalyzedJavaClass targetClass, JavaIdentifierKey methodKey) {

			Optional<JavaIdentifierDeclaration> methodFromClass = targetClass.findDeclaredMethod(methodKey);
			if (methodFromClass.isPresent()) {
				return methodFromClass;
			}

			Optional<JavaIdentifierDeclaration> methodFromInterface = findMethodDeclarationInInterfaces(targetClass, methodKey);
			if (methodFromInterface.isPresent()) {
				return methodFromInterface;
			}

			Optional<JavaIdentifierDeclaration> methodFromSuperClass = findMethodDeclarationInSuperClass(targetClass, methodKey);
			if (methodFromSuperClass.isPresent()) {
				return methodFromSuperClass;
			}

			return Optional.empty();
		}

		private Optional<JavaIdentifierDeclaration> findMethodDeclarationInInterfaces(AnalyzedJavaClass targetClass, JavaIdentifierKey methodKey) {

			for (JavaClassName interfaceClassName: targetClass.getInterfaces()) {
				Optional<JavaIdentifierDeclaration> methodDeclaration = findMethodDeclaration(interfaceClassName, methodKey);
				if (methodDeclaration.isPresent()) {
					return methodDeclaration;
				}
			}
			return Optional.empty();
		}

		private Optional<JavaIdentifierDeclaration> findMethodDeclarationInSuperClass(AnalyzedJavaClass targetClass, JavaIdentifierKey methodKey) {

			return Optional//
				.ofNullable(targetClass.getSuperClass())
				.flatMap(superClass -> findMethodDeclaration(superClass, methodKey));
		}

		private Optional<AnalyzedJavaClass> getAnalyzedJavaClass(JavaClassName className) {

			AnalyzedJavaClass javaClass = classesOnClassPath.get(className);
			if (javaClass != null) {
				return Optional.of(javaClass);
			} else {
				try {
					Class<?> clazz = Class.forName(className.getName());
					AnalyzedJavaClass analyzedJavaClass = new JavaClassAnalyzer(clazz).analyze();
					classesOnClassPath.put(className, analyzedJavaClass);
					return Optional.of(analyzedJavaClass);
				} catch (@SuppressWarnings("unused") ClassNotFoundException exception) {
					validatorResult.addMissingClass(className, referencingClass);
					return Optional.empty();
				}
			}
		}
	}
}
