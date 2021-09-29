package com.softicar.platform.emf.source.code.reference.point;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;

/**
 * This exception is thrown if the class of a given
 * {@link IEmfSourceCodeReferencePoint} is missing the
 * {@link EmfSourceCodeReferencePointUuid} annotation.
 *
 * @author Oliver Richers
 */
public class EmfSourceCodeReferencePointMissingUuidAnnotationException extends SofticarDeveloperException {

	public EmfSourceCodeReferencePointMissingUuidAnnotationException(Class<?> referencePointClass) {

		super(//
			"Missing %s annotation on class: %s",
			EmfSourceCodeReferencePointUuid.class.getSimpleName(),
			referencePointClass.getCanonicalName());
	}
}
