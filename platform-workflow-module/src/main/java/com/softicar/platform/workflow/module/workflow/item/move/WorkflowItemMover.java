package com.softicar.platform.workflow.module.workflow.item.move;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.message.AGWorkflowItemMessage;
import com.softicar.platform.workflow.module.workflow.item.message.severity.AGWorkflowMessageSeverityEnum;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import java.util.List;
import java.util.stream.Collectors;

public class WorkflowItemMover {

	private final AGWorkflowNode sourceNode;

	public WorkflowItemMover(AGWorkflowNode workflowNode) {

		this.sourceNode = workflowNode;
	}

	public void moveItemsToNode(AGWorkflowNode targetNode) {

		if (!targetNode.isActive()) {
			throw new SofticarDeveloperException("targetNode must be active");
		} else if (sourceNode.equals(targetNode)) {
			Log.finfo("Moving isn't necessary.");
		} else {
			String itemMessageText = createItemMessageText(targetNode);
			moveItemsToNode(targetNode, itemMessageText);
		}
	}

	private String createItemMessageText(AGWorkflowNode targetNode) {

		return String
			.format(
				"Item was moved from node \"%s\" [%s] of workflow version ID %s to node \"%s\" [%s] of workflow version ID %s.",
				sourceNode.getName(),
				sourceNode.getId(),
				sourceNode.getWorkflowVersionID(),
				targetNode.getName(),
				targetNode.getId(),
				targetNode.getWorkflowVersionID());
	}

	private void moveItemsToNode(AGWorkflowNode targetNode, String itemMessageText) {

		for (AGWorkflowItem item: loadWorkflowItems()) {
			lockAndUpdateItem(item, targetNode, itemMessageText);
		}
	}

	private List<AGWorkflowItem> loadWorkflowItems() {

		return AGWorkflowItem //
			.createSelect()
			.join(AGWorkflowItem.WORKFLOW_NODE)
			.where(AGWorkflowNode.ID.isEqual(sourceNode.getId()))
			.stream()
			.collect(Collectors.toList());
	}

	private void lockAndUpdateItem(AGWorkflowItem item, AGWorkflowNode targetNode, String itemMessageText) {

		try (DbTransaction transaction = new DbTransaction()) {
			boolean reload = item.reloadForUpdate();
			AGWorkflowNode currentNode = item.getWorkflowNode();

			if (!reload) {
				Log.fwarning("WARNING: Workflow item ID %s could not be reloaded.", item.getId());
			} else if (currentNode.equals(targetNode)) {
				Log.finfo("Workflow item \"%s\" is already in target workflow node \"%s\".", item.toDisplay(), targetNode.toDisplay());
			} else if (!currentNode.equals(sourceNode)) {
				Log.finfo("Workflow item \"%s\" is not in source workflow node \"%s\" anymore.", item.toDisplay(), sourceNode.toDisplay());
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
