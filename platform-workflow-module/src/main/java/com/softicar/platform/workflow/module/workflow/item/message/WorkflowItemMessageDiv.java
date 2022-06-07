package com.softicar.platform.workflow.module.workflow.item.message;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.field.bool.EmfBooleanInput;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegationLog;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.execution.AGWorkflowTransitionExecution;
import com.softicar.platform.workflow.module.workflow.transition.execution.auto.AGWorkflowAutoTransitionExecution;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WorkflowItemMessageDiv extends DomDiv implements IDomRefreshBusListener {

	private final AGWorkflowItem item;
	private final EmfBooleanInput showTransitionsCheckbox;
	private IEmfDataTableDiv<WorkflowItemMessageRow> tableDiv;

	public WorkflowItemMessageDiv(AGWorkflowItem item) {

		this.item = item;

		var actionBar = appendChild(new DomActionBar());

		actionBar
			.appendChild(
				new DomPopupButton()//
					.setPopupFactory(() -> new EmfFormPopup<>(new AGWorkflowItemMessage().setWorkflowItem(item)))
					.setIcon(EmfImages.ENTITY_CREATE.getResource())
					.setLabel(WorkflowI18n.ADD_NEW_MESSAGE)
					.setEnabled(WorkflowPermissions.OPERATOR.test(item.getWorkflow().getModuleInstance(), CurrentBasicUser.get())));
		this.showTransitionsCheckbox = actionBar//
			.appendChild(new EmfBooleanInput(true).setLabel(WorkflowI18n.SHOW_TRANSITIONS));
		showTransitionsCheckbox.setChangeCallback(() -> refresh(null));
		refresh(null);
	}

	@Override
	public void refresh(IDomRefreshBusEvent event) {

		if (tableDiv != null) {
			tableDiv.disappend();
		}

		List<WorkflowItemMessageRow> rows = loadAllRows();
		WorkflowItemMessageDataTable dataTable = new WorkflowItemMessageDataTable(showTransitionsCheckbox.isChecked(), rows);

		EmfDataTableDivBuilder<WorkflowItemMessageRow> tableDivBuilder = new EmfDataTableDivBuilder<>(dataTable)//
			.addOrderBy(dataTable.getAtColumn(), OrderDirection.DESCENDING)
			.addOrderBy(dataTable.getByColumn(), OrderDirection.ASCENDING);

		this.tableDiv = tableDivBuilder.buildAndAppendTo(this);
	}

	private List<WorkflowItemMessageRow> loadAllRows() {

		List<WorkflowItemMessageRow> rows = new ArrayList<>();
		rows.addAll(loadMessageRows());
		rows.addAll(loadTransitionRows());
		rows.addAll(loadAutoTransitionRows());
		rows.addAll(loadDelegationRows());
		return rows;
	}

	private List<WorkflowItemMessageRow> loadMessageRows() {

		return AGWorkflowItemMessage
			.createSelect()//
			.where(AGWorkflowItemMessage.WORKFLOW_ITEM.equal(item))
			.stream()
			.map(this::createRow)
			.collect(Collectors.toList());
	}

	private WorkflowItemMessageRow createRow(AGWorkflowItemMessage message) {

		return new WorkflowItemMessageRow(false, message.getTransaction(), message.getText());
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

		if (transitionExecution.relatedWorkflowItemLogRecordExists(item)) {
			// Assumption: The transition was really done (the workflow switched to the next node)
			// only if the workflow item was saved/updated and therefore a AGWorkflowItemLog record can be found
			rows.add(createTransitionRow(transitionExecution.getWorkflowTransition(), transitionExecution.getAt()));
		}

		return rows;
	}

	private WorkflowItemMessageRow createTransitionExecutionRow(AGWorkflowTransitionExecution transitionExecution) {

		String text = WorkflowI18n.EXECUTED_ACTION_ARG1.toDisplay(transitionExecution.getWorkflowTransitionToDisplay()).toString();
		return new WorkflowItemMessageRow(true, transitionExecution.getTransaction(), text);
	}

	private WorkflowItemMessageRow createTransitionRow(AGWorkflowTransition transition, DayTime createdAt) {

		String text = transition.getSourceNode().toDisplayWithoutId() + " -> " + transition.getTargetNode().toDisplayWithoutId();
		return new WorkflowItemMessageRow(true, null, text, createdAt);
	}

	private List<WorkflowItemMessageRow> loadAutoTransitionRows() {

		List<WorkflowItemMessageRow> rows = new ArrayList<>();
		for (AGWorkflowAutoTransitionExecution autoTransitionExecution: item.loadAutoTransitionExecutions()) {
			rows
				.add(
					new WorkflowItemMessageRow(//
						true,
						null,
						autoTransitionExecution.toDisplayWithoutId().toString(),
						autoTransitionExecution.getTransaction().getAt()));
		}
		return rows;
	}

	private List<WorkflowItemMessageRow> loadDelegationRows() {

		return item//
			.getAllWorkflowTasks()
			.stream()
			.map(it -> AGWorkflowTaskDelegation.TABLE.load(it))
			.flatMap(it -> AGWorkflowTaskDelegationLog.TABLE.createSelect().where(AGWorkflowTaskDelegationLog.WORKFLOW_TASK_DELEGATION.equal(it)).stream())
			.map(this::createRow)
			.collect(Collectors.toList());
	}

	private WorkflowItemMessageRow createRow(AGWorkflowTaskDelegationLog delegationLog) {

		return new WorkflowItemMessageRow(
			false,
			delegationLog.getTransaction(),
			WorkflowI18n.TASK_DELEGATED_TO_ARG1.toDisplay(delegationLog.getTargetUser().toDisplay()).toString());
	}
}
