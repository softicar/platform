package com.softicar.platform.workflow.module.workflow.item.message;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.WorkflowShowGraphButton;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import java.util.List;

public class WorkflowItemMessageDiv extends DomDiv implements IDomRefreshBusListener {

	private final AGWorkflowItem item;
	private final DomCheckbox verboseCheckbox;
	private IEmfDataTableDiv<WorkflowItemMessageRow> tableDiv;

	public WorkflowItemMessageDiv(AGWorkflowItem item) {

		this.item = item;

		var actionBar = appendChild(new DomActionBar());

		actionBar.appendChild(new WorkflowShowGraphButton(item));
		actionBar.appendChild(new WorkflowItemMessageCreationButton(item));
		this.verboseCheckbox = actionBar//
			.appendChild(new DomCheckbox(true).setLabel(WorkflowI18n.VERBOSE));
		verboseCheckbox.addChangeCallback(this::refresh);
		refresh();
	}

	@Override
	public void refresh(IDomRefreshBusEvent event) {

		refresh();
	}

	private void refresh() {

		if (tableDiv != null) {
			tableDiv.disappend();
		}

		List<WorkflowItemMessageRow> rows = new WorkflowItemMessageRowsLoader(item).loadAllRows();
		WorkflowItemMessageDataTable dataTable = new WorkflowItemMessageDataTable(verboseCheckbox.isChecked(), rows);

		EmfDataTableDivBuilder<WorkflowItemMessageRow> tableDivBuilder = new EmfDataTableDivBuilder<>(dataTable)//
			.addOrderBy(dataTable.getIndexColumn(), OrderDirection.DESCENDING)
			.addOrderBy(dataTable.getAtColumn(), OrderDirection.DESCENDING);

		this.tableDiv = tableDivBuilder.buildAndAppendTo(this);
	}
}
