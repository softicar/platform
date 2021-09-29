package com.softicar.platform.workflow.module.workflow.node.action;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import java.util.Objects;
import java.util.Set;

public class WorkflowNodeActionPredicate<R extends IWorkflowableObject<R>> implements IEmfPredicate<R> {

	private final Set<AGWorkflowNode> workflowNodes;

	public WorkflowNodeActionPredicate(AGWorkflowNode...nodes) {

		this(Set.of(nodes));
	}

	public WorkflowNodeActionPredicate(Set<AGWorkflowNode> workflowNodes) {

		this.workflowNodes = Objects.requireNonNull(workflowNodes);
	}

	@Override
	public boolean test(R tableRow) {

		return tableRow//
			.getWorkflowItemAsOptional()
			.map(AGWorkflowItem::getWorkflowNode)
			.filter(Objects::nonNull)
			.map(workflowNodes::contains)
			.orElse(false);
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.WORKFLOW_ACTION_AVAILABLE;
	}
}
