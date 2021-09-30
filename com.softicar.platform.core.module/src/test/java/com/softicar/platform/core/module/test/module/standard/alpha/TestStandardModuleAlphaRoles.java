package com.softicar.platform.core.module.test.module.standard.alpha;

import com.softicar.platform.core.module.module.role.TestModuleRole;
import com.softicar.platform.emf.module.role.IEmfModuleRole;

public interface TestStandardModuleAlphaRoles {

	IEmfModuleRole<TestStandardModuleAlphaInstance> ROLE_ONE = new TestModuleRole<>("e9f3ef4e-7eec-43ef-abd2-52d231161b4e", "alphaRoleOne");
	IEmfModuleRole<TestStandardModuleAlphaInstance> ROLE_TWO = new TestModuleRole<>("ff1324db-e419-44b1-ac3b-1a20f27dcb66", "alphaRoleTwo");
}
