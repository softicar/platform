package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.singleton.Singleton;

public class CurrentEmfRoleRegistry {

	private static final Singleton<IEmfRoleRegistry> INSTANCE = new Singleton<>();

	public static IEmfRoleRegistry get() {

		if (INSTANCE.get() == null) {
			INSTANCE.set(new EmfRoleRegistry());
		}
		return INSTANCE.get();
	}

	public static void set(IEmfRoleRegistry registry) {

		if (INSTANCE.get() != null) {
			throw new SofticarDeveloperException("%s was already created.", IEmfRoleRegistry.class.getSimpleName());
		}
		INSTANCE.set(registry);
	}
}
