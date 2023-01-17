package com.softicar.platform.workflow.module.workflow.item.history;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.message.AGWorkflowItemMessage;
import com.softicar.platform.workflow.module.workflow.item.message.WorkflowItemMessageRow;
import com.softicar.platform.workflow.module.workflow.item.message.WorkflowItemMessageRowsLoader;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class WorkflowItemHistoryEntryList extends DomDiv implements IDomRefreshBusListener {

	private final AGWorkflowItem workflowItem;
	private boolean showAll;

	public WorkflowItemHistoryEntryList(AGWorkflowItem workflowItem) {

		this.workflowItem = workflowItem;
		this.showAll = false;

		refresh();
	}

	public WorkflowItemHistoryEntryList showAll(boolean showAll) {

		this.showAll = showAll;
		refresh();
		return this;
	}

	@Override
	public void refresh(IDomRefreshBusEvent event) {

		if (event.isAnyObjectChanged(AGWorkflowItemMessage.class) || event.isChanged(workflowItem)) {
			refresh();
		}
	}

	private void refresh() {

		removeChildren();

		List<WorkflowItemMessageRow> rows = new WorkflowItemMessageRowsLoader(workflowItem).loadAllRows();
		var highestIndex = getHighestIndex(rows);
		var rowsToShow = getRowsToShow(rows, highestIndex - 1);
		if (rowsToShow.size() < rows.size()) {
			appendChild(new WorkflowItemHistoryShowMoreDiv(this));
		}

		for (WorkflowItemMessageRow row: rowsToShow) {
			if (row.isTransition()) {
				appendChild(new WorkflowItemHistoryWorkflowNodeDiv(row));
			} else {
				appendChild(new WorkflowItemHistoryEntryDiv(row));
			}
		}
	}

	private int getHighestIndex(List<WorkflowItemMessageRow> rows) {

		return rows//
			.stream()
			.mapToInt(WorkflowItemMessageRow::getIndex)
			.max()
			.orElse(0);
	}

	private Collection<WorkflowItemMessageRow> getRowsToShow(List<WorkflowItemMessageRow> rows, int minimumIndex) {

		return rows//
			.stream()
			.filter(row -> showAll || row.getIndex() >= minimumIndex)
			.collect(Collectors.toList());
	}
}
