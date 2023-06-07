package com.softicar.platform.workflow.module.workflow.task.execution;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItemLog;

public class AGWorkflowTaskExecution extends AGWorkflowTaskExecutionGenerated implements IEmfObject<AGWorkflowTaskExecution> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return WorkflowI18n.TASK_ARG1_EXECUTED_TRANSITION_ARG2.toDisplay(getWorkflowTask().toDisplay(), getWorkflowTransition().toDisplay());
	}

	public boolean relatedWorkflowItemLogRecordExists(AGWorkflowItem item) {

		return AGWorkflowItemLog.TABLE.load(new Tuple2<>(item, getTransaction())) != null;
	}
}
