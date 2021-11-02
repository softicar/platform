package com.softicar.platform.emf.source.code.reference.point;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Searches, loads and instantiates all classes with an
 * {@link EmfSourceCodeReferencePointUuid} annotation.
 *
 * @author Oliver Richers
 */
class EmfSourceCodeReferencePointsLoader {

	private final Map<UUID, IEmfSourceCodeReferencePoint> referencePoints;
	private final ExceptionsCollector exceptionsCollector;
	private boolean loaded;

	public EmfSourceCodeReferencePointsLoader() {

		this.referencePoints = new TreeMap<>();
		this.exceptionsCollector = new ExceptionsCollector();
		this.loaded = false;
	}

	public Map<UUID, IEmfSourceCodeReferencePoint> loadAll() {

		if (!loaded) {
			new EmfSourceCodeReferencePointClassesFinder()//
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
			exceptionsCollector.add(new EmfSourceCodeReferencePointLoadingException(className, exception));
		}
	}

	private UUID getUuid(Class<?> referencePointClass) {

		return EmfSourceCodeReferencePoints.getUuidOrThrow(referencePointClass);
	}

	private IEmfSourceCodeReferencePoint createInstance(Class<?> referencePointClass) throws ReflectiveOperationException {

		return (IEmfSourceCodeReferencePoint) referencePointClass.getConstructor().newInstance();
	}
}
