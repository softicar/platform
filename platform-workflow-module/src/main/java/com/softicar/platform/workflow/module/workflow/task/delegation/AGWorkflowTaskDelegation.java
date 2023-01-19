package com.softicar.platform.workflow.module.workflow.task.delegation;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.emf.trait.IEmfTrait;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import java.util.Optional;

public class AGWorkflowTaskDelegation extends AGWorkflowTaskDelegationGenerated implements IEmfTrait<AGWorkflowTaskDelegation, AGWorkflowTask> {

	public Optional<DayTime> getLastModificationTimeFormLog() {
		// TODO: PLAT-1272 Add a transaction column to AGWorkflowDelegation and read time form there

		return AGWorkflowTaskDelegationLog.TABLE
			.createSelect()
			.where(AGWorkflowTaskDelegationLog.WORKFLOW_TASK_DELEGATION.isEqual(this))
			.orderDescendingBy(AGWorkflowTaskDelegationLog.TRANSACTION)
			.getFirstAsOptional()
			.map(log -> log.getTransaction().getAt());
	}
}
