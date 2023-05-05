package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

class WorkflowAutoTransitionsLoader {

	private final Collection<AGWorkflowItem> workflowItemFilter;
	private final Collection<AGWorkflowItem> workflowItemBlacklist;

	public WorkflowAutoTransitionsLoader() {

		this.workflowItemFilter = new HashSet<>();
		this.workflowItemBlacklist = new HashSet<>();
	}

	public void setFilter(Collection<AGWorkflowItem> workflowItems) {

		this.workflowItemFilter.clear();
		addToFilter(workflowItems);
	}

	public void addToFilter(Collection<AGWorkflowItem> workflowItems) {

		this.workflowItemFilter.addAll(workflowItems);
	}

	public void addToBlacklist(AGWorkflowItem workflowItem) {

		Objects.requireNonNull(workflowItem);
		this.workflowItemBlacklist.add(workflowItem);
	}

	public WorkflowAutoTransitionsMap loadTransitionsMap() {

		var map = new WorkflowAutoTransitionsMap();
		Sql//
			.from(AGWorkflowItem.TABLE)
			.select(AGWorkflowItem.TABLE)
			.whereIf(!workflowItemFilter.isEmpty(), () -> AGWorkflowItem.ID.isIn(workflowItemFilter))
			.whereIf(!workflowItemBlacklist.isEmpty(), () -> AGWorkflowItem.ID.isNotIn(workflowItemBlacklist))
			.join(AGWorkflowItem.WORKFLOW_NODE)
			.where(AGWorkflowNode.ACTIVE)
			.joinReverse(AGWorkflowTransition.SOURCE_NODE)
			.where(AGWorkflowTransition.AUTO_TRANSITION)
			.where(AGWorkflowTransition.ACTIVE)
			.select(AGWorkflowTransition.TABLE)
			.forEach(
				tuple -> map//
					.computeIfAbsent(tuple.get0(), dummy -> new ArrayList<>())
					.add(tuple.get1()));
		return map;
	}
}
