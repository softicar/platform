package com.softicar.platform.emf.persistence;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * Holds the thread-local {@link IEmfPersistenceApi} instance.
 *
 * @author Oliver Richers
 */
public class CurrentEmfPersistenceApi {

	private static final Singleton<IEmfPersistenceApi> INSTANCE = new Singleton<>(EmfDummyPersistenceApi::new);

	public static IEmfPersistenceApi get() {

		return INSTANCE.get();
	}

	public static void set(IEmfPersistenceApi instance) {

		INSTANCE.set(instance);
	}
}
