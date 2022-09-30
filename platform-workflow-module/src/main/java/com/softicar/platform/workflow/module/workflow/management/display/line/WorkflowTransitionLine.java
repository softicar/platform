package com.softicar.platform.workflow.module.workflow.management.display.line;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.ui.color.Color;
import com.softicar.platform.dom.parent.DomParentElement;
import com.softicar.platform.workflow.module.workflow.management.display.element.AbstractDisplayElement;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;

public class WorkflowTransitionLine {

	private static final int BORDER = AbstractDisplayElement.BORDER;
	private static final int GAP = 30;
	private static final int HEIGHT = AbstractDisplayElement.HEIGHT;
	private static final int WIDTH = AbstractDisplayElement.WIDTH;
	private final DomParentElement parentElement;
	private final AGWorkflowTransition transition;
	private final INullaryVoidFunction refreshCallback;
	private final String color;
	private final IDisplayString title;

	public WorkflowTransitionLine(DomParentElement parentElement, AGWorkflowTransition transition, INullaryVoidFunction refreshCallback) {

		this.parentElement = parentElement;
		this.transition = transition;
		this.refreshCallback = refreshCallback;
		this.color = determineHtmlColor();
		this.title = transition.toDisplayWithoutId();
	}

	private String determineHtmlColor() {

		if (transition.getHtmlColor() != null) {
			try {
				return Color.parseHtmlCode(transition.getHtmlColor()).toHtml();
			} catch (Exception exception) {
				DevNull.swallow(exception);
			}
		}
		return "#21445b";
	}

	public void draw() {

		AGWorkflowNode sourceNode = transition.getSourceNode();
		AGWorkflowNode targetNode = transition.getTargetNode();
		int halfElementWidth = WIDTH / 2;
		int elementHeight = HEIGHT + BORDER;
		int sourceX = sourceNode.getXCoordinate();
		int sourceY = sourceNode.getYCoordinate();
		int targetX = targetNode.getXCoordinate();
		int targetY = targetNode.getYCoordinate();
		if (sourceNode == targetNode) {
			var halfGap = GAP / 2;
			var centerX = sourceX + WIDTH / 2;
			var centerY = sourceY + HEIGHT / 2;
			var startX = sourceX + WIDTH + BORDER * 2;
			var startY = centerY - halfGap;
			var outerX = startX + halfGap;
			var outerY = sourceY - halfGap;
			var endX = centerX + halfGap;
			var endY = sourceY - BORDER;
			drawLine(startX, startY, outerX, startY);
			drawLine(outerX, startY, outerX, outerY);
			drawLine(outerX, outerY, endX, outerY);
			drawArrow(endX, outerY, endX, endY);
		} else if (sourceY + elementHeight < targetY) {
			//Straigh Top-Down Arrow
			sourceX += halfElementWidth;
			sourceY += elementHeight;
			targetX += halfElementWidth;
			targetY -= BORDER;
			drawArrow(sourceX, sourceY, targetX, targetY);
		} else if (sourceY > targetY + elementHeight) {
			if (sourceX <= targetX) {
				//Left Side Angled Arrow
				sourceX -= BORDER;
				sourceY += HEIGHT / 2;
				targetX -= BORDER;
				targetY += HEIGHT / 2;
				int lineX = sourceX - GAP;
				drawLine(sourceX, sourceY, lineX, sourceY);
				drawLine(lineX, sourceY, lineX, targetY);
				drawArrow(lineX, targetY, targetX, targetY);
			} else {
				//Right Side Angled Arrow
				sourceX += WIDTH + BORDER * 2;
				sourceY += HEIGHT / 2;
				targetX += WIDTH + BORDER * 2;
				targetY += HEIGHT / 2;
				int lineX = sourceX + GAP;
				drawLine(sourceX, sourceY, lineX, sourceY);
				drawLine(lineX, sourceY, lineX, targetY);
				drawArrow(lineX, targetY, targetX, targetY);
			}
		} else {
			if (sourceX < targetX) {
				//Draw arrow from right side
				sourceX += WIDTH + BORDER * 2;
				sourceY += HEIGHT / 2;
				targetX -= BORDER;
				targetY += HEIGHT / 2;
			} else {
				//Draw arrow from left side
				sourceX -= BORDER;
				sourceY += HEIGHT / 2;
				targetX += WIDTH + BORDER * 2;
				targetY += HEIGHT / 2;
			}
			drawArrow(sourceX, sourceY, targetX, targetY);
		}
	}

	private void drawLine(int x0, int y0, int x1, int y1) {

		WorkflowDisplayLine line = new WorkflowDisplayLine(transition, refreshCallback, x0, y0, x1, y1, color, 1);
		line.setTitle(title);
		parentElement.appendChild(line);
	}

	private void drawArrow(int x0, int y0, int x1, int y1) {

		WorkflowDisplayArrow arrow = new WorkflowDisplayArrow(transition, refreshCallback, x0, y0, x1, y1, color, 1);
		arrow.setTitle(title);
		parentElement.appendChild(arrow);
	}
}
