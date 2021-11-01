package com.softicar.platform.workflow.module.workflow.management.display.element;

import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

public class WorkflowStartCircleDisplayElement extends AbstractCircleDisplayElement {

	public WorkflowStartCircleDisplayElement(AGWorkflowNode workflowNode) {

		super(workflowNode);
	}

	@Override
	protected int getY() {

		return workflowNode.getYCoordinate() - DIAMETER - 2 * BORDER - DISTANCE;
	}
}
