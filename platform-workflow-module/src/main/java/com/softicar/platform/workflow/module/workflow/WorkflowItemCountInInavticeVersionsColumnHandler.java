package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableRowBasedColumnHandler;
import com.softicar.platform.workflow.module.WorkflowCssClasses;

public class WorkflowItemCountInInavticeVersionsColumnHandler extends EmfDataTableRowBasedColumnHandler<AGWorkflow, Integer> {

	@Override
	public void buildCell(IEmfDataTableCell<AGWorkflow, Integer> cell, AGWorkflow row) {

		super.buildCell(cell, row);

		cell.addCssClass(WorkflowCssClasses.WORKFLOW_ITEM_COUNT_IN_INAVTICE_VERSIONS_CELL);
		if (AGWorkflow.ITEM_COUNT_IN_INACTIVE_VERSIONS_FIELD.getValue(row) > 0) {
			cell.addCssClass(DomCssPseudoClasses.ERROR);
		}
	}
}
