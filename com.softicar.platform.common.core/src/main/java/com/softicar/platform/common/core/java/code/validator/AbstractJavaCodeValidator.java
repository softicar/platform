package com.softicar.platform.common.core.java.code.validator;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classpath.IJavaClasspathRoot;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validation.output.IJavaCodeValidationOuput;
import com.softicar.platform.common.core.java.code.validation.output.JavaCodeViolations;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Convenience base class for classes implementing {@link IJavaCodeValidator}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractJavaCodeValidator implements IJavaCodeValidator, IJavaCodeValidationOuput {

	private final Collection<Consumer<Class<?>>> classValidators;
	private final Collection<Consumer<AnalyzedJavaClass>> analyzedClassValidators;
	private Predicate<IJavaClasspathRoot> classPathRootFilter;
	private Predicate<AnalyzedJavaClass> classFilter;
	private JavaCodeValidationEnvironment environment;
	private JavaCodeViolations violations;

	public AbstractJavaCodeValidator() {

		this.classValidators = new ArrayList<>();
		this.analyzedClassValidators = new ArrayList<>();
		this.classPathRootFilter = IJavaClasspathRoot::isFolder;
		this.classFilter = clazz -> true;
	}

	// ------------------------------ validation ------------------------------ //

	@Override
	public final void validate(JavaCodeValidationEnvironment environment) {

		this.environment = environment;
		this.violations = new JavaCodeViolations();

		environment//
			.getClassPath()
			.getAllRoots()
			.stream()
			.filter(classPathRootFilter)
			.map(IJavaClasspathRoot::getAnalyzedClasses)
			.flatMap(Collection::stream)
			.filter(classFilter)
			.forEach(this::validateClass);

		violations.throwExceptionIfNotEmpty();
	}

	@Override
	public void addViolation(String message) {

		violations.addViolation(message);
	}

	@Override
	public void formatViolation(String message, Object...arguments) {

		violations.formatViolation(message, arguments);
	}

	private void validateClass(AnalyzedJavaClass javaClass) {

		environment.logVerbose("%s: Validating class: %s", getClass().getSimpleName(), javaClass.getClassName());
		assertValidatorsDefined();
		executeAnalyzedClassValidators(javaClass);
		executeLoadedClassValidators(javaClass);
	}

	private void assertValidatorsDefined() {

		if (analyzedClassValidators.isEmpty() && classValidators.isEmpty()) {
			throw new IllegalStateException(String.format("Validator does not define any sub-validators: %s", getClass().getCanonicalName()));
		}
	}

	private void executeAnalyzedClassValidators(AnalyzedJavaClass javaClass) {

		analyzedClassValidators.forEach(validator -> validator.accept(javaClass));
	}

	private void executeLoadedClassValidators(AnalyzedJavaClass javaClass) {

		if (!classValidators.isEmpty()) {
			Class<?> loadedClass = loadClass(javaClass);
			classValidators.forEach(validator -> validator.accept(loadedClass));
		}
	}

	// ------------------------------ configuration ------------------------------ //

	protected void setClassPathRootFilter(Predicate<IJavaClasspathRoot> classPathRootFilter) {

		this.classPathRootFilter = classPathRootFilter;
	}

	protected void setClassFilter(Predicate<AnalyzedJavaClass> classFilter) {

		this.classFilter = classFilter;
	}

	protected void addClassValidator(Consumer<Class<?>> validator) {

		classValidators.add(validator);
	}

	protected void addAnalyzedClassValidator(Consumer<AnalyzedJavaClass> validator) {

		analyzedClassValidators.add(validator);
	}

	// ------------------------------ utilities ------------------------------ //

	// TODO move this into AnalyzedJavaClass
	// TODO use it in I18nClassesValidator
	protected Class<?> loadClass(AnalyzedJavaClass javaClass) {

		try {
			return Class.forName(javaClass.getClassName().getCanonicalName());
		} catch (ClassNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}

	// TODO move this into AnalyzedJavaClass
	// TODO use it in I18nClassesValidator
	protected boolean hasAnnotation(AnalyzedJavaClass javaClass, Class<?> annotationClass) {

		return javaClass.getAnnotations().stream().anyMatch(annotation -> annotation.is(annotationClass));
	}

	protected JavaCodeValidationEnvironment getEnvironment() {

		return environment;
	}
}
