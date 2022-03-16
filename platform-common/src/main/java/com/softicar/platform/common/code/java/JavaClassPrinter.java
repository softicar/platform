package com.softicar.platform.common.code.java;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;

/**
 * Extended version of {@link JavaCodePrinter} implementing
 * {@link IJavaClassSourceCode}.
 *
 * @author Oliver Richers
 */
public class JavaClassPrinter extends JavaCodePrinter implements IJavaClassSourceCode {

	private final JavaClassName className;

	public JavaClassPrinter(JavaClassName className) {

		this.className = className;
	}

	public JavaClassPrinter(String className) {

		this(new JavaClassName(className));
	}

	@Override
	public JavaClassName getClassName() {

		return className;
	}

	@Override
	public String getSourceCode() {

		return toString(className.getPackageName());
	}
}
