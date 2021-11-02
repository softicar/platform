package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowRoles;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

public class AGWorkflowTaskTable extends EmfObjectTable<AGWorkflowTask, AGWorkflowItem> {

	public AGWorkflowTaskTable(IDbObjectTableBuilder<AGWorkflowTask> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowTask, AGWorkflowItem> authorizer) {

		authorizer//
			.setCreationRole(
				WorkflowRoles.OPERATOR.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflow().getModuleInstance())))
			.setViewRole(
				WorkflowRoles.VIEWER
					.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflowItem().getWorkflow().getModuleInstance())))
			.setEditRole(
				WorkflowRoles.OPERATOR
					.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflowItem().getWorkflow().getModuleInstance())));
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowTask, Integer, AGWorkflowItem> configuration) {

		configuration.setScopeField(AGWorkflowTask.WORKFLOW_ITEM);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowTask> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowTaskLog.WORKFLOW_TASK, AGWorkflowTaskLog.TRANSACTION)//
			.addMapping(AGWorkflowTask.NOTIFY, AGWorkflowTaskLog.NOTIFY)
			.addMapping(AGWorkflowTask.USER, AGWorkflowTaskLog.USER)
			.addMapping(AGWorkflowTask.CLOSED, AGWorkflowTaskLog.CLOSED);
	}
}
