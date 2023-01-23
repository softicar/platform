package com.softicar.platform.workflow.module.workflow.item.history;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.workflow.module.WorkflowCssClasses;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.message.WorkflowItemMessageRow;

public class WorkflowItemHistoryWorkflowNodeDiv extends DomDiv {

	public WorkflowItemHistoryWorkflowNodeDiv(WorkflowItemMessageRow row) {

		addCssClass(WorkflowCssClasses.WORKFLOW_ITEM_HISTORY_WORKFLOW_NODE_DIV);
		appendChild(new Label(row));
		setTitle(row.getTransaction().getBy().toDisplay() + " - " + row.getTransaction().getAt().toDisplay());
	}

	private class Label extends DomDiv {

		public Label(WorkflowItemMessageRow row) {

			addCssClass(WorkflowCssClasses.WORKFLOW_ITEM_HISTORY_WORKFLOW_NODE_LABEL);
			appendText(WorkflowI18n.NODE_ARG1.toDisplay(row.getNodeName()));
		}
	}
}
