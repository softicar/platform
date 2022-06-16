package com.softicar.platform.core.module.test.module.alpha;

import com.softicar.platform.core.module.module.permission.TestModulePermission;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;

public interface TestModuleAlphaPermissions {

	IEmfModulePermission<TestModuleAlphaInstance> PERMISSION_ONE =//
			new TestModulePermission<>("e9f3ef4e-7eec-43ef-abd2-52d231161b4e", "alphaPermissionOne");
	IEmfModulePermission<TestModuleAlphaInstance> PERMISSION_TWO =//
			new TestModulePermission<>("ff1324db-e419-44b1-ac3b-1a20f27dcb66", "alphaPermissionTwo");
}
