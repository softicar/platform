package com.softicar.platform.emf.module.registry;

import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;

/**
 * Locates all {@link IEmfModule} classes, annotated with
 * {@link EmfSourceCodeReferencePointUuid}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 * @see IEmfModule
 */
public class EmfAnnotationBasedModuleRegistry extends AbstractEmfModuleRegistry {

	private static final EmfAnnotationBasedModuleRegistry INSTANCE = new EmfAnnotationBasedModuleRegistry();
	private RuntimeException initializationException;

	public static EmfAnnotationBasedModuleRegistry getInstance() {

		if (INSTANCE.initializationException == null) {
			return INSTANCE;
		} else {
			throw INSTANCE.initializationException;
		}
	}

	/**
	 * This constructor may not throw any exception. If an exception occurs, it
	 * is caught and thrown upon the first invocation of {@link #getInstance()}.
	 */
	private EmfAnnotationBasedModuleRegistry() {

		try {
			for (IEmfModule<?> module: EmfSourceCodeReferencePoints.getReferencePoints(IEmfModule.class)) {
				registerModule(module);
			}
		} catch (Exception exception) {
			// exception may not be thrown here, so defer it
			this.initializationException = new RuntimeException("Failed to initialize the module registry.", exception);
		}
	}
}
