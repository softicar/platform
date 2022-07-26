package com.softicar.platform.common.code.validator;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classpath.IJavaClasspathRoot;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationException;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.string.Imploder;

@JavaCodeValidator
public class PlatformCodeValidator implements IJavaCodeValidator {

	private PlatformCodeValidatorConfiguration configuration;
	private PlatformCodeViolationCollection violations;

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		this.configuration = new PlatformCodeValidatorConfiguration(environment.getConfigurationJsonValueReader());
		this.violations = new PlatformCodeViolationCollection();

		validateClassesInRootFolders(environment);
		throwExceptionIfViolationsExists();
	}

	private void validateClassesInRootFolders(JavaCodeValidationEnvironment environment) {

		environment.getClassPath().getRootFolders().forEach(this::validateRootFolder);
	}

	private void validateRootFolder(IJavaClasspathRoot rootFolder) {

		rootFolder//
			.getAnalyzedClasses()
			.stream()
			.forEach(this::validateClass);
	}

	public void validateClass(AnalyzedJavaClass javaClass) {

		checkForMainMethod(javaClass);
		checkForForbiddenClasses(javaClass);
	}

	private void checkForMainMethod(AnalyzedJavaClass javaClass) {

		if (!configuration.isAllowedToHaveMainMethod(javaClass.getClassName())) {
			if (javaClass.hasMainMethod()) {
				violations.addClassHasMainMethodViolation(javaClass.getClassName());
			}
		}
	}

	private void checkForForbiddenClasses(AnalyzedJavaClass javaClass) {

		if (!configuration.isAllowedToUseForbiddenClasses(javaClass.getClassName())) {
			javaClass//
				.getReferencedClasses()
				.stream()
				.filter(configuration::isForbiddenClass)
				.forEach(forbiddenClass -> violations.addClassUsesForbiddenClassViolation(javaClass.getClassName(), forbiddenClass));
		}
	}

	private void throwExceptionIfViolationsExists() {

		if (!violations.isEmpty()) {
			throw new JavaCodeValidationException(//
				"Platform code violations:\n\t%s",
				Imploder.implode(violations.getMessages(), "\n\t"));
		}
	}
}
