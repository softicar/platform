package com.softicar.platform.workflow.module.workflow.item.history;

import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.style.ICssClass;
import com.softicar.platform.workflow.module.WorkflowCssClasses;
import com.softicar.platform.workflow.module.workflow.item.message.WorkflowItemMessageRow;

public class WorkflowItemHistoryEntryDiv extends DomDiv {

	private final WorkflowItemMessageRow row;

	public WorkflowItemHistoryEntryDiv(WorkflowItemMessageRow row) {

		this.row = row;

		appendChild(new UserDiv());
		appendChild(new DateDiv());
		appendChild(new TextDiv());

		addCssClass(WorkflowCssClasses.WORKFLOW_ITEM_HISTORY_ENTRY_DIV);
		addCssClass(getSeverityCssClass());
	}

	private ICssClass getSeverityCssClass() {

		return switch (row.getSeverity().getEnum()) {
		case ERROR -> DomCssPseudoClasses.ERROR;
		case INFO -> DomCssPseudoClasses.INFO;
		case VERBOSE -> DomCssPseudoClasses.VERBOSE;
		case WARNING -> DomCssPseudoClasses.WARNING;
		};
	}

	private class UserDiv extends DomDiv {

		public UserDiv() {

			addCssClass(WorkflowCssClasses.WORKFLOW_ITEM_HISTORY_ENTRY_USER);
			appendText(row.getCreatedBy().toDisplay());
		}
	}

	private class DateDiv extends DomDiv {

		public DateDiv() {

			addCssClass(WorkflowCssClasses.WORKFLOW_ITEM_HISTORY_ENTRY_DATE);
			appendText(row.getCreatedAt().toDisplay());
		}
	}

	private class TextDiv extends DomDiv {

		public TextDiv() {

			addCssClass(WorkflowCssClasses.WORKFLOW_ITEM_HISTORY_ENTRY_TEXT);
			appendText(row.getText());
		}
	}
}
