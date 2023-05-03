package com.softicar.platform.workflow.module.workflow.item.move;

import com.softicar.platform.db.runtime.utils.DbAssertUtils;
import com.softicar.platform.workflow.module.AbstractWorkflowTest;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.test.WorkflowTestObjectTableReferencePoint;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.message.AGWorkflowItemMessage;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Test;

public class WorkflowItemsMoverTest extends AbstractWorkflowTest {

	private final AGWorkflowNode rootNode;
	private final AGWorkflowNode nodeA;
	private final AGWorkflowNode nodeB;
	private final AGWorkflowItem itemOne;
	private final AGWorkflowItem itemTwo;
	private final AGWorkflowItem itemThree;

	public WorkflowItemsMoverTest() {

		AGWorkflow workflow = insertWorkflow(moduleInstance, "Test Workflow", WorkflowTestObjectTableReferencePoint.class);
		AGWorkflowVersion workflowVersion = insertWorkflowVersion(workflow, false);
		this.rootNode = insertWorkflowNode(workflowVersion, "Root Node");

		workflow.setCurrentVersion(workflowVersion).save();
		workflowVersion.setRootNode(rootNode).save();

		this.nodeA = insertWorkflowNode(workflowVersion, "Node A");
		this.nodeB = insertWorkflowNode(workflowVersion, "Node B");

		this.itemOne = createWorkflowItem(workflow, "Test Object 1");
		this.itemTwo = createWorkflowItem(workflow, "Test Object 2");
		this.itemThree = createWorkflowItem(workflow, "Test Object 3");
	}

	private AGWorkflowItem createWorkflowItem(AGWorkflow workflow, String name) {

		WorkflowTestObject testObjectA = new WorkflowTestObject()//
			.setName(name)
			.save();
		workflow.startWorkflow(testObjectA);
		return testObjectA.getWorkflowItem();
	}

	@Test
	public void testMoveItemsToNode() {

		assertCount(0, AGWorkflowItemMessage.createSelect());

		new WorkflowItemsMover(rootNode).moveItemsToNode(nodeA);

		assertEquals(nodeA, itemOne.getWorkflowNode());
		assertEquals(nodeA, itemTwo.getWorkflowNode());
		assertEquals(nodeA, itemThree.getWorkflowNode());

		List<AGWorkflowItemMessage> messageRecords = assertCount(3, AGWorkflowItemMessage.createSelect());

		Map<AGWorkflowItem, String> itemMessageMap =
				messageRecords.stream().collect(Collectors.toMap(AGWorkflowItemMessage::getWorkflowItem, AGWorkflowItemMessage::getText));

		String expectedText = String
			.format(
				"Item was moved from node 'Root Node [%s]' of version '%s' to 'Node A [%s]' of version '%s'.",
				rootNode.getId(),
				rootNode.getWorkflowVersionID(),
				nodeA.getId(),
				nodeA.getWorkflowVersionID());

		assertEquals(expectedText, itemMessageMap.get(itemOne));
		assertEquals(expectedText, itemMessageMap.get(itemTwo));
		assertEquals(expectedText, itemMessageMap.get(itemThree));
	}

	@Test
	public void testMoveItemsToNodeWithItemsAtDifferentNodes() {

		itemOne.setWorkflowNode(nodeA).save();
		itemTwo.setWorkflowNode(nodeB).save();

		assertEquals(rootNode, itemThree.getWorkflowNode());

		new WorkflowItemsMover(rootNode).moveItemsToNode(nodeA);

		assertEquals(nodeA, itemOne.getWorkflowNode());
		assertEquals(nodeB, itemTwo.getWorkflowNode());
		assertEquals(nodeA, itemThree.getWorkflowNode());

		AGWorkflowItemMessage messageRecord = DbAssertUtils.assertOne(AGWorkflowItemMessage.TABLE);

		assertEquals(itemThree, messageRecord.getWorkflowItem());

		String expectedText = String
			.format(
				"Item was moved from node 'Root Node [%s]' of version '%s' to 'Node A [%s]' of version '%s'.",
				rootNode.getId(),
				rootNode.getWorkflowVersionID(),
				nodeA.getId(),
				nodeA.getWorkflowVersionID());

		assertEquals(expectedText, messageRecord.getText());
	}

	@Test
	public void testMoveItemsToNodeWithInactiveTargetNode() {

		nodeA.setActive(false).save();
		assertExceptionMessage(WorkflowI18n.TARGET_NODE_MUST_BE_ACTIVE.toString(), () -> new WorkflowItemsMover(rootNode).moveItemsToNode(nodeA));
	}

	@Test
	public void testMoveItemsWithSourceEqualsTarget() {

		assertCount(3, AGWorkflowItem.createSelect().where(AGWorkflowItem.WORKFLOW_NODE.isEqual(rootNode)));

		assertExceptionMessage(
			WorkflowI18n.TARGET_NODE_MUST_BE_DIFFERENT_THAN_SOURCE_NODE.toString(),
			() -> new WorkflowItemsMover(rootNode).moveItemsToNode(rootNode));
	}

	@Test
	public void testMoveItemsToNodeWithStaleWorkflowItem() {

		itemOne.setWorkflowNode(nodeA).save();

		// Put all workflow items on nodeB without updating the AG cache
		AGWorkflowItem.TABLE.createUpdate().set(AGWorkflowItem.WORKFLOW_NODE, nodeB).execute();

		assertEquals(nodeA, itemOne.getWorkflowNode());

		new WorkflowItemsMover(nodeA).moveItemsToNode(rootNode);

		assertCount(3, AGWorkflowItem.createSelect().where(AGWorkflowItem.WORKFLOW_NODE.isEqual(nodeB)));
		assertCount(0, AGWorkflowItemMessage.createSelect());
	}
}
