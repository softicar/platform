package com.softicar.platform.common.code.reference.point;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;

/**
 * This exception is thrown if the class of a given
 * {@link ISourceCodeReferencePoint} is missing the
 * {@link SourceCodeReferencePointUuid} annotation.
 *
 * @author Oliver Richers
 */
public class SourceCodeReferencePointMissingUuidAnnotationException extends SofticarDeveloperException {

	public SourceCodeReferencePointMissingUuidAnnotationException(Class<?> referencePointClass) {

		super(//
			"Missing %s annotation on class: %s",
			SourceCodeReferencePointUuid.class.getSimpleName(),
			referencePointClass.getCanonicalName());
	}
}
