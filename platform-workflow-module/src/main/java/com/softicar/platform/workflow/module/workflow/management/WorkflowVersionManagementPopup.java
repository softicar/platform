package com.softicar.platform.workflow.module.workflow.management;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import java.util.function.Supplier;

public class WorkflowVersionManagementPopup extends DomPopup implements IDomRefreshBusListener {

	private final AGWorkflowVersion workflowVersion;
	private final Supplier<AGWorkflowNode> currentNodeSupplier;

	public WorkflowVersionManagementPopup(AGWorkflowVersion workflowVersion) {

		this(workflowVersion, () -> null);
	}

	public WorkflowVersionManagementPopup(AGWorkflowItem workflowItem) {

		this(workflowItem.getWorkflowNode().getWorkflowVersion(), workflowItem::getWorkflowNode);
	}

	public WorkflowVersionManagementPopup(AGWorkflowNode currentNode) {

		this(currentNode.getWorkflowVersion(), () -> currentNode);
	}

	public WorkflowVersionManagementPopup(AGWorkflowVersion workflowVersion, Supplier<AGWorkflowNode> currentNodeSupplier) {

		this.workflowVersion = workflowVersion;
		this.currentNodeSupplier = currentNodeSupplier;
		setCaption(WorkflowI18n.MANAGE_WORKFLOW);
		setSubCaptionWithVersionType(workflowVersion.getVersionType());

		refresh();
	}

	private void setSubCaptionWithVersionType(IDisplayString versionType) {

		setSubCaption(
			workflowVersion//
				.toDisplay()
				.concatColon()
				.concatSpace()
				.concat(versionType));
	}

	@Override
	public void refresh(IDomRefreshBusEvent event) {

		refresh();
	}

	private void refresh() {

		removeChildren();
		appendChild(new WorkflowVersionManagementDiv(workflowVersion, currentNodeSupplier.get()));
	}
}
