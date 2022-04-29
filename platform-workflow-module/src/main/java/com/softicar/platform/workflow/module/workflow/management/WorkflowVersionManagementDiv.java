package com.softicar.platform.workflow.module.workflow.management;

import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.object.table.IEmfObjectTable;
import com.softicar.platform.workflow.module.WorkflowCssClasses;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowRoles;
import com.softicar.platform.workflow.module.workflow.WorkflowPredicates;
import com.softicar.platform.workflow.module.workflow.management.display.element.AbstractDisplayElement;
import com.softicar.platform.workflow.module.workflow.management.display.element.WorkflowEndCircleDisplayElement;
import com.softicar.platform.workflow.module.workflow.management.display.element.WorkflowNodeDisplayElement;
import com.softicar.platform.workflow.module.workflow.management.display.element.WorkflowStartCircleDisplayElement;
import com.softicar.platform.workflow.module.workflow.management.display.line.WorkflowDisplayArrow;
import com.softicar.platform.workflow.module.workflow.management.display.line.WorkflowTransitionLine;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import java.util.List;

public class WorkflowVersionManagementDiv extends DomDiv {

	private static final Integer PADDING = 60;
	private static final Integer MIN_WIDTH = 360;
	private static final Integer MIN_HEIGHT = 280;
	private final AGWorkflowVersion workflowVersion;
	private List<AGWorkflowNode> workflowNodes;

	public WorkflowVersionManagementDiv(AGWorkflowVersion workflowVersion) {

		this.workflowVersion = workflowVersion;
		addCssClass(WorkflowCssClasses.WORKFLOW_MANAGEMENT_DIV);
		if (!WorkflowPredicates.WORKFLOW_VERSION_FINALIZED.test(workflowVersion)
				&& WorkflowRoles.ADMINISTRATOR.test(workflowVersion.getWorkflow().getModuleInstance(), CurrentBasicUser.get())) {
			var actionBar = appendChild(new DomActionBar());
			actionBar.appendChild(new CreateEntityButton<>(AGWorkflowNode.TABLE, WorkflowI18n.ADD_NEW_NODE));
			actionBar.appendChild(new CreateEntityButton<>(AGWorkflowTransition.TABLE, WorkflowI18n.ADD_NEW_TRANSITION));
			appendNewChild(DomElementTag.HR);
		}
		repaint();
	}

	private void repaint() {

		workflowNodes = workflowVersion//
			.getAllActiveWorkflowNodes();
		resizeManagementDiv();
		drawWorkflowNodes();
		drawStartAndEndNodes();
		drawTransitions();
	}

	private void resizeManagementDiv() {

		Pair<Integer, Integer> workflowWidthAndHeight = new Pair<>(0, 0);
		workflowVersion//
			.getAllActiveWorkflowNodes()
			.stream()
			.forEach(node -> determineWidthAndHeight(node, workflowWidthAndHeight));
		Integer width = workflowWidthAndHeight.getFirst() + PADDING;
		Integer height = workflowWidthAndHeight.getSecond() + PADDING;
		if (width > MIN_WIDTH) {
			setStyle(CssStyle.MIN_WIDTH, width + "px");
		}
		if (height > MIN_HEIGHT) {
			setStyle(CssStyle.MIN_HEIGHT, height + "px");
		}
	}

	private void determineWidthAndHeight(AGWorkflowNode node, Pair<Integer, Integer> workflowWidthAndHeight) {

		Integer xCoordinateEnd = node.getXCoordinate() + AbstractDisplayElement.WIDTH;
		if (workflowWidthAndHeight.getFirst() < xCoordinateEnd) {
			workflowWidthAndHeight.setFirst(xCoordinateEnd);
		}
		Integer yCoordinateEnd = node.getYCoordinate() + AbstractDisplayElement.HEIGHT;
		if (workflowWidthAndHeight.getSecond() < yCoordinateEnd) {
			workflowWidthAndHeight.setSecond(yCoordinateEnd);
		}
	}

	private void drawWorkflowNodes() {

		workflowNodes//
			.stream()
			.forEach(node -> appendChild(new WorkflowNodeDisplayElement(node, this::repaint)));
	}

	private void drawStartAndEndNodes() {

		if (workflowVersion.getRootNode() != null) {
			WorkflowStartCircleDisplayElement startCircle = new WorkflowStartCircleDisplayElement(workflowVersion.getRootNode());

			appendChild(
				createArrow(
					startCircle.getBottomConnectorX(),
					startCircle.getBottomConnectorY(),
					startCircle.getBottomConnectorX(),
					startCircle.getBottomConnectorY() + WorkflowStartCircleDisplayElement.DISTANCE - 2));
			appendChild(startCircle);
		}
		for (AGWorkflowNode workflowNode: workflowNodes) {
			if (workflowNode.isEndNode()) {
				WorkflowEndCircleDisplayElement endCircle = new WorkflowEndCircleDisplayElement(workflowNode);
				appendChild(
					createArrow(
						endCircle.getTopConnectorX(),
						endCircle.getTopConnectorY() - WorkflowEndCircleDisplayElement.DISTANCE + 1,
						endCircle.getTopConnectorX(),
						endCircle.getTopConnectorY() - 1));
				appendChild(endCircle);
			}
		}
	}

	private void drawTransitions() {

		workflowVersion//
			.getAllActiveTransitions()
			.forEach(transition -> new WorkflowTransitionLine(this, transition, this::repaint).draw());
	}

	private WorkflowDisplayArrow createArrow(int firstX, int firstY, int secondX, int secondY) {

		return new WorkflowDisplayArrow(firstX, firstY, secondX, secondY, "#21445b", 1);
	}

	private class CreateEntityButton<E extends IEmfObject<E>> extends DomPopupButton {

		public <T extends IEmfObjectTable<E, AGWorkflowVersion>> CreateEntityButton(T table, IDisplayString label) {

			setPopupFactory(() -> new RefreshAfterHidePopup<>(table));
			setIcon(EmfImages.ENTITY_CREATE.getResource());
			setLabel(label);
		}
	}

	private class RefreshAfterHidePopup<E extends IEmfObject<E>> extends EmfFormPopup<E> {

		public <T extends IEmfObjectTable<E, AGWorkflowVersion>> RefreshAfterHidePopup(T table) {

			super(table.createEntity(workflowVersion));
			setDirectEditing(true);
			configuration.setCallbackBeforeClose(WorkflowVersionManagementDiv.this::repaint);
		}
	}
}
