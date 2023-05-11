package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.test.WorkflowTestObjectTable;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import org.junit.Test;

public class WorkflowStarterTest extends AbstractTestObjectWorkflowTest {

	@Test
	public void testStartWorkflow() {

		// setup
		AGWorkflowNode nodeA = insertWorkflowNode(workflowVersion, "A");
		AGWorkflowNode nodeB = insertWorkflowNode(workflowVersion, "B");
		AGWorkflowNode nodeC = insertWorkflowNode(workflowVersion, "C");
		AGWorkflowNode nodeD = insertWorkflowNode(workflowVersion, "D");
		insertWorkflowAutoTransition("Auto 1", rootNode, nodeA);
		insertWorkflowAutoTransition("Auto 2", nodeA, nodeB);
		insertWorkflowAutoTransition("Auto 3", nodeB, nodeC);
		insertWorkflowTransition("Manual Transition", nodeC, nodeD, "1", false, WorkflowTestObjectTable.PERMISSION_A);

		var testObject = new WorkflowTestObject()//
			.setName("TestName")
			.save();
		insertPermissionA(user, testObject);

		// assert setup
		assertNull(testObject.getWorkflowItem());

		// execute
		new WorkflowStarter(workflow).startWorkflow(testObject);

		// assert result
		AGWorkflowItem item = testObject.getWorkflowItem();
		assertNotNull(item);
		assertSame(workflow, item.getWorkflow());
		assertSame(nodeC, item.getWorkflowNode());

		var task = assertOne(AGWorkflowTask.TABLE.loadAll());
		assertSame(item, task.getWorkflowItem());
		assertSame(user, task.getUser());
		assertFalse(task.isClosed());
	}
}
