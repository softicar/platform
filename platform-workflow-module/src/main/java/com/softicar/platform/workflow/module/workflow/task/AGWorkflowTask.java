package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;
import com.softicar.platform.workflow.module.workflow.transition.execution.AGWorkflowTransitionExecution;
import java.util.Collection;
import java.util.Optional;

public class AGWorkflowTask extends AGWorkflowTaskGenerated implements IEmfObject<AGWorkflowTask> {

	public static final CreationTransactionField<AGWorkflowTask, AGWorkflowTaskLog> CREATION_TRANSACTION =
			new CreationTransactionField<>(AGWorkflowTaskLog.WORKFLOW_TASK, AGWorkflowTaskLog.TRANSACTION);

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString
			.create(
				getWorkflowItem().getWorkflow().getTableReferencePointOrThrow().getTable().getFullName().getSimpleName() // 
						+ " - " //
						+ getWorkflowItem().getEntityOrThrow().toDisplayWithoutId().toString() + // 
						" :: " // 
						+ getWorkflowItem().getWorkflowNode().getName());
	}

	public void close() {

		setClosed(true);
		save();

		Optional//
			.ofNullable(AGWorkflowTaskDelegation.TABLE.load(this))
			.ifPresent(it -> it.setActive(false).save());
	}

	public static Collection<AGWorkflowTask> getOpenWorkflowTasks(AGUser user, AGWorkflowItem workflowItem) {

		return AGWorkflowTask.TABLE//
			.createSelect()
			.where(AGWorkflowTask.USER.isEqual(user))
			.where(AGWorkflowTask.WORKFLOW_ITEM.isEqual(workflowItem))
			.where(AGWorkflowTask.CLOSED.isFalse())
			.list();
	}

	public boolean wasNotExecuted() {

		return !AGWorkflowTransitionExecution//
			.createSelect()
			.where(AGWorkflowTransitionExecution.WORKFLOW_TASK.isEqual(this))
			.exists();
	}

	public Optional<AGWorkflowTaskDelegation> getDelegation() {

		return Optional.ofNullable(AGWorkflowTaskDelegation.TABLE.get(this));
	}

	public boolean hasActiveDelegation() {

		return getDelegation().map(delegation -> delegation.isActive()).orElse(false);
	}
}
