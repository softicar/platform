package com.softicar.platform.core.module.test.module.beta;

import com.softicar.platform.core.module.module.permission.TestModulePermission;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;

public interface TestModuleBetaPermissions {

	IEmfModulePermission<TestModuleBetaInstance> PERMISSION_ONE = new TestModulePermission<>("cf7aa219-ed99-4b4d-b62e-81e210c9a551", "betaPermissionOne");
}
