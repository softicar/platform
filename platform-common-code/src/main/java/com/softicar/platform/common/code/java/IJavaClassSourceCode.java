package com.softicar.platform.common.code.java;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;

/**
 * A class implementing this interface represents the source code of a Java
 * class.
 *
 * @author Oliver Richers
 */
public interface IJavaClassSourceCode {

	/**
	 * Returns the class name of this Java class.
	 *
	 * @return Java class name (never null)
	 */
	JavaClassName getClassName();

	/**
	 * Returns the source code of this Java class as {@link CharSequence}.
	 *
	 * @return the class source code (never null)
	 */
	CharSequence getSourceCode();
}
