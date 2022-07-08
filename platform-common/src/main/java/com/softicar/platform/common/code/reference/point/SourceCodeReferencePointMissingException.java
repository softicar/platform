package com.softicar.platform.common.code.reference.point;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.UUID;

/**
 * This exception is thrown if no {@link ISourceCodeReferencePoint} can be
 * found for a given {@link UUID}.
 *
 * @author Oliver Richers
 */
public class SourceCodeReferencePointMissingException extends SofticarDeveloperException {

	public SourceCodeReferencePointMissingException(UUID uuid) {

		super(//
			"Missing %s for UUID: %s",
			ISourceCodeReferencePoint.class.getSimpleName(),
			uuid);
	}
}
