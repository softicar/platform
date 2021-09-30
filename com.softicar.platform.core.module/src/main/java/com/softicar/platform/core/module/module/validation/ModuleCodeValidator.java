package com.softicar.platform.core.module.module.validation;

import com.softicar.platform.common.core.java.classpath.IJavaClasspathRoot;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationException;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.string.Imploder;

@JavaCodeValidator
public class ModuleCodeValidator implements IJavaCodeValidator {

	private final ModuleCodeClassValidator classValidator;

	public ModuleCodeValidator() {

		this.classValidator = new ModuleCodeClassValidator();
	}

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		validateClassesInRootFolders(environment);
		throwExceptionIfViolationsExists();
	}

	private void validateClassesInRootFolders(JavaCodeValidationEnvironment environment) {

		environment.getClassPath().getRootFolders().forEach(this::validate);
	}

	private void validate(IJavaClasspathRoot rootFolder) {

		rootFolder//
			.getAnalyzedClasses()
			.stream()
			.forEach(classValidator::validate);
	}

	private void throwExceptionIfViolationsExists() {

		ModuleCodeViolationCollection violations = classValidator.getViolations();
		if (!violations.isEmpty()) {
			throw new JavaCodeValidationException(//
				"Module code violations:\n\t%s",
				Imploder.implode(violations.getMessages(), "\n\t"));
		}
	}
}
