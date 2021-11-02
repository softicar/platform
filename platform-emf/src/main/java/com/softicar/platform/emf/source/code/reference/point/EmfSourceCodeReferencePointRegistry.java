package com.softicar.platform.emf.source.code.reference.point;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Holds all existing {@link IEmfSourceCodeReferencePoint} instances.
 *
 * @author Oliver Richers
 */
class EmfSourceCodeReferencePointRegistry {

	private static final EmfSourceCodeReferencePointRegistry INSTANCE = new EmfSourceCodeReferencePointRegistry();
	private final Map<UUID, IEmfSourceCodeReferencePoint> referencePoints;

	public static EmfSourceCodeReferencePointRegistry getInstance() {

		return INSTANCE;
	}

	public EmfSourceCodeReferencePointRegistry() {

		this.referencePoints = new EmfSourceCodeReferencePointsLoader().loadAll();
	}

	/**
	 * Returns the matching {@link IEmfSourceCodeReferencePoint} for the given
	 * {@link UUID}.
	 *
	 * @param uuid
	 *            the {@link UUID} (never <i>null</i>)
	 * @return the {@link IEmfSourceCodeReferencePoint} as {@link Optional}
	 */
	public Optional<IEmfSourceCodeReferencePoint> getReferencePoint(UUID uuid) {

		return Optional.ofNullable(referencePoints.get(uuid));
	}

	/**
	 * Returns all instances of {@link IEmfSourceCodeReferencePoint},
	 * implementing the given class.
	 *
	 * @param <T>
	 *            the type of class to filter for
	 * @param targetClass
	 *            the class to filter for
	 * @return all matching instances of {@link IEmfSourceCodeReferencePoint}
	 *         (never <i>null</i>)
	 */
	public <T> Collection<T> getReferencePoints(Class<T> targetClass) {

		// TODO implement query cache to improve performance (a query cache must be thread-safe)
		return referencePoints//
			.values()
			.stream()
			.filter(targetClass::isInstance)
			.map(targetClass::cast)
			.sorted(Comparator.comparing(referencePoint -> new JavaClassName(referencePoint.getClass())))
			.collect(Collectors.toList());
	}
}
