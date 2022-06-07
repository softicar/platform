package com.softicar.platform.emf.permission;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.singleton.Singleton;

public class CurrentEmfPermissionRegistry {

	private static final Singleton<IEmfPermissionRegistry> INSTANCE = new Singleton<>();

	public static IEmfPermissionRegistry get() {

		if (INSTANCE.get() == null) {
			INSTANCE.set(new EmfPermissionRegistry());
		}
		return INSTANCE.get();
	}

	public static void set(IEmfPermissionRegistry registry) {

		if (INSTANCE.get() != null) {
			throw new SofticarDeveloperException("%s was already created.", IEmfPermissionRegistry.class.getSimpleName());
		}
		INSTANCE.set(registry);
	}
}
