package com.softicar.platform.workflow.module.workflow.management.display.element;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.IDomDropEvent;
import com.softicar.platform.dom.event.IDomDropEventHandler;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.workflow.module.WorkflowCssClasses;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

public class WorkflowNodeDisplayElement extends AbstractDisplayElement implements IDomDropEventHandler {

	private final AGWorkflowNode workflowNode;
	private final INullaryVoidFunction refreshCallback;

	public WorkflowNodeDisplayElement(AGWorkflowNode workflowNode, INullaryVoidFunction refreshCallback) {

		this.workflowNode = workflowNode;
		this.refreshCallback = refreshCallback;
		addCssClass(WorkflowCssClasses.WORKFLOW_NODE);
		if (WorkflowPermissions.ADMINISTRATOR.test(workflowNode.getWorkflowVersion().getWorkflow().getModuleInstance(), CurrentBasicUser.get())) {
			applyDraggableStyle();
		}
		setPositionStyle();

		appendChild(new EditButton());
		appendNewChild(DomElementTag.BR);
		addSeperator();
		addNodeName();
	}

	@Override
	public void handleDrop(IDomDropEvent dropParameters) {

		int x = (int) Math.round(dropParameters.getDropX() / 10.0) * 10;
		int y = (int) Math.round(dropParameters.getDropY() / 10.0) * 10;
		if (x > 0 && y > 0) {
			workflowNode.setXCoordinate(x);
			workflowNode.setYCoordinate(y);
			workflowNode.save();
		}
		refreshCallback.apply();
	}

	private void setPositionStyle() {

		setStyle(CssStyle.LEFT, workflowNode.getXCoordinate() + "px");
		setStyle(CssStyle.TOP, workflowNode.getYCoordinate() + "px");
	}

	private void applyDraggableStyle() {

		getDomEngine().makeDraggable(this, this);
		addCssClass(DomCssPseudoClasses.DRAGGABLE);
	}

	private void addSeperator() {

		DomDiv separator = appendChild(new DomDiv());
		separator.addCssClass(WorkflowCssClasses.WORKFLOW_NODE_SEPARATOR);
	}

	private void addNodeName() {

		DomDiv nodeNameDiv = appendChild(new DomDiv());
		nodeNameDiv.addCssClass(WorkflowCssClasses.WORKFLOW_NODE_NAME);
		nodeNameDiv.appendText(workflowNode.getName());
	}

	private class EditButton extends DomButton {

		public EditButton() {

			setIcon(EmfImages.ENTITY_EDIT.getResource());
			setLabel(IDisplayString.format("%s", workflowNode.getId()));
			setTitle(WorkflowI18n.EDIT.concatEllipsis());
			setClickCallback(() -> new WorkflowNodeEditPopup(workflowNode).open());
		}

		private class WorkflowNodeEditPopup extends EmfFormPopup<AGWorkflowNode> {

			public WorkflowNodeEditPopup(AGWorkflowNode workflowNode) {

				super(workflowNode);
				setDirectEditing(false);
				configuration.setCallbackBeforeClose(refreshCallback);
			}
		}
	}
}
