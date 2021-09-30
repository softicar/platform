package com.softicar.platform.core.module.module.validation;

import com.softicar.platform.common.code.java.JavaPackageTree;
import com.softicar.platform.common.code.java.JavaPackageTreeSet;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.Set;
import java.util.TreeSet;

public class ModuleCodeValidatorConfiguration {

	private final Set<JavaClassName> classesAllowedToHaveMainMethod;
	private final Set<JavaClassName> forbiddenClasses;
	private final JavaPackageTreeSet packageTreesAllowedToUseForbiddenClasses;

	// TODO the whole configuration must be extracted into the build configuration
	public ModuleCodeValidatorConfiguration() {

		this.classesAllowedToHaveMainMethod = new TreeSet<>();
		this.forbiddenClasses = new TreeSet<>();
		this.packageTreesAllowedToUseForbiddenClasses = new JavaPackageTreeSet();

		allowClassToHaveMainMethod("com.softicar.platform.core.module.program.ProgramStarter");

		addForbiddenClass("com.softicar.platform.db.core.statement.DbStatement");
		addForbiddenClass("java.sql.Connection");
		addForbiddenClass("java.sql.Driver");
		addForbiddenClass("java.sql.DriverManager");
		addForbiddenClass("java.sql.ResultSet");
		addForbiddenClass("java.sql.ResultSetMetaData");
		addForbiddenClass("java.sql.Statement");

		// FIXME this references code of a dependent project
		allowPackageTreeToUseForbiddenClasses("com.softicar.eas.database.administration.module");
	}

	public boolean isAllowedToHaveMainMethod(JavaClassName className) {

		return classesAllowedToHaveMainMethod.contains(className);
	}

	public boolean isForbiddenClass(JavaClassName className) {

		return forbiddenClasses.contains(className);
	}

	public boolean isAllowedToUseForbiddenClasses(JavaClassName className) {

		return packageTreesAllowedToUseForbiddenClasses.contains(className);
	}

	private void allowClassToHaveMainMethod(String className) {

		classesAllowedToHaveMainMethod.add(new JavaClassName(className));
	}

	private void addForbiddenClass(String className) {

		forbiddenClasses.add(new JavaClassName(className));
	}

	private void allowPackageTreeToUseForbiddenClasses(String rootPackageName) {

		packageTreesAllowedToUseForbiddenClasses.add(new JavaPackageTree(rootPackageName));
	}
}
