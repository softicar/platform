package com.softicar.platform.core.module.module.validation;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.classes.name.matcher.IJavaClassNameMatcher;
import com.softicar.platform.common.core.java.classes.name.matcher.JavaClassNameGlobPatternMatcher;
import com.softicar.platform.common.core.java.code.validator.AbstractJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.core.module.module.AbstractStandardModule;
import com.softicar.platform.core.module.module.AbstractSystemModule;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.Arrays;
import java.util.Collection;

/**
 * Ensures a valid source layout and a valid structural composition of extracted
 * modules.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@JavaCodeValidator
public class ModuleClassesValidator extends AbstractJavaCodeValidator {

	private static final Collection<Class<?>> LEGAL_SUPER_TYPES = Arrays
		.asList(//
			AbstractStandardModule.class,
			AbstractSystemModule.class);
	private static final IJavaClassNameMatcher VALID_CLASS_NAME = new JavaClassNameGlobPatternMatcher("com.softicar.**.module.*Module");

	public ModuleClassesValidator() {

		setClassFilter(javaClass -> hasAnnotation(javaClass, EmfSourceCodeReferencePointUuid.class));
		addClassValidator(this::validateModuleSuperType);
		addClassValidator(this::validateModuleClassName);
	}

	// -------------------------------- class super type -------------------------------- //

	private void validateModuleSuperType(Class<?> javaClass) {

		if (isModuleClass(javaClass) && !hasLegalSuperType(javaClass)) {
			formatViolation(
				"Module '%s' needs to extend one of the following types: %s",
				javaClass.getCanonicalName(),
				Imploder.implode(LEGAL_SUPER_TYPES, it -> it.getSimpleName(), ", "));
		}
	}

	private boolean hasLegalSuperType(Class<?> moduleClass) {

		return LEGAL_SUPER_TYPES.stream().anyMatch(superType -> superType.isAssignableFrom(moduleClass));
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
}
