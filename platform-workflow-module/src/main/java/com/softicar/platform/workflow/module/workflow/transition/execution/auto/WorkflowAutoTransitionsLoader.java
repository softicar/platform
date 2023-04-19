package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

class WorkflowAutoTransitionsLoader {

	public List<AGWorkflowTransition> loadTransitions(AGWorkflowItem workflowItem) {

		Objects.requireNonNull(workflowItem);
		return loadTransitionsByItem(workflowItem)//
			.stream()
			.map(tuple -> tuple.get1())
			.collect(Collectors.toList());
	}

	public Map<AGWorkflowItem, List<AGWorkflowTransition>> loadTransitionsByItem() {

		var resultMap = new TreeMap<AGWorkflowItem, List<AGWorkflowTransition>>();
		loadTransitionsByItem(null)
			.forEach(
				tuple -> resultMap//
					.computeIfAbsent(tuple.get0(), dummy -> new ArrayList<>())
					.add(tuple.get1()));
		return resultMap;
	}

	private List<Tuple2<AGWorkflowItem, AGWorkflowTransition>> loadTransitionsByItem(AGWorkflowItem workflowItem) {

		return Sql//
			.from(AGWorkflowItem.TABLE)
			.select(AGWorkflowItem.TABLE)
			.whereIf(workflowItem != null, () -> AGWorkflowItem.ID.isEqual(workflowItem))
			.join(AGWorkflowItem.WORKFLOW_NODE)
			.where(AGWorkflowNode.ACTIVE)
			.joinReverse(AGWorkflowTransition.SOURCE_NODE)
			.where(AGWorkflowTransition.AUTO_TRANSITION)
			.where(AGWorkflowTransition.ACTIVE)
			.select(AGWorkflowTransition.TABLE)
			.list();
	}
}
