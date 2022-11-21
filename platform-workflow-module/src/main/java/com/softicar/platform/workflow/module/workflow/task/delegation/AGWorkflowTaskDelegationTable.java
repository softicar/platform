package com.softicar.platform.workflow.module.workflow.task.delegation;

import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfAttributeDefaultValueSet;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.trait.table.EmfTraitTable;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;

public class AGWorkflowTaskDelegationTable extends EmfTraitTable<AGWorkflowTaskDelegation, AGWorkflowTask> {

	protected AGWorkflowTaskDelegationTable(IDbTableBuilder<AGWorkflowTaskDelegation, AGWorkflowTask> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowTaskDelegation, AGWorkflowTask, AGWorkflowTask> configuration) {

		configuration.setScopeField(AGWorkflowTaskDelegation.WORKFLOW_TASK);
		configuration.addValidator(WorkflowTaskDelegationValidator::new);
		configuration.setIcon(WorkflowImages.RIGHT);
		configuration.addSaveHook(WorkflowTaskDelegationSaveHook::new);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowTaskDelegation> attributes) {

		//FIXME explicitly setting this to non-editable here shouldn't be necessary
		attributes//
			.editAttribute(AGWorkflowTaskDelegation.DELEGATED_BY)
			.setEditable(false)
			.setPredicateVisible(EmfPredicates.never());

		attributes//
			.editAttribute(AGWorkflowTaskDelegation.TARGET_USER)
			.setPredicateMandatory(EmfPredicates.always());

		attributes//
			.editAttribute(AGWorkflowTaskDelegation.WORKFLOW_TASK)
			.setImmutable(true)
			.setPredicateEditable(EmfPredicates.never());

		attributes//
			.editAttribute(AGWorkflowTaskDelegation.ACTIVE)
			.setPredicateVisibleEditable(EmfPredicates.never());
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowTaskDelegation, AGWorkflowTask> authorizer) {

		authorizer//
			.setCreationPermission(EmfPermissions.always())
			.setViewPermission(
				WorkflowPermissions.VIEW
					.of(
						IEmfTableRowMapper
							.nonOptional(
								WorkflowI18n.WORKFLOW_MODULE_INSTANCE,
								it -> it.getWorkflowTask().getWorkflowItem().getWorkflow().getModuleInstance())))
			.setEditPermission(EmfPermissions.always());
	}

	@Override
	public void customizeAttributeDefaultValues(EmfAttributeDefaultValueSet<AGWorkflowTaskDelegation, AGWorkflowTask> defaultValueSet) {

		defaultValueSet.setSupplier(AGWorkflowTaskDelegation.DELEGATED_BY, CurrentUser::get);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowTaskDelegation> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowTaskDelegationLog.WORKFLOW_TASK_DELEGATION, AGWorkflowTaskDelegationLog.TRANSACTION)
			.addMapping(AGWorkflowTaskDelegation.ACTIVE, AGWorkflowTaskDelegationLog.ACTIVE)
			.addMapping(AGWorkflowTaskDelegation.TARGET_USER, AGWorkflowTaskDelegationLog.TARGET_USER)
			.addMapping(AGWorkflowTaskDelegation.DELEGATED_BY, AGWorkflowTaskDelegationLog.DELEGATED_BY);
	}
}
