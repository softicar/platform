package com.softicar.platform.workflow.module.workflow.item;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowRoles;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;

public class AGWorkflowItemTable extends EmfObjectTable<AGWorkflowItem, AGWorkflow> {

	public AGWorkflowItemTable(IDbObjectTableBuilder<AGWorkflowItem> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowItem, AGWorkflow> authorizer) {

		authorizer//
			.setCreationRole(
				WorkflowRoles.ADMINISTRATOR.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getModuleInstance())))
			.setViewRole(
				WorkflowRoles.VIEWER.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflow().getModuleInstance())))
			.setEditRole(
				WorkflowRoles.ADMINISTRATOR
					.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflow().getModuleInstance())));
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowItem, Integer, AGWorkflow> configuration) {

		configuration.setScopeField(AGWorkflowItem.WORKFLOW);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowItem> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowItemLog.WORKFLOW_ITEM, AGWorkflowItemLog.TRANSACTION)
			.addMapping(AGWorkflowItem.WORKFLOW_NODE, AGWorkflowItemLog.WORKFLOW_NODE);
	}
}
