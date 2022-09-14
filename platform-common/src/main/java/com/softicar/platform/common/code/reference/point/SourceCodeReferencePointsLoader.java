package com.softicar.platform.common.code.reference.point;

import com.softicar.platform.common.code.classpath.metadata.ClasspathFilesMetadata;
import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.utils.ReflectionUtils;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Searches, loads and instantiates all classes with an
 * {@link SourceCodeReferencePointUuid} annotation.
 *
 * @author Oliver Richers
 */
class SourceCodeReferencePointsLoader {

	private final Map<UUID, ISourceCodeReferencePoint> referencePoints;
	private final ExceptionsCollector exceptionsCollector;
	private boolean loaded;

	public SourceCodeReferencePointsLoader() {

		this.referencePoints = new TreeMap<>();
		this.exceptionsCollector = new ExceptionsCollector();
		this.loaded = false;
	}

	public Map<UUID, ISourceCodeReferencePoint> loadAll() {

		if (!loaded) {
			ClasspathFilesMetadata//
				.getInstance()
				.getDirectlyAnnotatedClasses(SourceCodeReferencePointUuid.class)
				.stream()
				.filter(this::isPublic)
				.forEach(this::addReferencePoint);
			this.loaded = true;
		}

		exceptionsCollector.throwExceptionIfNotEmpty();
		return referencePoints;
	}

	private boolean isPublic(Class<?> referencePointClass) {

		return ReflectionUtils.isPublic(referencePointClass);
	}

	private void addReferencePoint(Class<?> referencePointClass) {

		try {
			referencePoints.put(getUuid(referencePointClass), createInstance(referencePointClass));
		} catch (Exception exception) {
			exceptionsCollector.add(new SourceCodeReferencePointLoadingException(referencePointClass, exception));
		}
	}

	private UUID getUuid(Class<?> referencePointClass) {

		return SourceCodeReferencePoints.getUuidOrThrow(referencePointClass);
	}

	private ISourceCodeReferencePoint createInstance(Class<?> referencePointClass) throws ReflectiveOperationException {

		return (ISourceCodeReferencePoint) referencePointClass.getConstructor().newInstance();
	}
}
