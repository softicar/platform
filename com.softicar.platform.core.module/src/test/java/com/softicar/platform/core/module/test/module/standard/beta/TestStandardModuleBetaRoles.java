package com.softicar.platform.core.module.test.module.standard.beta;

import com.softicar.platform.core.module.module.role.TestModuleRole;
import com.softicar.platform.emf.module.role.IEmfModuleRole;

public interface TestStandardModuleBetaRoles {

	IEmfModuleRole<TestStandardModuleBetaInstance> ROLE_ONE = new TestModuleRole<>("cf7aa219-ed99-4b4d-b62e-81e210c9a551", "betaRoleOne");
}
