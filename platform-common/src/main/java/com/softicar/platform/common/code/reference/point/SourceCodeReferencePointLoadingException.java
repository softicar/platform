package com.softicar.platform.common.code.reference.point;

/**
 * This exception is thrown if the instantiation of an
 * {@link ISourceCodeReferencePoint} fails.
 *
 * @author Oliver Richers
 */
public class SourceCodeReferencePointLoadingException extends RuntimeException {

	public SourceCodeReferencePointLoadingException(Class<?> referencePointClass, Exception cause) {

		super("Failed to load %s class: %s".formatted(ISourceCodeReferencePoint.class.getSimpleName(), referencePointClass.getCanonicalName()), cause);
	}
}
