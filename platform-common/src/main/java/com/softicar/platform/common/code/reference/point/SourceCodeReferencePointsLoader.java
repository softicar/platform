package com.softicar.platform.common.code.reference.point;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
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
			new SourceCodeReferencePointClassesFinder()//
				.findClasses()
				.forEach(this::loadReferencePoint);
			this.loaded = true;
		}

		exceptionsCollector.throwExceptionIfNotEmpty();
		return referencePoints;
	}

	private void loadReferencePoint(JavaClassName className) {

		try {
			Class<?> referencePointClass = Class.forName(className.getName());
			referencePoints.put(getUuid(referencePointClass), createInstance(referencePointClass));
		} catch (Exception exception) {
			exceptionsCollector.add(new SourceCodeReferencePointLoadingException(className, exception));
		}
	}

	private UUID getUuid(Class<?> referencePointClass) {

		return SourceCodeReferencePoints.getUuidOrThrow(referencePointClass);
	}

	private ISourceCodeReferencePoint createInstance(Class<?> referencePointClass) throws ReflectiveOperationException {

		return (ISourceCodeReferencePoint) referencePointClass.getConstructor().newInstance();
	}
}
