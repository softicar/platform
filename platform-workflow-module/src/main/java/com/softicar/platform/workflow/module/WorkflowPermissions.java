package com.softicar.platform.workflow.module;

import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;
import com.softicar.platform.emf.permission.IEmfPermission;

public interface WorkflowPermissions {

	IEmfPermission<AGWorkflowModuleInstance> VIEWER = EmfDefaultModulePermissions.getModuleViewer();
	IEmfPermission<AGWorkflowModuleInstance> OPERATOR = EmfDefaultModulePermissions.getModuleOperator();
	IEmfPermission<AGWorkflowModuleInstance> ADMINISTRATOR = EmfDefaultModulePermissions.getModuleAdministator();
}
