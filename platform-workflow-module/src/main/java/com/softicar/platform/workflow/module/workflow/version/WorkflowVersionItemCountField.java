package com.softicar.platform.workflow.module.workflow.version;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.transients.AbstractTransientIntegerField;
import com.softicar.platform.db.runtime.transients.IValueAccumulator;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import java.util.Set;

public class WorkflowVersionItemCountField extends AbstractTransientIntegerField<AGWorkflowVersion> {

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.WORKFLOW_ITEMS;
	}

	@Override
	protected void loadValues(Set<AGWorkflowVersion> workflowVersions, IValueAccumulator<AGWorkflowVersion, Integer> accumulator) {

		Sql//
			.from(AGWorkflowItem.TABLE)
			.join(AGWorkflowItem.WORKFLOW_NODE)
			.where(AGWorkflowNode.WORKFLOW_VERSION.isIn(workflowVersions))
			.groupBy(AGWorkflowNode.WORKFLOW_VERSION)
			.select(AGWorkflowNode.WORKFLOW_VERSION)
			.select(Sql.count())
			.forEach(pair -> accumulator.add(pair.get0(), pair.get1()));
	}
}
