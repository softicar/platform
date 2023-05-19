package com.softicar.platform.workflow.module.workflow.item.message;

import com.softicar.platform.common.core.utils.equals.Equals;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItemLog;
import com.softicar.platform.workflow.module.workflow.item.message.severity.AGWorkflowMessageSeverityEnum;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegationLog;
import com.softicar.platform.workflow.module.workflow.transition.execution.AGWorkflowTransitionExecution;
import com.softicar.platform.workflow.module.workflow.transition.execution.auto.AGWorkflowAutoTransitionExecution;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WorkflowItemMessageRowsLoader {

	private final AGWorkflowItem item;

	public WorkflowItemMessageRowsLoader(AGWorkflowItem item) {

		this.item = item;
	}

	public List<WorkflowItemMessageRow> loadAllRows() {

		// please note, loading order is important
		List<WorkflowItemMessageRow> rows = new ArrayList<>();
		rows.addAll(loadMessageRows());
		rows.addAll(loadTaskExecutionRows());
		rows.addAll(loadDelegationRows());
		rows.addAll(loadAutoTransitionSourceRows());
		rows.addAll(loadItemLogs());

		Collections.sort(rows);
		removedDuplicates(rows);
		fillNodeAndIndex(rows);
		return rows;
	}

	private void removedDuplicates(List<WorkflowItemMessageRow> rows) {

		var previousRow = (WorkflowItemMessageRow) null;
		var iterator = rows.iterator();
		while (iterator.hasNext()) {
			var row = iterator.next();
			if (previousRow != null && isEqual(row, previousRow)) {
				iterator.remove();
			}
			previousRow = row;
		}
	}

	private boolean isEqual(WorkflowItemMessageRow a, WorkflowItemMessageRow b) {

		return Equals//
			.comparing(WorkflowItemMessageRow::getText)
			.comparing(WorkflowItemMessageRow::getTransaction)
			.compare(a, b);
	}

	private void fillNodeAndIndex(List<WorkflowItemMessageRow> rows) {

		int index = 0;
		AGWorkflowNode node = null;
		for (var row: rows) {
			if (row.getWorkflowNode() != null) {
				node = row.getWorkflowNode();
				index = index + 1;
			} else {
				row.setWorkflowNode(node, false);
			}
			row.setIndex(index);
		}
	}

	private List<WorkflowItemMessageRow> loadMessageRows() {

		return AGWorkflowItemMessage.TABLE//
			.createSelect()
			.where(AGWorkflowItemMessage.WORKFLOW_ITEM.isEqual(item))
			.stream()
			.map(this::createRow)
			.collect(Collectors.toList());
	}

	private WorkflowItemMessageRow createRow(AGWorkflowItemMessage message) {

		return new WorkflowItemMessageRow(WorkflowI18n.COMMENT_ARG1.toDisplay(message.getText()), message.getSeverity(), message.getTransaction());
	}

	private List<WorkflowItemMessageRow> loadItemLogs() {

		return AGWorkflowItemLog.TABLE//
			.createSelect()
			.where(AGWorkflowItemLog.WORKFLOW_ITEM.isEqual(item))
			.orderBy(AGWorkflowItemLog.TRANSACTION)
			.stream()
			.map(this::createRow)
			.collect(Collectors.toList());
	}

	private WorkflowItemMessageRow createRow(AGWorkflowItemLog itemLog) {

		return createTransitionRow(//
			itemLog.getWorkflowNode(),
			itemLog.getTransaction());
	}

	private List<WorkflowItemMessageRow> loadAutoTransitionSourceRows() {

		return AGWorkflowAutoTransitionExecution.TABLE//
			.createSelect()
			.where(AGWorkflowAutoTransitionExecution.WORKFLOW_ITEM.isEqual(item))
			.orderBy(AGWorkflowAutoTransitionExecution.TRANSACTION)
			.orderBy(AGWorkflowAutoTransitionExecution.ID)
			.stream()
			.map(this::createAutoTransitionSourceRow)
			.collect(Collectors.toList());
	}

	private WorkflowItemMessageRow createAutoTransitionSourceRow(AGWorkflowAutoTransitionExecution transitionExecution) {

		return createTransitionRow(//
			transitionExecution.getWorkflowTransition().getSourceNode(),
			transitionExecution.getTransaction());
	}

	private WorkflowItemMessageRow createTransitionRow(AGWorkflowNode targetNode, AGTransaction transaction) {

		var message = WorkflowI18n.TRANSITION_TO_ARG1.toDisplay(targetNode.getName());
		return new WorkflowItemMessageRow(message, transaction).setWorkflowNode(targetNode, true);
	}

	private List<WorkflowItemMessageRow> loadTaskExecutionRows() {

		List<WorkflowItemMessageRow> rows = new ArrayList<>();
		for (Set<AGWorkflowTransitionExecution> transitionExecutionSet: item.loadTransitionExecutionSets()) {
			rows.addAll(createTaskExecutionRows(transitionExecutionSet.iterator()));
		}
		return rows;
	}

	private List<WorkflowItemMessageRow> createTaskExecutionRows(Iterator<AGWorkflowTransitionExecution> transitionExecutionIterator) {

		List<WorkflowItemMessageRow> rows = new ArrayList<>();

		AGWorkflowTransitionExecution transitionExecution;
		do {
			transitionExecution = transitionExecutionIterator.next();
			rows.add(createTaskExecutionRow(transitionExecution));
		} while (transitionExecutionIterator.hasNext());

		return rows;
	}

	private WorkflowItemMessageRow createTaskExecutionRow(AGWorkflowTransitionExecution transitionExecution) {

		var text = WorkflowI18n.EXECUTED_ACTION_ARG1.toDisplay(transitionExecution.getWorkflowTransition().toDisplay());
		return new WorkflowItemMessageRow(text, transitionExecution.getTransaction());
	}

	private List<WorkflowItemMessageRow> loadDelegationRows() {

		return item//
			.getAllWorkflowTasks()
			.stream()
			.map(it -> AGWorkflowTaskDelegation.TABLE.load(it))
			.flatMap(it -> AGWorkflowTaskDelegationLog.TABLE.createSelect().where(AGWorkflowTaskDelegationLog.WORKFLOW_TASK_DELEGATION.isEqual(it)).stream())
			.map(this::createRow)
			.collect(Collectors.toList());
	}

	private WorkflowItemMessageRow createRow(AGWorkflowTaskDelegationLog delegationLog) {

		return new WorkflowItemMessageRow(//
			WorkflowI18n.TASK_DELEGATED_TO_ARG1.toDisplay(delegationLog.getTargetUser().toDisplay()),
			AGWorkflowMessageSeverityEnum.INFO.getRecord(),
			delegationLog.getTransaction());
	}

}
