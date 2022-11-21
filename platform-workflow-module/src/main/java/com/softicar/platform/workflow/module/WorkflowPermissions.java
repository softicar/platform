package com.softicar.platform.workflow.module;

import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;
import com.softicar.platform.emf.permission.IEmfPermission;

public interface WorkflowPermissions {

	IEmfPermission<AGWorkflowModuleInstance> VIEW = EmfDefaultModulePermissions.getModuleView();
	IEmfPermission<AGWorkflowModuleInstance> OPERATION = EmfDefaultModulePermissions.getModuleOperation();
	IEmfPermission<AGWorkflowModuleInstance> ADMINISTRATION = EmfDefaultModulePermissions.getModuleAdministration();
}
