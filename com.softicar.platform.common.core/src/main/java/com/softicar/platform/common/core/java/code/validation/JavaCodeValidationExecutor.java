package com.softicar.platform.common.core.java.code.validation;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidatorClassesFinder;
import com.softicar.platform.common.core.java.stack.trace.JavaStackTraces;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class JavaCodeValidationExecutor {

	private final JavaCodeValidationEnvironment environment;
	private final Collection<Throwable> throwables;

	public JavaCodeValidationExecutor(JavaCodeValidationEnvironment environment) {

		this.environment = environment;
		this.throwables = new ArrayList<>();
	}

	public void execute() {

		environment.logVerbose("Looking for code validator classes.");
		Collection<AnalyzedJavaClass> validatorClasses = new JavaCodeValidatorClassesFinder(environment).findAll();
		if (validatorClasses.isEmpty()) {
			environment.logVerbose("No code validator classes found.");
		} else {
			validatorClasses.forEach(this::executeValidatorClass);
		}

		if (!throwables.isEmpty()) {
			throw new JavaCodeValidationException(
				"Code validation failure:\n%s",
				throwables//
					.stream()
					.map(this::getMessage)
					.collect(Collectors.joining("\n")));
		}
	}

	private void executeValidatorClass(AnalyzedJavaClass annotatedClass) {

		environment.logVerbose("Executing validator class: %s", annotatedClass.getClassName());
		try {
			Class<?> validatorClass = Class.forName(annotatedClass.getClassName().getName());
			IJavaCodeValidator validator = (IJavaCodeValidator) validatorClass.getConstructor().newInstance();
			validator.validate(environment);
		} catch (Throwable throwable) {
			throwables.add(throwable);
		}
	}

	private String getMessage(Throwable throwable) {

		if (throwable instanceof JavaCodeValidationException) {
			return throwable.getMessage();
		} else {
			return "Unexpected exception:\n" + JavaStackTraces.getStackTraceAsString(throwable);
		}
	}
}
