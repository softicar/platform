package com.softicar.platform.workflow.module;

import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.module.role.EmfDefaultModuleRoles;

public interface WorkflowRoles {

	IEmfRole<AGWorkflowModuleInstance> VIEWER = EmfDefaultModuleRoles.getModuleViewer();
	IEmfRole<AGWorkflowModuleInstance> OPERATOR = EmfDefaultModuleRoles.getModuleOperator();
	IEmfRole<AGWorkflowModuleInstance> ADMINISTRATOR = EmfDefaultModuleRoles.getModuleAdministator();
}
