package com.softicar.platform.workflow.module.workflow.item;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.transients.AbstractTransientSetField;
import com.softicar.platform.db.runtime.transients.IValueAccumulator;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.task.execution.AGWorkflowTaskExecution;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OpenTasksField extends AbstractTransientSetField<AGWorkflowItem, AGWorkflowTask> {

	public OpenTasksField() {

		super(HashSet::new);
	}

	@Override
	protected void loadValues(Set<AGWorkflowItem> rows, IValueAccumulator<AGWorkflowItem, AGWorkflowTask> accumulator) {

		AGWorkflowTask//
			.createSelect()
			.where(AGWorkflowTask.WORKFLOW_ITEM.isIn(rows))
			.where(AGWorkflowTask.CLOSED.not())
			.where(AGWorkflowTask.NOTIFY)
			.joinLeftReverse(AGWorkflowTaskExecution.WORKFLOW_TASK)
			.where(AGWorkflowTaskExecution.WORKFLOW_TASK.isNull())
			.stream()
			.collect(Collectors.groupingBy(AGWorkflowTask::getWorkflowItem))
			.entrySet()
			.stream()
			.forEach(entry -> accumulator.addAll(entry.getKey(), entry.getValue()));
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.OPEN_TASKS;
	}
}
