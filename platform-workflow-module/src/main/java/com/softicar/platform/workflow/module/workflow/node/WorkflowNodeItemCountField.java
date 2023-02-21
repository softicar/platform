package com.softicar.platform.workflow.module.workflow.node;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.transients.AbstractTransientIntegerField;
import com.softicar.platform.db.runtime.transients.IValueAccumulator;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import java.util.Set;

public class WorkflowNodeItemCountField extends AbstractTransientIntegerField<AGWorkflowNode> {

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.WORKFLOW_ITEMS;
	}

	@Override
	protected void loadValues(Set<AGWorkflowNode> workflowNodes, IValueAccumulator<AGWorkflowNode, Integer> accumulator) {

		Sql//
			.from(AGWorkflowItem.TABLE)
			.where(AGWorkflowItem.WORKFLOW_NODE.isIn(workflowNodes))
			.groupBy(AGWorkflowItem.WORKFLOW_NODE)
			.select(AGWorkflowItem.WORKFLOW_NODE)
			.select(Sql.count())
			.forEach(pair -> accumulator.add(pair.get0(), pair.get1()));
	}
}
