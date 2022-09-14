package com.softicar.platform.common.code.validator;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classpath.IJavaClasspathRoot;
import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationException;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.string.Imploder;

@JavaCodeValidator
public class PlatformCodeValidator implements IJavaCodeValidator {

	private JavaCodeValidationEnvironment environment;
	private PlatformCodeValidatorConfiguration configuration;
	private PlatformCodeViolationCollection violations;

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		this.environment = environment;
		this.configuration = new PlatformCodeValidatorConfiguration(environment.getConfigurationJsonValueReader());
		this.violations = new PlatformCodeViolationCollection();

		validateClasses();
		throwExceptionIfViolationsExists();
	}

	private void validateClasses() {

		JavaClasspath//
			.getInstance()
			.getPayloadRoots()
			.forEach(this::validateRoot);
	}

	private void validateRoot(IJavaClasspathRoot root) {

		root//
			.getAnalyzedClasses()
			.stream()
			.forEach(this::validateClass);
	}

	public void validateClass(AnalyzedJavaClass javaClass) {

		checkForMainMethod(javaClass);
		checkForForbiddenClasses(javaClass);
	}

	private void checkForMainMethod(AnalyzedJavaClass javaClass) {

		if (javaClass.hasMainMethod()) {
			if (!configuration.isAllowedToHaveMainMethod(javaClass.getClassName())) {
				violations.addClassHasMainMethodViolation(javaClass.getClassName());
			} else {
				environment.logVerbose("Class with allowed main method: %s", javaClass.getClassName());
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
