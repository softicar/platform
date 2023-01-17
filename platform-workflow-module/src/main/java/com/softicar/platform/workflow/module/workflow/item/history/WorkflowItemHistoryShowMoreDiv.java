package com.softicar.platform.workflow.module.workflow.item.history;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.workflow.module.WorkflowCssClasses;
import com.softicar.platform.workflow.module.WorkflowI18n;

public class WorkflowItemHistoryShowMoreDiv extends DomDiv {

	private final WorkflowItemHistoryEntryList entryList;

	public WorkflowItemHistoryShowMoreDiv(WorkflowItemHistoryEntryList entryList) {

		this.entryList = entryList;

		appendChild(new ShowMoreButton());
		addCssClass(WorkflowCssClasses.WORKFLOW_ITEM_HISTORY_SHOW_MORE_DIV);
	}

	private class ShowMoreButton extends DomButton {

		public ShowMoreButton() {

			setLabel(WorkflowI18n.SHOW_MORE);
			setTitle(WorkflowI18n.SHOW_MORE);
			setClickCallback(this::showMore);
		}

		private void showMore() {

			entryList.showAll(true);
		}
	}
}
