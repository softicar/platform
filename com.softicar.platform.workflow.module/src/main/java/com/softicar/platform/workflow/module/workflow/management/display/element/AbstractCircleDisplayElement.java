package com.softicar.platform.workflow.module.workflow.management.display.element;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.workflow.module.WorkflowCssClasses;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

public abstract class AbstractCircleDisplayElement extends AbstractDisplayElement {

	public static final int DISTANCE = 20;
	protected static final int DIAMETER = 20;
	protected final AGWorkflowNode workflowNode;
	private final int x;
	private final int y;

	public AbstractCircleDisplayElement(AGWorkflowNode workflowNode) {

		this.workflowNode = workflowNode;
		this.x = getX();
		this.y = getY();
		setStyle(CssStyle.LEFT, x + "px");
		setStyle(CssStyle.TOP, y + "px");
		addCssClass(WorkflowCssClasses.WORKFLOW_CIRCLE_DISPLAY_ELEMENT);
	}

	public int getBottomConnectorX() {

		return x + DIAMETER / 2 + BORDER - 1;
	}

	public int getBottomConnectorY() {

		return y + DIAMETER + 2 * BORDER;
	}

	public int getTopConnectorX() {

		return x + DIAMETER / 2 + BORDER - 1;
	}

	public int getTopConnectorY() {

		return y - 1;
	}

	protected int getX() {

		return workflowNode.getXCoordinate() + WorkflowNodeDisplayElement.WIDTH / 2 - DIAMETER / 2;
	}

	protected abstract int getY();
}
