package com.softicar.platform.emf.test.module.beta;

import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.test.permission.EmfModuleTestPermission;

public interface TestModuleBetaPermissions {

	IEmfModulePermission<TestModuleBetaInstance> PERMISSION_ONE = new EmfModuleTestPermission<>("permissionOne", (a, b) -> true);
	IEmfModulePermission<TestModuleBetaInstance> PERMISSION_TWO = new EmfModuleTestPermission<>("permissionTwo", (a, b) -> true);
}
