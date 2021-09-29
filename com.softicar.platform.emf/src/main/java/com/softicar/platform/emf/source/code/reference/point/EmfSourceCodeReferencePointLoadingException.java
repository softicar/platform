package com.softicar.platform.emf.source.code.reference.point;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;

/**
 * This exception is thrown if the instantiation of an
 * {@link IEmfSourceCodeReferencePoint} fails.
 *
 * @author Oliver Richers
 */
public class EmfSourceCodeReferencePointLoadingException extends RuntimeException {

	public EmfSourceCodeReferencePointLoadingException(JavaClassName className, Exception cause) {

		super(String.format("Failed to load %s class: %s", IEmfSourceCodeReferencePoint.class.getSimpleName(), className), cause);
	}
}
