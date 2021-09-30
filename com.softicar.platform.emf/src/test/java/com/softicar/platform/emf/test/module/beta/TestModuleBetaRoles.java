package com.softicar.platform.emf.test.module.beta;

import com.softicar.platform.emf.module.role.IEmfModuleRole;
import com.softicar.platform.emf.test.role.EmfModuleTestRole;

public interface TestModuleBetaRoles {

	IEmfModuleRole<TestModuleBetaInstance> ROLE_ONE = new EmfModuleTestRole<>("roleOne", (a, b) -> true);
	IEmfModuleRole<TestModuleBetaInstance> ROLE_TWO = new EmfModuleTestRole<>("roleTwo", (a, b) -> true);
}
