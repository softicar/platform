package com.softicar.platform.workflow.module.workflow.item.move;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.message.AGWorkflowItemMessage;
import com.softicar.platform.workflow.module.workflow.item.message.severity.AGWorkflowMessageSeverityEnum;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

public class WorkflowItemsMover {

	private final AGWorkflowNode sourceNode;

	public WorkflowItemsMover(AGWorkflowNode sourceNode) {

		this.sourceNode = sourceNode;
	}

	public void moveItemsToNode(AGWorkflowNode targetNode) {

		if (!targetNode.isActive()) {
			throw new SofticarUserException(WorkflowI18n.TARGET_NODE_MUST_BE_ACTIVE);
		} else if (sourceNode.equals(targetNode)) {
			throw new SofticarUserException(WorkflowI18n.TARGET_NODE_MUST_BE_DIFFERENT_THAN_SOURCE_NODE);
		} else {
			String itemMessageText = createItemMessageText(targetNode);
			moveItemsToNode(targetNode, itemMessageText);
		}
	}

	private String createItemMessageText(AGWorkflowNode targetNode) {

		return String
			.format(
				"Item was moved from node '%s' [%s] of workflow version ID %s to node '%s' [%s] of workflow version ID %s.",
				sourceNode.getName(),
				sourceNode.getId(),
				sourceNode.getWorkflowVersionID(),
				targetNode.getName(),
				targetNode.getId(),
				targetNode.getWorkflowVersionID());
	}

	private void moveItemsToNode(AGWorkflowNode targetNode, String itemMessageText) {

		for (AGWorkflowItem item: sourceNode.getAllWorkflowItems()) {
			lockAndUpdateItem(item, targetNode, itemMessageText);
		}
	}

	private void lockAndUpdateItem(AGWorkflowItem item, AGWorkflowNode targetNode, String itemMessageText) {

		try (DbTransaction transaction = new DbTransaction()) {
			boolean reload = item.reloadForUpdate();
			AGWorkflowNode currentNode = item.getWorkflowNode();

			if (!reload) {
				Log.fwarning("WARNING: Workflow item ID '%s' could not be reloaded.", item.getId());
			} else if (currentNode.equals(targetNode)) {
				Log.finfo("Workflow item '%s' is already in target workflow node '%s'.", item.toDisplay(), targetNode.toDisplay());
			} else if (!currentNode.equals(sourceNode)) {
				Log.finfo("Workflow item '%s' is not in source workflow node '%s' anymore.", item.toDisplay(), sourceNode.toDisplay());
			} else {
				updateItemAndInsertMessage(item, targetNode, itemMessageText);
			}
			transaction.commit();
		}
	}

	private void updateItemAndInsertMessage(AGWorkflowItem item, AGWorkflowNode targetNode, String itemMessageText) {

		item.setWorkflowNode(targetNode).save();
		new AGWorkflowItemMessage()//
			.setWorkflowItem(item)
			.setSeverity(AGWorkflowMessageSeverityEnum.INFO.getRecord())
			.setText(itemMessageText)
			.save();
	}
}
