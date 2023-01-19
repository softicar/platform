package com.softicar.platform.workflow.module;

import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public interface WorkflowPermissions {

	// ---------------- AGWorkflowModuleInstance ---------------- //

	IEmfPermission<AGWorkflowModuleInstance> VIEW = EmfDefaultModulePermissions.getModuleView();
	IEmfPermission<AGWorkflowModuleInstance> OPERATION = EmfDefaultModulePermissions.getModuleOperation();
	IEmfPermission<AGWorkflowModuleInstance> ADMINISTRATION = EmfDefaultModulePermissions.getModuleAdministration();

	// ---------------- AGWorkflowVersion ---------------- //

	IEmfPermission<AGWorkflowVersion> VERSION_CREATE = ADMINISTRATION//
		.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflow().getModuleInstance()));

	// ---------------- AGWorkflowNode ---------------- //

	IEmfPermission<AGWorkflowNode> NODE_EDIT = ADMINISTRATION//
		.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflowVersion().getWorkflow().getModuleInstance()));

	IEmfPermission<AGWorkflowNode> NODE_VIEW = VIEW//
		.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflowVersion().getWorkflow().getModuleInstance()));
}
