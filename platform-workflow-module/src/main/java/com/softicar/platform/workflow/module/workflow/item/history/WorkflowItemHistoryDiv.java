package com.softicar.platform.workflow.module.workflow.item.history;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.workflow.module.WorkflowCssClasses;
import com.softicar.platform.workflow.module.workflow.WorkflowShowGraphButton;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.message.WorkflowItemMessageCreationButton;

public class WorkflowItemHistoryDiv extends DomDiv {

	private final WorkflowItemHistoryEntryList historyEntryList;

	public WorkflowItemHistoryDiv(AGWorkflowItem workflowItem) {

		this.historyEntryList = new WorkflowItemHistoryEntryList(workflowItem);

		var bar = appendChild(new DomBar());
		bar.appendChild(new WorkflowItemMessageCreationButton(workflowItem).removeLabel());
		bar.appendChild(new WorkflowShowGraphButton(workflowItem).removeLabel());
		appendChild(historyEntryList);

		addCssClass(WorkflowCssClasses.WORKFLOW_ITEM_HISTORY_DIV);
	}
}
