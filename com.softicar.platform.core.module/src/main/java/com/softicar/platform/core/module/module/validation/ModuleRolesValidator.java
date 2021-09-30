package com.softicar.platform.core.module.module.validation;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.classes.name.matcher.IJavaClassNameMatcher;
import com.softicar.platform.common.core.java.classes.name.matcher.JavaClassNameGlobPatternMatcher;
import com.softicar.platform.common.core.java.code.validator.AbstractJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.emf.authorization.role.EmfAllRoles;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.module.role.EmfDefaultModuleRoles;
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
public class ModuleRolesValidator extends AbstractJavaCodeValidator {

	private static final String ROLES_CLASS_SUFFIX = "Roles";
	private static final String MODULE_CLASS_SUFFIX = "Module";
	private static final IJavaClassNameMatcher ROLES_CLASS_NAME = new JavaClassNameGlobPatternMatcher("**.*" + ROLES_CLASS_SUFFIX);
	private static final Collection<Class<?>> IGNORED_CLASSES = Arrays
		.asList(//
			EmfAllRoles.class,
			EmfRoles.class,
			EmfDefaultModuleRoles.class);

	public ModuleRolesValidator() {

		setClassFilter(javaClass -> ROLES_CLASS_NAME.test(javaClass.getClassName()) && !isIgnored(javaClass));
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

	private void validateIsPublic(Class<?> rolesClass) {

		if (!Modifier.isPublic(rolesClass.getModifiers())) {
			formatViolation(//
				"Roles class must be public: %s",
				rolesClass.getCanonicalName());
		}
	}

	private void validateIsInterface(Class<?> rolesClass) {

		if (!Modifier.isPublic(rolesClass.getModifiers())) {
			formatViolation(//
				"Roles class must be an interface: %s",
				rolesClass.getCanonicalName());
		}
	}

	private void validateHasModule(Class<?> rolesClass) {

		JavaClassName rolesClassName = new JavaClassName(rolesClass);
		String simpleModuleClassName = replaceSuffix(rolesClassName.getSimpleName(), ROLES_CLASS_SUFFIX, MODULE_CLASS_SUFFIX);
		JavaClassName moduleClassName = rolesClassName.getPackageName().getClassName(simpleModuleClassName);

		try {
			Class.forName(moduleClassName.getName());
		} catch (ClassNotFoundException exception) {
			DevNull.swallow(exception);
			formatViolation("Roles class %s has no associated module class: %s", rolesClass.getSimpleName(), moduleClassName);
		}
	}

	private String replaceSuffix(String text, String oldSuffix, String newSuffix) {

		return text.substring(0, text.length() - oldSuffix.length()) + newSuffix;
	}
}
