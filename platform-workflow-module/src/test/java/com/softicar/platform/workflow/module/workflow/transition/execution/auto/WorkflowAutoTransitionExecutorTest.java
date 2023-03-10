package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.test.FalsePrecondition;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

public class WorkflowAutoTransitionExecutorTest extends AbstractTestObjectWorkflowTest {

	private final AGWorkflowNode nodeA;
	private final AGWorkflowNode nodeB;
	private final AGWorkflowTransition autoTransitionA;
	private final AGWorkflowTransition autoTransitionB;
	private final AGWorkflowTransition autoTransitionC;
	private final AGWorkflowItem item;

	public WorkflowAutoTransitionExecutorTest() {

		this.nodeA = insertWorkflowNode(workflowVersion, "A");
		this.nodeB = insertWorkflowNode(workflowVersion, "B");
		insertWorkflowNodePrecondition(nodeB, FalsePrecondition.class);
		this.autoTransitionA = insertWorkflowAutoTransition(rootNode, nodeA, "Auto Transition A");
		this.autoTransitionB = insertWorkflowAutoTransition(rootNode, nodeB, "Auto Transition B");
		this.autoTransitionC = insertWorkflowAutoTransition(nodeA, nodeB, "Auto Transition C");

		WorkflowTestObject testObject = new WorkflowTestObject()//
			.setName("TestName")
			.save();
		workflow.startWorkflow(testObject);
		this.item = testObject.getWorkflowItem();
	}

	@Test
	public void testAutoTransitionExecution() {

		new WorkflowAutoTransitionExecutor(item, Arrays.asList(autoTransitionA, autoTransitionB)).evaluateAndExecute();
		assertEquals(nodeA, item.getWorkflowNode());
	}

	@Test
	public void testNoAutoTransitionExecution() {

		new WorkflowAutoTransitionExecutor(item, Collections.singletonList(autoTransitionB)).evaluateAndExecute();
		assertEquals(rootNode, item.getWorkflowNode());
	}

	@Test
	public void testWrongAutoTransition() {

		new WorkflowAutoTransitionExecutor(item, Arrays.asList(autoTransitionA, autoTransitionB, autoTransitionC)).evaluateAndExecute();
		assertEquals(rootNode, item.getWorkflowNode());
	}

	@Test
	public void testWithOutdatedWorkflowItem() {

		assertEquals(rootNode, item.getWorkflowNode());

		// Update of database records without updating the AG-class cache
		AGWorkflowItem.TABLE.createUpdate().set(AGWorkflowItem.WORKFLOW_NODE, nodeB).execute();

		assertEquals(rootNode, item.getWorkflowNode());

		new WorkflowAutoTransitionExecutor(item, Collections.singletonList(autoTransitionA)).evaluateAndExecute();
		assertEquals(nodeB, item.getWorkflowNode());
	}
}
