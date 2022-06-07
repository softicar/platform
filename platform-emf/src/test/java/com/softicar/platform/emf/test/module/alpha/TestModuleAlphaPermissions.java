package com.softicar.platform.emf.test.module.alpha;

import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.test.permission.EmfModuleTestPermission;

public interface TestModuleAlphaPermissions {

	IEmfModulePermission<TestModuleAlphaInstance> PERMISSION_ONE = new EmfModuleTestPermission<>("permissionOne", (a, b) -> true);
	IEmfModulePermission<TestModuleAlphaInstance> PERMISSION_TWO = new EmfModuleTestPermission<>("permissionTwo", (a, b) -> true);
}
