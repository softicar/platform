package com.softicar.platform.common.core.constant.container.validator;

import com.softicar.platform.common.core.constant.container.validator.error.IConstantContainerValidationError;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClassFetcher;
import com.softicar.platform.common.core.java.classpath.IJavaClasspathRoot;
import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationException;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Basic {@link IJavaCodeValidator} for "constant container" classes.
 * <p>
 * Checks all classes below all {@link JavaClasspath} root folders.
 *
 * @param <T>
 *            the expected type of the fields in the constant container class
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class AbstractConstantContainersValidator<T> implements IJavaCodeValidator {

	private ConstantContainerValidatorResult<T> result;
	private final Class<T> containerClass;
	private JavaCodeValidationEnvironment environment;

	public AbstractConstantContainersValidator(Class<T> containerClass) {

		this.result = null;
		this.containerClass = Objects.requireNonNull(containerClass);
	}

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		this.environment = environment;
		this.result = new ConstantContainerValidatorResult<>();

		prepareForEnvironment(environment);

		environment//
			.getClassPath()
			.getRootFolders()
			.forEach(this::validate);

		if (result.getErrors().isEmpty()) {
			environment.logVerbose("Validation of all %s container classes successful.", containerClass.getSimpleName());
		} else {
			throw new JavaCodeValidationException(//
				"Validation of %s container class(es) failed:\n\t%s",
				containerClass.getSimpleName(),
				result//
					.getErrors()
					.stream()
					.map(IConstantContainerValidationError::getMessage)
					.collect(Collectors.joining("\n\t")));
		}
	}

	protected abstract AnalyzedJavaClassFetcher createClassFetcher();

	protected abstract IConstantContainerValidator<T> createValidator(Class<?> containerClass);

	protected abstract void prepareForEnvironment(JavaCodeValidationEnvironment environment);

	private void validate(IJavaClasspathRoot classpathRoot) {

		createClassFetcher()//
			.fetchJavaClasses(classpathRoot)
			.forEach(this::validate);
	}

	private void validate(AnalyzedJavaClass javaClass) {

		try {
			environment.logVerbose("Validating %s container class: %s", containerClass.getSimpleName(), javaClass.getClassName());
			createValidator(javaClass.loadClass()).validate(result);
		} catch (ClassNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
}
