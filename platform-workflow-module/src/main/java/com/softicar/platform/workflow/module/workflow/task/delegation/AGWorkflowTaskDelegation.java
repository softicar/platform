package com.softicar.platform.workflow.module.workflow.task.delegation;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.emf.trait.IEmfTrait;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import java.util.Optional;

public class AGWorkflowTaskDelegation extends AGWorkflowTaskDelegationGenerated implements IEmfTrait<AGWorkflowTaskDelegation, AGWorkflowTask> {

	public Optional<DayTime> getLastModificationTimeFormLog() {

		return AGWorkflowTaskDelegationLog.TABLE
			.createSelect()
			.where(AGWorkflowTaskDelegationLog.WORKFLOW_TASK_DELEGATION.isEqual(this))
			.orderDescendingBy(AGWorkflowTaskDelegationLog.TRANSACTION)
			.getFirstAsOptional()
			.map(log -> log.getTransaction().getAt());
	}
}
