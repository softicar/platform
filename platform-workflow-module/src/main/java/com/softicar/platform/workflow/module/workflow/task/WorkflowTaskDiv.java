package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTable;
import com.softicar.platform.emf.data.table.IEmfDataTableActionCell;
import com.softicar.platform.emf.data.table.IEmfDataTableActionColumnHandler;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.workflow.item.message.AGWorkflowItemMessage;
import com.softicar.platform.workflow.module.workflow.item.message.WorkflowItemMessagePopup;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.task.IWorkflowTaskQuery.IRow;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;
import com.softicar.platform.workflow.module.workflow.user.configuration.AGWorkflowUserConfiguration;

public class WorkflowTaskDiv extends DomDiv {

	public WorkflowTaskDiv() {

		AGUser currentUser = CurrentUser.get();

		appendChild(new DomActionBar())
			.appendChild(
				new DomPopupButton()//
					.setPopupFactory(() -> new EmfFormPopup<>(AGWorkflowUserConfiguration.TABLE.getOrCreate(currentUser)))
					.setIcon(WorkflowImages.USERS.getResource())
					.setLabel(WorkflowI18n.CONFIGURE));

		appendNewChild(DomElementTag.HR);
		appendChild(new WorkflowTaskForUserDiv(currentUser));
		for (AGUser user: AGWorkflowUserConfiguration.loadAllUsersWithSubstitute(currentUser)) {
			appendNewChild(DomElementTag.HR);
			appendNewChild(DomElementTag.H4).appendText(WorkflowI18n.SUBSTITUTE_FOR_ARG1.toDisplay(user.toDisplayWithoutId()));
			appendChild(new WorkflowTaskForUserDiv(user));
		}
	}

	private class WorkflowTaskForUserDiv extends DomDiv implements IDomRefreshBusListener {

		private final DomCheckbox showDelegatedTasksCheckbox;
		private final DomCheckbox showExclusiveTasksOnlyCheckbox;
		private final IWorkflowTaskQuery query;
		private final IEmfDataTableDiv<IRow> tableDiv;

		public WorkflowTaskForUserDiv(AGUser user) {

			this.showDelegatedTasksCheckbox = new ShowDelegatedTasksCheckbox();
			this.showExclusiveTasksOnlyCheckbox = new ShowExclusiveTasksOnlyCheckbox();
			this.query = IWorkflowTaskQuery.FACTORY//
				.createQuery()
				.setUser(user)
				.setShowMyDelegations(showDelegatedTasksCheckbox.isChecked())
				.setShowExclusiveTasksOnly(showExclusiveTasksOnlyCheckbox.isChecked());
			var closeTasksButton = new CloseTasksButton();
			this.tableDiv = new EmfDataTableDivBuilder<>(query)//
				.setActionColumnHandler(new ActionColumnHandler())
				.setColumnHandler(IWorkflowTaskQuery.TASK_COLUMN, new TaskColumnHandler())
				.setColumnHandler(IWorkflowTaskQuery.WORKFLOW_NODE_COLUMN, new NodeColumnHandler())
				.setColumnHandler(IWorkflowTaskQuery.DELEGATED_BY_COLUMN, new DelegationColumnHandler())
				.setConcealed(IWorkflowTaskQuery.ITEM_COLUMN, true)
				.setOrderBy(IWorkflowTaskQuery.CREATED_AT_COLUMN, OrderDirection.DESCENDING)
				.setColumnTitle(IWorkflowTaskQuery.TASK_COLUMN, WorkflowI18n.TASK)
				.setColumnTitle(IWorkflowTaskQuery.WORKFLOW_NODE_COLUMN, WorkflowI18n.NODE)
				.setColumnTitle(IWorkflowTaskQuery.DELEGATED_BY_COLUMN, WorkflowI18n.DELEGATION)
				.setColumnTitle(IWorkflowTaskQuery.CREATED_AT_COLUMN, WorkflowI18n.CREATED_AT)
				.setRowSelectionModeMulti()
				.setRowSelectionCallback(closeTasksButton::updateState)
				.build();

			new DomActionBar(closeTasksButton, showDelegatedTasksCheckbox, showExclusiveTasksOnlyCheckbox).appendTo(this);
			appendChild(tableDiv);
		}

