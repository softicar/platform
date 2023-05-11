package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.test.WorkflowTestObjectTable;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import org.junit.Test;

public class WorkflowTransitionActionPermissionTest extends AbstractTestObjectWorkflowTest {

	private final AGWorkflowNode sourceNode;
	private final AGWorkflowNode targetNode;
	private final AGWorkflowItem workflowItem;
	private final WorkflowTestObject workflowObject;
	private final AGWorkflowTransition normalTransition;
	private final AGWorkflowTransition votingTransitionAbsolute;
	private final AGWorkflowTransition votingTransitionPercentage;

	public WorkflowTransitionActionPermissionTest() {

		this.sourceNode = rootNode;
		this.targetNode = insertWorkflowNode(workflowVersion, "Next Node");
		this.workflowItem = insertWorkflowItem(sourceNode);
		this.workflowObject = insertWorkflowTestEntity("Workflow Test Object", workflowItem);

		this.normalTransition = insertWorkflowTransition("T", sourceNode, targetNode, "1", true, WorkflowTestObjectTable.PERMISSION_A);
		this.votingTransitionAbsolute = insertWorkflowTransition("T", sourceNode, targetNode, "2", true, WorkflowTestObjectTable.PERMISSION_A);
		this.votingTransitionPercentage = insertWorkflowTransition("T", sourceNode, targetNode, "70%", true, WorkflowTestObjectTable.PERMISSION_A);
	}

	// ------------------------------ tests ------------------------------ //

	@Test
	public void testWithoutPermissionAndWithoutTask() {

		assertNoPermission(normalTransition);
		assertNoPermission(votingTransitionAbsolute);
		assertNoPermission(votingTransitionPercentage);
	}

	@Test
	public void testWithoutPermissionButWithTask() {

		insertWorkflowTaskOpen(user, workflowItem);

		assertNoPermission(normalTransition);
		assertNoPermission(votingTransitionAbsolute);
		assertNoPermission(votingTransitionPercentage);
	}

	@Test
	public void testWithPermissionButWithoutTask() {

		insertPermissionA(user, workflowObject);

		assertPermission(normalTransition);
		assertNoPermission(votingTransitionAbsolute);
		assertNoPermission(votingTransitionPercentage);
	}

	@Test
	public void testWithPermissionAndWithTask() {

		insertPermissionA(user, workflowObject);
		insertWorkflowTaskOpen(user, workflowItem);

		assertPermission(normalTransition);
		assertPermission(votingTransitionAbsolute);
		assertPermission(votingTransitionPercentage);
	}

	// ------------------------------ private ------------------------------ //

	private void assertPermission(AGWorkflowTransition transition) {

		assertTrue(testPermission(transition));
	}

	private void assertNoPermission(AGWorkflowTransition transition) {

		assertFalse(testPermission(transition));
	}

	private boolean testPermission(AGWorkflowTransition transition) {

		return new WorkflowTransitionActionPermission<WorkflowTestObject>(transition).test(workflowObject, user);
	}
}
