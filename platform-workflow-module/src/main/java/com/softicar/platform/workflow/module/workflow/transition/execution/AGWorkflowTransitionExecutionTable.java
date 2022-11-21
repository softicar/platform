package com.softicar.platform.workflow.module.workflow.transition.execution;

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

public class AGWorkflowTransitionExecutionTable extends EmfObjectTable<AGWorkflowTransitionExecution, AGWorkflowTask> {

	public AGWorkflowTransitionExecutionTable(IDbObjectTableBuilder<AGWorkflowTransitionExecution> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowTransitionExecution, AGWorkflowTask> authorizer) {

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
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowTransitionExecution, Integer, AGWorkflowTask> configuration) {

		configuration.setScopeField(AGWorkflowTransitionExecution.WORKFLOW_TASK);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowTransitionExecution> attributes) {

		attributes//
			.editAttribute(AGWorkflowTransitionExecution.WORKFLOW_TRANSITION)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editAttribute(AGWorkflowTransitionExecution.WORKFLOW_TASK)
			.setPredicateMandatory(EmfPredicates.always());
	}
}
