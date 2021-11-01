package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableActionCell;
import com.softicar.platform.emf.data.table.IEmfDataTableActionColumnHandler;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.workflow.item.message.AGWorkflowItemMessage;
import com.softicar.platform.workflow.module.workflow.item.message.WorkflowItemMessagePopup;
import com.softicar.platform.workflow.module.workflow.substitute.AGWorkflowSubstitute;
import com.softicar.platform.workflow.module.workflow.task.IWorkflowTaskQuery.IRow;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;

public class WorkflowTaskDiv extends DomDiv {

	public WorkflowTaskDiv() {

		AGUser currentUser = CurrentUser.get();

		appendChild(new DomActionBar())
			.appendChild(
				new DomPopupButton()//
					.setPopupFactory(() -> new EmfFormPopup<>(AGWorkflowSubstitute.TABLE.getOrCreate(currentUser)))
					.setIcon(WorkflowImages.USERS.getResource())
					.setLabel(WorkflowI18n.CONFIGURE_WORKFLOW_SUBSTITUTE));

		appendNewChild(DomElementTag.HR);
		appendChild(new WorkflowTaskForUserDiv(currentUser));
		for (AGWorkflowSubstitute substitute: AGWorkflowSubstitute.loadAllActiveForUser(currentUser)) {
			appendNewChild(DomElementTag.HR);
			appendNewChild(DomElementTag.H4).appendText(WorkflowI18n.SUBSTITUTE_FOR_ARG1.toDisplay(substitute.getUser().toDisplay()));
			appendChild(new WorkflowTaskForUserDiv(substitute.getUser()));
		}
	}

	private class WorkflowTaskForUserDiv extends DomDiv implements IDomRefreshBusListener {

		private final DomCheckbox showDelegatedTasksCheckbox;
		private final DomDiv contentDiv;
		private final AGUser user;

		public WorkflowTaskForUserDiv(AGUser user) {

			this.user = user;
			this.showDelegatedTasksCheckbox = appendChild(
				new DomCheckbox()//
					.setChecked(false)
					.setChangeCallback(() -> refresh(null))
					.setLabel(WorkflowI18n.SHOW_TASKS_DELEGATED_BY_ME));
			appendChild(contentDiv = new DomDiv());
			refresh(null);
		}

		@Override
		public void refresh(IDomRefreshBusEvent event) {

			contentDiv.removeChildren();
			contentDiv.appendChild(buildDiv());
		}

		private DomDiv buildDiv() {

			DomDiv userDiv = new DomDiv();

			IWorkflowTaskQuery query = IWorkflowTaskQuery.FACTORY//
				.createQuery()
				.setUser(user)
				.setShowMyDelegations(showDelegatedTasksCheckbox.isChecked());

			userDiv
				.appendChild(
					new EmfDataTableDivBuilder<>(query)//
						.setActionColumnHandler(new ActionColumnHandler())
						.setColumnHandler(IWorkflowTaskQuery.DELEGATED_BY_COLUMN, new DelegationColumnHandler())
						.setConcealed(IWorkflowTaskQuery.ITEM_COLUMN, true)
						.setOrderBy(IWorkflowTaskQuery.CREATED_AT_COLUMN, OrderDirection.DESCENDING)
						.build());

			return userDiv;
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
						.setPopupFactory(() -> new EmfFormPopup<>(AGWorkflowTaskDelegation.TABLE.getOrCreate(row.getTask())).setDirectEditing(true))
						.setIcon(WorkflowImages.RIGHT.getResource()) //FIXME check/change icon
						.setTitle(WorkflowI18n.DELEGATE));
		}
	}

	private class DelegationColumnHandler extends EmfDataTableValueBasedColumnHandler<AGUser> {

		@Override
		public void buildCell(IEmfDataTableCell<?, AGUser> cell, AGUser user) {

			if (user != null) {
				cell.appendText(WorkflowI18n.DELEGATED_BY_ARG1.toDisplay(user.toDisplay()));
			}
		}
	}
}
