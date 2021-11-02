package com.softicar.platform.core.module.module.validation;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;

public class ModuleCodeClassValidator {

	private final ModuleCodeValidatorConfiguration configuration;
	private final ModuleCodeViolationCollection violations;
	private AnalyzedJavaClass javaClass;
	private JavaClassName className;

	public ModuleCodeClassValidator() {

		this.configuration = new ModuleCodeValidatorConfiguration();
		this.violations = new ModuleCodeViolationCollection();
	}

	public void validate(AnalyzedJavaClass javaClass) {

		this.javaClass = javaClass;
		this.className = javaClass.getClassName();

		checkForMainMethod();
		checkForForbiddenClasses();
	}

	public ModuleCodeViolationCollection getViolations() {

		return violations;
	}

	private void checkForMainMethod() {

		if (!configuration.isAllowedToHaveMainMethod(className)) {
			if (javaClass.hasMainMethod()) {
				violations.addClassHasMainMethodViolation(className);
			}
		}
	}

	private void checkForForbiddenClasses() {

		if (!configuration.isAllowedToUseForbiddenClasses(className)) {
			javaClass//
				.getReferencedClasses()
				.stream()
				.filter(configuration::isForbiddenClass)
				.forEach(forbiddenClass -> violations.addClassUsesForbiddenClassViolation(className, forbiddenClass));
		}
	}
}
