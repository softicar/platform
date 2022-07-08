package com.softicar.platform.common.code.reference.point;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Holds all existing {@link ISourceCodeReferencePoint} instances.
 *
 * @author Oliver Richers
 */
class SourceCodeReferencePointRegistry {

	private static final SourceCodeReferencePointRegistry INSTANCE = new SourceCodeReferencePointRegistry();
	private final Map<UUID, ISourceCodeReferencePoint> referencePoints;

	public static SourceCodeReferencePointRegistry getInstance() {

		return INSTANCE;
	}

	public SourceCodeReferencePointRegistry() {

		this.referencePoints = new SourceCodeReferencePointsLoader().loadAll();
	}

	/**
	 * Returns the matching {@link ISourceCodeReferencePoint} for the given
	 * {@link UUID}.
	 *
	 * @param uuid
	 *            the {@link UUID} (never <i>null</i>)
	 * @return the {@link ISourceCodeReferencePoint} as {@link Optional}
	 */
	public Optional<ISourceCodeReferencePoint> getReferencePoint(UUID uuid) {

		return Optional.ofNullable(referencePoints.get(uuid));
	}

	/**
	 * Returns all instances of {@link ISourceCodeReferencePoint},
	 * implementing the given class.
	 *
	 * @param <T>
	 *            the type of class to filter for
	 * @param targetClass
	 *            the class to filter for
	 * @return all matching instances of {@link ISourceCodeReferencePoint}
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
