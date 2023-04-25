package com.softicar.platform.workflow.module.workflow.item.move;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.message.AGWorkflowItemMessage;
import com.softicar.platform.workflow.module.workflow.item.message.severity.AGWorkflowMessageSeverityEnum;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import java.util.List;
import java.util.stream.Collectors;

public class WorkflowItemMover {

	private final AGWorkflowNode workflowNode;

	public WorkflowItemMover(AGWorkflowNode workflowNode) {

		this.workflowNode = workflowNode;
	}

	public void moveItemsToNode(AGWorkflowNode targetNode) {

		if (!targetNode.isActive()) {
			throw new RuntimeException();
		}

		if (targetNode.equals(workflowNode)) {
			throw new RuntimeException();
		}

		List<AGWorkflowItem> workflowItems = AGWorkflowItem //
			.createSelect()
			.join(AGWorkflowItem.WORKFLOW_NODE)
			.where(AGWorkflowNode.ID.isEqual(workflowNode.getId()))
			.where(AGWorkflowNode.ACTIVE)
			.stream()
			.collect(Collectors.toList());

		String itemMessageText = createItemMessageText(targetNode);

		for (AGWorkflowItem item: workflowItems) {
			lockAndUpdateItem(item, targetNode, itemMessageText);
		}
	}

	private String createItemMessageText(AGWorkflowNode targetNode) {

		return String
			.format(
				"Item was moved from node \"%s\" [%s] of workflow version ID %s to node \"%s\" [%s] of workflow version ID %s.",
				workflowNode.getName(),
				workflowNode.getId(),
				workflowNode.getWorkflowVersionID(),
				targetNode.getName(),
				targetNode.getId(),
				targetNode.getWorkflowVersionID());
	}

	private void lockAndUpdateItem(AGWorkflowItem item, AGWorkflowNode targetNode, String itemMessageText) {

		try (DbTransaction transaction = new DbTransaction()) {
			if (!item.reloadForUpdate()) {
				Log.fwarning("WARNING: Workflow item ID %s could not be reloaded.", item.getId());
			} else if (!workflowNode.equals(item.getWorkflowNode())) {
				Log.fwarning("WARNING: Workflow item \"%s\" is not in workflow node \"%s\" anymore.", item.toDisplay(), workflowNode.toDisplay());
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
