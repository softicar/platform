package com.softicar.platform.common.code.reference.point;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;

/**
 * This exception is thrown if the instantiation of an
 * {@link ISourceCodeReferencePoint} fails.
 *
 * @author Oliver Richers
 */
public class SourceCodeReferencePointLoadingException extends RuntimeException {

	public SourceCodeReferencePointLoadingException(JavaClassName className, Exception cause) {

		super(String.format("Failed to load %s class: %s", ISourceCodeReferencePoint.class.getSimpleName(), className), cause);
	}
}
