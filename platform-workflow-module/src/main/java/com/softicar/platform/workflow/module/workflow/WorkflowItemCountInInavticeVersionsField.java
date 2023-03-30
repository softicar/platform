package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.transients.AbstractTransientIntegerField;
import com.softicar.platform.db.runtime.transients.IValueAccumulator;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import java.util.Set;

public class WorkflowItemCountInInavticeVersionsField extends AbstractTransientIntegerField<AGWorkflow> {

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.ITEMS_IN_INACTIVE_VERSIONS;
	}

	@Override
	protected void loadValues(Set<AGWorkflow> workflows, IValueAccumulator<AGWorkflow, Integer> accumulator) {

		Sql//
			.from(AGWorkflowItem.TABLE)
			.join(AGWorkflowItem.WORKFLOW_NODE)
			.where(AGWorkflowNode.WORKFLOW_VERSION.isNotIn(AGWorkflowVersion.getCurrentVersions()))
			.join(AGWorkflowNode.WORKFLOW_VERSION)
			.where(AGWorkflowVersion.WORKFLOW.isIn(workflows))
			.select(AGWorkflowVersion.WORKFLOW)
			.groupBy(AGWorkflowVersion.WORKFLOW)
			.select(Sql.count())
			.forEach(pair -> accumulator.add(pair.get0(), pair.get1()));
	}
}
