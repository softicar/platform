package com.softicar.platform.common.container.derived;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * Holds the thread-local instance of the {@link DerivedObjectRegistry}.
 *
 * @author Oliver Richers
 */
public class CurrentDerivedObjectRegistry {

	private static final Singleton<DerivedObjectRegistry> INSTANCE = new Singleton<>(DerivedObjectRegistry::new);

	public static DerivedObjectRegistry getInstance() {

		return INSTANCE.get();
	}
}
