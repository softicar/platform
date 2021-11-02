package com.softicar.platform.emf.source.code.reference.point;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.UUID;

/**
 * This exception is thrown if no {@link IEmfSourceCodeReferencePoint} can be
 * found for a given {@link UUID}.
 *
 * @author Oliver Richers
 */
public class EmfSourceCodeReferencePointMissingException extends SofticarDeveloperException {

	public EmfSourceCodeReferencePointMissingException(UUID uuid) {

		super(//
			"Missing %s for UUID: %s",
			IEmfSourceCodeReferencePoint.class.getSimpleName(),
			uuid);
	}
}
