package com.softicar.platform.core.module.test.module.standard.beta;

import com.softicar.platform.core.module.module.permission.TestModulePermission;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;

public interface TestStandardModuleBetaPermissions {

	IEmfModulePermission<TestStandardModuleBetaInstance> PERMISSION_ONE = new TestModulePermission<>("cf7aa219-ed99-4b4d-b62e-81e210c9a551", "betaPermissionOne");
}
