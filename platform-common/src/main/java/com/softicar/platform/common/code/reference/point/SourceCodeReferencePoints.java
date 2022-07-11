package com.softicar.platform.common.code.reference.point;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.utils.CastUtils;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Utility methods for classes implementing
 * {@link ISourceCodeReferencePoint}.
 *
 * @author Oliver Richers
 */
public class SourceCodeReferencePoints {

	/**
	 * Returns the {@link UUID} of the given
	 * {@link ISourceCodeReferencePoint} class from the
	 * {@link SourceCodeReferencePointUuid} annotation.
	 *
	 * @param referencePointClass
	 *            the class of the reference point (never <i>null</i>)
	 * @return the {@link UUID} (never <i>null</i>)
	 * @throws SourceCodeReferencePointMissingUuidAnnotationException
	 *             if the given {@link Class} is missing an
	 *             {@link SourceCodeReferencePointUuid} annotation
	 */
	public static UUID getUuidOrThrow(Class<?> referencePointClass) {

		SourceCodeReferencePointUuid uuidAnnotation = referencePointClass.getAnnotation(SourceCodeReferencePointUuid.class);
		if (uuidAnnotation != null) {
			return UUID.fromString(uuidAnnotation.value());
		} else {
			throw new SourceCodeReferencePointMissingUuidAnnotationException(referencePointClass);
		}
	}

	/**
	 * Returns the {@link ISourceCodeReferencePoint} instance of the given
	 * {@link Class}.
	 *
	 * @param referencePointClass
	 *            the {@link Class} of the {@link ISourceCodeReferencePoint}
	 *            (never <i>null</i>)
	 * @return the {@link ISourceCodeReferencePoint} instance (never
	 *         <i>null</i>)
	 * @throws SourceCodeReferencePointMissingUuidAnnotationException
	 *             if the given {@link Class} is missing an
	 *             {@link SourceCodeReferencePointUuid} annotation
	 * @throws SourceCodeReferencePointMissingException
	 *             if no matching {@link ISourceCodeReferencePoint} was found
	 */
	public static <T extends ISourceCodeReferencePoint> T getReferencePoint(Class<T> referencePointClass) {

		return getReferencePointOrThrow(getUuidOrThrow(referencePointClass), referencePointClass);
	}

	/**
	 * Returns the {@link ISourceCodeReferencePoint} matching the given
	 * {@link UUID}.
	 *
	 * @param uuid
	 *            the {@link UUID} (never <i>null</i>)
	 * @return the {@link ISourceCodeReferencePoint} as {@link Optional}
	 */
	public static Optional<ISourceCodeReferencePoint> getReferencePoint(UUID uuid) {

		return SourceCodeReferencePointRegistry//
			.getInstance()
			.getReferencePoint(uuid);
	}

	/**
	 * Returns the {@link ISourceCodeReferencePoint} matching the given
	 * {@link UUID}.
	 *
	 * @param uuid
	 *            the {@link UUID} (never <i>null</i>)
	 * @return the {@link ISourceCodeReferencePoint} (never <i>null</i>)
	 * @throws SourceCodeReferencePointMissingException
	 *             if no matching {@link ISourceCodeReferencePoint} was found
	 */
	public static ISourceCodeReferencePoint getReferencePointOrThrow(UUID uuid) {

		return getReferencePoint(uuid)//
			.orElseThrow(() -> new SourceCodeReferencePointMissingException(uuid));
	}

	/**
	 * Returns the {@link ISourceCodeReferencePoint} matching the given
	 * {@link UUID} and implementing the given class.
	 *
	 * @param uuid
	 *            the {@link UUID} (never <i>null</i>)
	 * @param targetClass
	 *            the class to implement (never <i>null</i>)
	 * @return the {@link ISourceCodeReferencePoint} (never <i>null</i>)
	 * @throws SourceCodeReferencePointMissingException
	 *             if no matching {@link ISourceCodeReferencePoint} was found
	 */
	public static <T> T getReferencePointOrThrow(UUID uuid, Class<T> targetClass) {

		ISourceCodeReferencePoint referencePoint = getReferencePointOrThrow(uuid);

		return CastUtils//
			.tryCast(referencePoint, targetClass)
			.orElseThrow(
				() -> new SofticarDeveloperException(//
					"%s does not implement %s",
					ISourceCodeReferencePoint.class.getSimpleName(),
					targetClass.getCanonicalName()));
	}

	/**
	 * Returns all {@link ISourceCodeReferencePoint}s implementing the given
	 * given class.
	 *
	 * @param targetClass
	 *            the class to implement (never <i>null</i>)
	 * @return all matching {@link ISourceCodeReferencePoint}s (never
	 *         <i>null</i>)
	 */
	public static <T> Collection<T> getReferencePoints(Class<T> targetClass) {

		return SourceCodeReferencePointRegistry//
			.getInstance()
			.getReferencePoints(targetClass);
	}
}
