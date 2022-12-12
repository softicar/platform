package com.softicar.platform.workflow.module.workflow.node;

import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public interface WorkflowNodePermissions {

	IEmfPermission<AGWorkflowVersion> CREATE = WorkflowPermissions.ADMINISTRATION
		.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflow().getModuleInstance()));

	IEmfPermission<AGWorkflowNode> EDIT = WorkflowPermissions.ADMINISTRATION
		.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflowVersion().getWorkflow().getModuleInstance()));

	IEmfPermission<AGWorkflowNode> VIEW = WorkflowPermissions.VIEW
		.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflowVersion().getWorkflow().getModuleInstance()));
}
