package com.softicar.platform.common.code.validator;

import com.softicar.platform.common.code.java.JavaPackageTree;
import com.softicar.platform.common.code.java.JavaPackageTreeSet;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.io.serialization.json.JsonValueReader;
import java.util.Set;
import java.util.TreeSet;

class PlatformCodeValidatorConfiguration {

	private final Set<JavaClassName> classesAllowedToHaveMainMethod;
	private final Set<JavaClassName> forbiddenClasses;
	private final JavaPackageTreeSet packageTreesAllowedToUseForbiddenClasses;

	public PlatformCodeValidatorConfiguration(JsonValueReader configurationReader) {

		this.classesAllowedToHaveMainMethod = new TreeSet<>();
		this.forbiddenClasses = new TreeSet<>();
		this.packageTreesAllowedToUseForbiddenClasses = new JavaPackageTreeSet();

		configurationReader//
			.readList("classesAllowedToHaveMainMethod")
			.forEach(this::allowClassToHaveMainMethod);

		configurationReader//
			.readList("forbiddenClasses")
			.forEach(this::addForbiddenClass);

		configurationReader//
			.readList("packageTreesAllowedToUseForbiddenClasses")
			.forEach(this::allowPackageTreeToUseForbiddenClasses);
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
