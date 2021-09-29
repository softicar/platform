package com.softicar.platform.emf.source.code.reference.point;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.utils.CastUtils;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Utility methods for classes implementing
 * {@link IEmfSourceCodeReferencePoint}.
 *
 * @author Oliver Richers
 */
public class EmfSourceCodeReferencePoints {

	/**
	 * Returns the {@link UUID} of the given
	 * {@link IEmfSourceCodeReferencePoint} class from the
	 * {@link EmfSourceCodeReferencePointUuid} annotation.
	 *
	 * @param referencePointClass
	 *            the class of the reference point (never <i>null</i>)
	 * @return the {@link UUID} (never <i>null</i>)
	 * @throws EmfSourceCodeReferencePointMissingUuidAnnotationException
	 *             if the given {@link Class} is missing an
	 *             {@link EmfSourceCodeReferencePointUuid} annotation
	 */
	public static UUID getUuidOrThrow(Class<?> referencePointClass) {

		EmfSourceCodeReferencePointUuid uuidAnnotation = referencePointClass.getAnnotation(EmfSourceCodeReferencePointUuid.class);
		if (uuidAnnotation != null) {
			return UUID.fromString(uuidAnnotation.value());
		} else {
			throw new EmfSourceCodeReferencePointMissingUuidAnnotationException(referencePointClass);
		}
	}

	/**
	 * Returns the {@link IEmfSourceCodeReferencePoint} instance of the given
	 * {@link Class}.
	 *
	 * @param referencePointClass
	 *            the {@link Class} of the {@link IEmfSourceCodeReferencePoint}
	 *            (never <i>null</i>)
	 * @return the {@link IEmfSourceCodeReferencePoint} instance (never
	 *         <i>null</i>)
	 * @throws EmfSourceCodeReferencePointMissingUuidAnnotationException
	 *             if the given {@link Class} is missing an
	 *             {@link EmfSourceCodeReferencePointUuid} annotation
	 * @throws EmfSourceCodeReferencePointMissingException
	 *             if no matching {@link IEmfSourceCodeReferencePoint} was found
	 */
	public static <T extends IEmfSourceCodeReferencePoint> T getReferencePoint(Class<T> referencePointClass) {

		return getReferencePointOrThrow(getUuidOrThrow(referencePointClass), referencePointClass);
	}

	/**
	 * Returns the {@link IEmfSourceCodeReferencePoint} matching the given
	 * {@link UUID}.
	 *
	 * @param uuid
	 *            the {@link UUID} (never <i>null</i>)
	 * @return the {@link IEmfSourceCodeReferencePoint} as {@link Optional}
	 */
	public static Optional<IEmfSourceCodeReferencePoint> getReferencePoint(UUID uuid) {

		return EmfSourceCodeReferencePointRegistry//
			.getInstance()
			.getReferencePoint(uuid);
	}

	/**
	 * Returns the {@link IEmfSourceCodeReferencePoint} matching the given
	 * {@link UUID}.
	 *
	 * @param uuid
	 *            the {@link UUID} (never <i>null</i>)
	 * @return the {@link IEmfSourceCodeReferencePoint} (never <i>null</i>)
	 * @throws EmfSourceCodeReferencePointMissingException
	 *             if no matching {@link IEmfSourceCodeReferencePoint} was found
	 */
	public static IEmfSourceCodeReferencePoint getReferencePointOrThrow(UUID uuid) {

		return getReferencePoint(uuid)//
			.orElseThrow(() -> new EmfSourceCodeReferencePointMissingException(uuid));
	}

	/**
	 * Returns the {@link IEmfSourceCodeReferencePoint} matching the given
	 * {@link UUID} and implementing the given class.
	 *
	 * @param uuid
	 *            the {@link UUID} (never <i>null</i>)
	 * @param targetClass
	 *            the class to implement (never <i>null</i>)
	 * @return the {@link IEmfSourceCodeReferencePoint} (never <i>null</i>)
	 * @throws EmfSourceCodeReferencePointMissingException
	 *             if no matching {@link IEmfSourceCodeReferencePoint} was found
	 */
	public static <T> T getReferencePointOrThrow(UUID uuid, Class<T> targetClass) {

		IEmfSourceCodeReferencePoint referencePoint = getReferencePointOrThrow(uuid);

		return CastUtils//
			.tryCast(referencePoint, targetClass)
			.orElseThrow(
				() -> new SofticarDeveloperException(//
					"%s does not implement %s",
					IEmfSourceCodeReferencePoint.class.getSimpleName(),
					targetClass.getCanonicalName()));
	}

	/**
	 * Returns all {@link IEmfSourceCodeReferencePoint}s implementing the given
	 * given class.
	 *
	 * @param targetClass
	 *            the class to implement (never <i>null</i>)
	 * @return all matching {@link IEmfSourceCodeReferencePoint}s (never
	 *         <i>null</i>)
	 */
	public static <T> Collection<T> getReferencePoints(Class<T> targetClass) {

		return EmfSourceCodeReferencePointRegistry//
			.getInstance()
			.getReferencePoints(targetClass);
	}
}
