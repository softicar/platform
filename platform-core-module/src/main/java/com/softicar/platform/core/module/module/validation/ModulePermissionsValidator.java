package com.softicar.platform.core.module.module.validation;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.classes.name.matcher.IJavaClassNameMatcher;
import com.softicar.platform.common.core.java.classes.name.matcher.JavaClassNameGlobPatternMatcher;
import com.softicar.platform.common.core.java.code.validator.AbstractJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;
import com.softicar.platform.emf.permission.EmfAllPermissions;
import com.softicar.platform.emf.permission.EmfPermissions;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;

/**
 * Ensures a valid source layout and a valid structural composition of extracted
 * modules.
 * <p>
 * TODO move to `com.softicar.module.system.module.validation`
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@JavaCodeValidator
public class ModulePermissionsValidator extends AbstractJavaCodeValidator {

	private static final String PERMISSIONS_CLASS_SUFFIX = "Permissions";
	private static final String MODULE_CLASS_SUFFIX = "Module";
	private static final IJavaClassNameMatcher PERMISSIONS_CLASS_NAME = new JavaClassNameGlobPatternMatcher("**.*" + PERMISSIONS_CLASS_SUFFIX);
	private static final Collection<Class<?>> IGNORED_CLASSES = Arrays
		.asList(//
			EmfAllPermissions.class,
			EmfPermissions.class,
			EmfDefaultModulePermissions.class);

	public ModulePermissionsValidator() {

		setClassFilter(javaClass -> PERMISSIONS_CLASS_NAME.test(javaClass.getClassName()) && !isIgnored(javaClass));
		addClassValidator(this::validateIsPublic);
		addClassValidator(this::validateIsInterface);
		addClassValidator(this::validateHasModule);
	}

	private boolean isIgnored(AnalyzedJavaClass javaClass) {

		return IGNORED_CLASSES//
			.stream()
			.map(JavaClassName::new)
			.anyMatch(javaClass.getClassName()::equals);
	}

	private void validateIsPublic(Class<?> permissionsClass) {

		if (!Modifier.isPublic(permissionsClass.getModifiers())) {
			formatViolation(//
				"Permissions class must be public: %s",
				permissionsClass.getCanonicalName());
		}
	}

	private void validateIsInterface(Class<?> permissionsClass) {

		if (!Modifier.isPublic(permissionsClass.getModifiers())) {
			formatViolation(//
				"Permissions class must be an interface: %s",
				permissionsClass.getCanonicalName());
		}
	}

	private void validateHasModule(Class<?> permissionsClass) {

		JavaClassName permissionsClassName = new JavaClassName(permissionsClass);
		String simpleModuleClassName = replaceSuffix(permissionsClassName.getSimpleName(), PERMISSIONS_CLASS_SUFFIX, MODULE_CLASS_SUFFIX);
		JavaClassName moduleClassName = permissionsClassName.getPackageName().getClassName(simpleModuleClassName);

		try {
			Class.forName(moduleClassName.getName());
		} catch (ClassNotFoundException exception) {
			DevNull.swallow(exception);
			formatViolation("Permissions class %s has no associated module class: %s", permissionsClass.getSimpleName(), moduleClassName);
		}
	}

	private String replaceSuffix(String text, String oldSuffix, String newSuffix) {

		return text.substring(0, text.length() - oldSuffix.length()) + newSuffix;
	}
}
