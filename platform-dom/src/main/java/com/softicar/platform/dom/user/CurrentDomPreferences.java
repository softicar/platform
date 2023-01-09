package com.softicar.platform.dom.user;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * Holds the thread-local {@link IDomPreferences} instance.
 *
 * @author Alexander Schmidt
 */
public class CurrentDomPreferences {

	private static final Singleton<IDomPreferences> INSTANCE = new Singleton<>(DomDefaultPreferences::new);

	public static IDomPreferences get() {

		return INSTANCE.get();
	}

	public static void set(IDomPreferences instance) {

		INSTANCE.set(instance);
	}
}
