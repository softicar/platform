package com.softicar.platform.emf.module.registry;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * Holds the thread local instance of {@link IEmfModuleRegistry}.
 *
 * @author Oliver Richers
 */
public class CurrentEmfModuleRegistry {

	private static final Singleton<IEmfModuleRegistry> INSTANCE = new Singleton<>();

	public static IEmfModuleRegistry get() {

		if (INSTANCE.get() == null) {
			INSTANCE.set(EmfAnnotationBasedModuleRegistry.getInstance());
		}

		return INSTANCE.get();
	}

	public static void set(IEmfModuleRegistry registry) {

		if (CurrentEmfModuleRegistry.INSTANCE.get() != null) {
			throw new IllegalStateException("Module registry has already been created.");
		}

		INSTANCE.set(registry);
	}

	public static void reset() {

		INSTANCE.reset();
	}
}
