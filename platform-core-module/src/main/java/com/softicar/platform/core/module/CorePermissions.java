package com.softicar.platform.core.module;

import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;

public interface CorePermissions {

	IEmfModulePermission<AGCoreModuleInstance> VIEW = EmfDefaultModulePermissions.getModuleView();
	IEmfModulePermission<AGCoreModuleInstance> OPERATION = EmfDefaultModulePermissions.getModuleOperation();
	IEmfModulePermission<AGCoreModuleInstance> ADMINISTRATION = EmfDefaultModulePermissions.getModuleAdministation();
}