		@Override
		public void refresh(IDomRefreshBusEvent event) {

			tableDiv.refresh();
		}

		private class CloseTasksButton extends DomButton {

			public CloseTasksButton() {

				setIcon(WorkflowImages.ENTITY_DEACTIVATE.getResource());
				setLabel(WorkflowI18n.CLOSE_SELECTED_TASKS);
				setClickCallback(this::closeAllSelected);
				setConfirmationMessage(WorkflowI18n.ARE_YOU_SURE_QUESTION);
				setDisabled(true);
			}

			public void updateState(IEmfDataTable<?> dataTable) {

				setDisabled(dataTable.getController().getSelectedRows().isEmpty());
			}

			private void closeAllSelected() {

				tableDiv.getSelectedRows().forEach(row -> row.getTask().close());
			}
		}

		private class ShowDelegatedTasksCheckbox extends DomCheckbox {

			public ShowDelegatedTasksCheckbox() {

				super(false);
				addChangeCallback(this::handleToggle);
				setLabel(WorkflowI18n.SHOW_TASKS_DELEGATED_BY_ME);
			}

			private void handleToggle() {

				query.setShowMyDelegations(isChecked());
				tableDiv.refresh();
			}
		}

		private class ShowExclusiveTasksOnlyCheckbox extends DomCheckbox {

			public ShowExclusiveTasksOnlyCheckbox() {

				super(false);
				addChangeCallback(this::handleToggle);
				setLabel(WorkflowI18n.SHOW_ONLY_EXCLUSIVE_TASKS);
			}

			private void handleToggle() {

				query.setShowExclusiveTasksOnly(isChecked());
				tableDiv.refresh();
			}
		}
	}

	private class ActionColumnHandler implements IEmfDataTableActionColumnHandler<IRow> {

		@Override
		public void buildCell(IEmfDataTableActionCell<IRow> cell, IRow row) {

			cell.appendChild(new WorkflowItemEntityButtonFactory(row.getItem()).createShowButton());
			cell
				.appendChild(
					new DomPopupButton()//
						.setPopupFactory(() -> new WorkflowItemMessagePopup(row.getItem()))
						.setIcon(AGWorkflowItemMessage.TABLE.getIcon())
						.setTitle(WorkflowI18n.MESSAGES));
			cell
				.appendChild(
					new DomPopupButton()//
						.setPopupFactory(
							() -> new EmfFormPopup<>(AGWorkflowTaskDelegation.TABLE.getOrCreate(row.getTask()))//
								.setDirectEditing(true)
								.setCallbackAfterCreation(this::sendNotification))
						.setIcon(WorkflowImages.RIGHT.getResource()) //FIXME check/change icon
						.setTitle(WorkflowI18n.DELEGATE));
		}

		private void sendNotification(AGWorkflowTaskDelegation delegation) {

			new WorkflowTaskNotificationSubmitter(delegation.getWorkflowTask())//
				.setNotificationRecipient(delegation.getTargetUser())
				.submit();
		}
	}

	private class NodeColumnHandler extends EmfDataTableValueBasedColumnHandler<AGWorkflowNode> {

		@Override
		public void buildCell(IEmfDataTableCell<?, AGWorkflowNode> cell, AGWorkflowNode node) {

			cell.appendText(node.toDisplayWithoutId());
		}
	}

	private class TaskColumnHandler extends EmfDataTableValueBasedColumnHandler<AGWorkflowTask> {

		@Override
		public void buildCell(IEmfDataTableCell<?, AGWorkflowTask> cell, AGWorkflowTask task) {

			cell.appendText(task.toDisplayWithoutId());
		}
	}

	private class DelegationColumnHandler extends EmfDataTableValueBasedColumnHandler<AGUser> {

		@Override
		public void buildCell(IEmfDataTableCell<?, AGUser> cell, AGUser user) {

			if (user != null) {
				cell.appendText(WorkflowI18n.DELEGATED_BY_ARG1.toDisplay(user.toDisplayWithoutId()));
			}
		}
	}
}
