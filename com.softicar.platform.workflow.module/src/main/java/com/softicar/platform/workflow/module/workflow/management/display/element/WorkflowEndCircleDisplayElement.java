package com.softicar.platform.workflow.module.workflow.management.display.element;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.workflow.module.WorkflowCssClasses;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

public class WorkflowEndCircleDisplayElement extends AbstractCircleDisplayElement {

	public WorkflowEndCircleDisplayElement(AGWorkflowNode workflowNode) {

		super(workflowNode);
		DomDiv inner = new DomDiv();
		inner.addCssClass(WorkflowCssClasses.WORKFLOW_END_CIRCLE_INNER_CIRCLE);
		appendChild(inner);
	}

	@Override
	protected int getY() {

		return workflowNode.getYCoordinate() + WorkflowNodeDisplayElement.HEIGHT + DISTANCE + 2 * BORDER;
	}
}
