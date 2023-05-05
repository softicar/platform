package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.test.FalsePrecondition;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
		this.autoTransitionA = insertWorkflowAutoTransition("Auto Transition A", rootNode, nodeA);
		this.autoTransitionB = insertWorkflowAutoTransition("Auto Transition B", rootNode, nodeB);
		this.autoTransitionC = insertWorkflowAutoTransition("Auto Transition C", nodeA, nodeB);

		WorkflowTestObject testObject = new WorkflowTestObject()//
			.setName("TestName")
			.save();
		workflow.startWorkflow(testObject);
		this.item = testObject.getWorkflowItem();
	}

	@Test
	public void testAutoTransitionExecution() {

		new WorkflowAutoTransitionExecutor(item).evaluateAndExecute(Arrays.asList(autoTransitionA, autoTransitionB));
		assertEquals(nodeA, item.getWorkflowNode());
	}

	@Test
	public void testNoAutoTransitionExecution() {

		new WorkflowAutoTransitionExecutor(item).evaluateAndExecute(Collections.singletonList(autoTransitionB));
		assertEquals(rootNode, item.getWorkflowNode());
	}

	@Test
	public void testWrongAutoTransition() {

		new WorkflowAutoTransitionExecutor(item).evaluateAndExecute(Arrays.asList(autoTransitionA, autoTransitionB, autoTransitionC));
		assertEquals(rootNode, item.getWorkflowNode());
	}

	@Test
	public void testWithStaleWorkflowItem() {

		assertEquals(rootNode, item.getWorkflowNode());

		// Update database records without updating the AG cache
		AGWorkflowItem.TABLE.createUpdate().set(AGWorkflowItem.WORKFLOW_NODE, nodeB).execute();

		assertEquals(rootNode, item.getWorkflowNode());

		new WorkflowAutoTransitionExecutor(item).evaluateAndExecute(List.of(autoTransitionA));
		assertEquals(nodeB, item.getWorkflowNode());
	}
}
