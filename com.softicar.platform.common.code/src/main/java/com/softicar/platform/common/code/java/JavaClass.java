package com.softicar.platform.common.code.java;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.Collection;
import java.util.Set;

public class JavaClass {

	private final JavaClassName name;
	private final JavaImports imports = new JavaImports();

	public JavaClass(JavaClassName name) {

		this.name = name;
	}

	public JavaClass(Class<?> theClass) {

		this.name = new JavaClassName(theClass);
	}

	public JavaClassName getName() {

		return name;
	}

	public String getCanonicalName() {

		return name.getCanonicalName();
	}

	public String getSimpleName() {

		return name.getSimpleName();
	}

	public Set<JavaClassName> getImports() {

		return imports.getImports();
	}

	public JavaClass addImport(Class<?> javaClass) {

		imports.addImport(javaClass);
		return this;
	}

	public JavaClass addImport(JavaClassName className) {

		imports.addImport(className);
		return this;
	}

	public JavaClass addImports(Collection<Class<?>> classes) {

		imports.addImports(classes);
		return this;
	}
}
