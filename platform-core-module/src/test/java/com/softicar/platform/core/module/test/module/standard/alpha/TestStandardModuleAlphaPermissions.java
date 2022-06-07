package com.softicar.platform.core.module.test.module.standard.alpha;

import com.softicar.platform.core.module.module.permission.TestModulePermission;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;

public interface TestStandardModuleAlphaPermissions {

	IEmfModulePermission<TestStandardModuleAlphaInstance> PERMISSION_ONE =//
			new TestModulePermission<>("e9f3ef4e-7eec-43ef-abd2-52d231161b4e", "alphaPermissionOne");
	IEmfModulePermission<TestStandardModuleAlphaInstance> PERMISSION_TWO =//
			new TestModulePermission<>("ff1324db-e419-44b1-ac3b-1a20f27dcb66", "alphaPermissionTwo");
}
