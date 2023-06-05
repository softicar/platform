package com.softicar.platform.workflow.module.workflow.task.execution;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;

public class AGWorkflowTaskExecutionTable extends EmfObjectTable<AGWorkflowTaskExecution, AGWorkflowTask> {

	public AGWorkflowTaskExecutionTable(IDbObjectTableBuilder<AGWorkflowTaskExecution> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowTaskExecution, AGWorkflowTask> authorizer) {

		authorizer//
			.setCreationPermission(
				WorkflowPermissions.ADMINISTRATION
					.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflowItem().getWorkflow().getModuleInstance())))
			.setViewPermission(
				WorkflowPermissions.VIEW
					.of(
						IEmfTableRowMapper
							.nonOptional(
								WorkflowI18n.WORKFLOW_MODULE_INSTANCE,
								it -> it.getWorkflowTransition().getWorkflowVersion().getWorkflow().getModuleInstance())))
			.setEditPermission(
				WorkflowPermissions.ADMINISTRATION
					.of(
						IEmfTableRowMapper
							.nonOptional(
								WorkflowI18n.WORKFLOW_MODULE_INSTANCE,
								it -> it.getWorkflowTransition().getWorkflowVersion().getWorkflow().getModuleInstance())));
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowTaskExecution, Integer, AGWorkflowTask> configuration) {

		configuration.setScopeField(AGWorkflowTaskExecution.WORKFLOW_TASK);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowTaskExecution> attributes) {

		attributes//
			.editAttribute(AGWorkflowTaskExecution.WORKFLOW_TRANSITION)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editAttribute(AGWorkflowTaskExecution.WORKFLOW_TASK)
			.setPredicateMandatory(EmfPredicates.always());
	}
}
