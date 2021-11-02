package com.softicar.platform.emf.test.module.alpha;

import com.softicar.platform.emf.module.role.IEmfModuleRole;
import com.softicar.platform.emf.test.role.EmfModuleTestRole;

public interface TestModuleAlphaRoles {

	IEmfModuleRole<TestModuleAlphaInstance> ROLE_ONE = new EmfModuleTestRole<>("roleOne", (a, b) -> true);
	IEmfModuleRole<TestModuleAlphaInstance> ROLE_TWO = new EmfModuleTestRole<>("roleTwo", (a, b) -> true);
}
