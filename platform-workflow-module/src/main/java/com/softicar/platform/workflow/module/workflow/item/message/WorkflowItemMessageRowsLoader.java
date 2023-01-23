package com.softicar.platform.workflow.module.workflow.item.message;

import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItemLog;
import com.softicar.platform.workflow.module.workflow.item.message.severity.AGWorkflowMessageSeverityEnum;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegationLog;
import com.softicar.platform.workflow.module.workflow.transition.execution.AGWorkflowTransitionExecution;
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

		List<WorkflowItemMessageRow> rows = new ArrayList<>();
		rows.addAll(loadItemLogs());
		rows.addAll(loadTransitionRows());
		rows.addAll(loadMessageRows());
		rows.addAll(loadDelegationRows());
		Collections.sort(rows);
		fillNodeAndIndex(rows);
		return rows;
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

		return new WorkflowItemMessageRow(//
			WorkflowI18n.TRANSITION_TO_ARG1.toDisplay(itemLog.getWorkflowNode().getName()),
			itemLog.getTransaction())//
				.setWorkflowNode(itemLog.getWorkflowNode(), true);
	}

	private List<WorkflowItemMessageRow> loadTransitionRows() {

		List<WorkflowItemMessageRow> rows = new ArrayList<>();
		for (Set<AGWorkflowTransitionExecution> transitionExecutionSet: item.loadTransitionExecutionSets()) {
			rows.addAll(createTransitionExecutionRows(transitionExecutionSet.iterator()));
		}
		return rows;
	}

	private List<WorkflowItemMessageRow> createTransitionExecutionRows(Iterator<AGWorkflowTransitionExecution> transitionExecutionIterator) {

		List<WorkflowItemMessageRow> rows = new ArrayList<>();

		AGWorkflowTransitionExecution transitionExecution;
		do {
			transitionExecution = transitionExecutionIterator.next();
			rows.add(createTransitionExecutionRow(transitionExecution));
		} while (transitionExecutionIterator.hasNext());

		return rows;
	}

	private WorkflowItemMessageRow createTransitionExecutionRow(AGWorkflowTransitionExecution transitionExecution) {

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
