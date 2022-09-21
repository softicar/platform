package com.softicar.platform.core.module.module.validation;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.classes.name.matcher.IJavaClassNameMatcher;
import com.softicar.platform.common.core.java.classes.name.matcher.JavaClassNameGlobPatternMatcher;
import com.softicar.platform.common.core.java.code.validator.AbstractJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.core.module.module.AbstractModule;
import com.softicar.platform.emf.module.IEmfModule;

/**
 * Ensures a valid source layout and a valid structural composition of extracted
 * modules.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@JavaCodeValidator
public class ModuleClassesValidator extends AbstractJavaCodeValidator {

	private static final IJavaClassNameMatcher VALID_CLASS_NAME = new JavaClassNameGlobPatternMatcher("**.module.*Module");

	public ModuleClassesValidator() {

		setClassFilter(this::isSourceCodeReferencePointAndNotTestingOnly);
		addClassValidator(this::validateModuleSuperType);
		addClassValidator(this::validateModuleClassName);
	}

	// -------------------------------- class super type -------------------------------- //

	private void validateModuleSuperType(Class<?> javaClass) {

		if (isModuleClass(javaClass) && !AbstractModule.class.isAssignableFrom(javaClass)) {
			formatViolation(//
				"Module '%s' needs to extend the type: %s",
				javaClass.getCanonicalName(),
				AbstractModule.class.getSimpleName());
		}
	}

	// -------------------------------- class name -------------------------------- //

	private void validateModuleClassName(Class<?> javaClass) {

		if (isModuleClass(javaClass) && !hasValidClassName(javaClass)) {
			formatViolation(//
				"Module class name does not match expected pattern '%s': %s",
				VALID_CLASS_NAME,
				javaClass.getCanonicalName());
		}
	}

	private boolean hasValidClassName(Class<?> moduleClass) {

		return VALID_CLASS_NAME.test(new JavaClassName(moduleClass));
	}

	// -------------------------------- auxiliary -------------------------------- //

	private boolean isModuleClass(Class<?> javaClass) {

		return IEmfModule.class.isAssignableFrom(javaClass);
	}

	private boolean isSourceCodeReferencePointAndNotTestingOnly(AnalyzedJavaClass javaClass) {

		return hasAnnotation(javaClass, SourceCodeReferencePointUuid.class) && !hasAnnotation(javaClass, TestingOnly.class);
	}
}
