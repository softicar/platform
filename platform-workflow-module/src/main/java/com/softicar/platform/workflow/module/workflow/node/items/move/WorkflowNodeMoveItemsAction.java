package com.softicar.platform.workflow.module.workflow.node.items.move;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.emf.action.IEmfSecondaryAction;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.WorkflowPredicates;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

public class WorkflowNodeMoveItemsAction implements IEmfSecondaryAction<AGWorkflowNode> {

	@Override
	public IEmfPredicate<AGWorkflowNode> getPrecondition() {

		return WorkflowPredicates.WORKFLOW_NODE_CONTAINS_ONE_OR_MORE_ITEMS;
	}

	@Override
	public IEmfPermission<AGWorkflowNode> getRequiredPermission() {

		return WorkflowPermissions.NODE_EDIT;
	}

	@Override
	public IResource getIcon() {

		return WorkflowImages.RIGHT.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.MOVE_WORKFLOW_ITEMS_TO_ANOTHER_WORKFLOW_NODE;
	}

	@Override
	public void handleClick(AGWorkflowNode workflowNode) {

		DomPopupManager//
			.getInstance()
			.getPopup(workflowNode, WorkflowNodeMoveItemsPopup.class, WorkflowNodeMoveItemsPopup::new)
			.open();
	}
}
