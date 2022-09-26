package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.test.WorkflowTestObjectTable;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.transition.execution.AGWorkflowTransitionExecution;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class WorkflowTransitionActionExecutorTest extends AbstractTestObjectWorkflowTest {

	private static final String PREMISSION = "A";
	private final AGWorkflowNode nextNode;
	private final AGWorkflowTransition transition;
	private final AGWorkflowItem workflowItem;
	private final WorkflowTestObject workflowObject;
	private final List<SideEffectExecution> sideEffectExecutions;

	public WorkflowTransitionActionExecutorTest() {

		this.nextNode = insertWorkflowNode(workflowVersion, "Next Node");
		this.transition = insertWorkflowTransition("Transition", rootNode, nextNode, "100%", true, WorkflowTestObjectTable.PERMISSION_A);
		this.workflowItem = insertWorkflowItem(rootNode);
		this.workflowObject = insertWorkflowTestObject("Workflow Test Object", workflowItem);
		this.sideEffectExecutions = new ArrayList<>();

		// setup side-effect execution
		transition.setSideEffect(WorkflowTransitionTestSideEffect.class);
		WorkflowTransitionTestSideEffect.setConsumer(this::addSideEffectExecution);
	}

	@Test
	public void testSideEffectExecution() {

		var user1 = insertUserPermissionAndTask("User #1", PREMISSION);
		var user2 = insertUserPermissionAndTask("User #2", PREMISSION);
		var user3 = insertUserPermissionAndTask("User #3", PREMISSION);

		new WorkflowTransitionActionExecutor(workflowItem, transition, user1).execute();
		assertExecutedSideEffects(0);

		new WorkflowTransitionActionExecutor(workflowItem, transition, user2).execute();
		assertExecutedSideEffects(0);

		new WorkflowTransitionActionExecutor(workflowItem, transition, user3).execute();
		assertExecutedSideEffects(1);

		// assert proper side-effect execution
		var execution = assertOne(sideEffectExecutions);
		execution.assertWorkflowObject(workflowObject);
		execution.assertWorkflowTransition(transition);
		execution.assertWorkflowNode(nextNode);

		assertSame(nextNode, workflowItem.getWorkflowNode());
	}

	@Test
	public void testSideEffectExecutionWithExceptionOnSideEffect() {

		var exceptionMessage = "Intentional exception for side-effect.";
		var user = insertUserPermissionAndTask("User", PREMISSION);

		WorkflowTransitionTestSideEffect.setConsumer((object, transition) -> {
			throw new RuntimeException(exceptionMessage);
		});

		assertExceptionMessage(exceptionMessage, () -> {
			new WorkflowTransitionActionExecutor(workflowItem, transition, user).execute();
		});

		assertSame(rootNode, workflowItem.getWorkflowNode());
		assertEmpty(AGWorkflowTransitionExecution.TABLE.loadAll());
		assertOne(AGWorkflowTask.getOpenWorkflowTasks(user, workflowItem));
	}

	private AGUser insertUserPermissionAndTask(String username, String permission) {

		var user = insertUser(username);
		workflowObject.addPermissionAssignment(user, permission);
		insertWorkflowTaskOpen(user, workflowItem);
		return user;
	}

	private void addSideEffectExecution(WorkflowTestObject object, AGWorkflowTransition transition) {

		sideEffectExecutions.add(new SideEffectExecution(object, transition));
	}

	private void assertExecutedSideEffects(int expectedCount) {

		assertEquals("executed side-effects", expectedCount, sideEffectExecutions.size());
	}

	private static class SideEffectExecution {

		private final WorkflowTestObject workflowObject;
		private final AGWorkflowTransition workflowTransition;
		private final AGWorkflowNode workflowNode;

		public SideEffectExecution(WorkflowTestObject object, AGWorkflowTransition transition) {

			this.workflowObject = object;
			this.workflowTransition = transition;
			this.workflowNode = object.getWorkflowItem().getWorkflowNode();
		}

		public void assertWorkflowObject(WorkflowTestObject expectedObject) {

			assertSame(expectedObject, workflowObject);
		}

		public void assertWorkflowTransition(AGWorkflowTransition expectedTransition) {

			assertSame(expectedTransition, workflowTransition);
		}

		public void assertWorkflowNode(AGWorkflowNode expectedNode) {

			assertSame(expectedNode, workflowNode);
		}
	}
}
