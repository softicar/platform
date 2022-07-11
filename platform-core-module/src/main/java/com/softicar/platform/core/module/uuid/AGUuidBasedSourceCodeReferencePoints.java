package com.softicar.platform.core.module.uuid;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;
import java.util.Collection;
import java.util.stream.Collectors;

public class AGUuidBasedSourceCodeReferencePoints {

	/**
	 * Returns all existing {@link ISourceCodeReferencePoint} instances that
	 * implement or extend the given {@link Class}.
	 *
	 * @param referencePointClass
	 *            the {@link Class} (never <i>null</i>)
	 * @return a {@link Collection} of all existing
	 *         {@link ISourceCodeReferencePoint} instances (never
	 *         <i>null</i>)
	 */
	public static Collection<AGUuidBasedSourceCodeReferencePoint> getAll(Class<? extends ISourceCodeReferencePoint> referencePointClass) {

		return SourceCodeReferencePoints
			.getReferencePoints(referencePointClass)
			.stream()
			.map(AGUuidBasedSourceCodeReferencePoint::new)
			.collect(Collectors.toList());
	}
}
