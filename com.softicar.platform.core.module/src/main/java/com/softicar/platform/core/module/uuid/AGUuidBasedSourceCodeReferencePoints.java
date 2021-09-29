package com.softicar.platform.core.module.uuid;

import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;
import java.util.Collection;
import java.util.stream.Collectors;

public class AGUuidBasedSourceCodeReferencePoints {

	/**
	 * Returns all existing {@link IEmfSourceCodeReferencePoint} instances that
	 * implement or extend the given {@link Class}.
	 *
	 * @param referencePointClass
	 *            the {@link Class} (never <i>null</i>)
	 * @return a {@link Collection} of all existing
	 *         {@link IEmfSourceCodeReferencePoint} instances (never
	 *         <i>null</i>)
	 */
	public static Collection<AGUuidBasedSourceCodeReferencePoint> getAll(Class<? extends IEmfSourceCodeReferencePoint> referencePointClass) {

		return EmfSourceCodeReferencePoints
			.getReferencePoints(referencePointClass)
			.stream()
			.map(AGUuidBasedSourceCodeReferencePoint::new)
			.collect(Collectors.toList());
	}
}
