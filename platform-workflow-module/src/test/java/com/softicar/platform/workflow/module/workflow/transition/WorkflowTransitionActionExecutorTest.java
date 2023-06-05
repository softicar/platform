package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.test.WorkflowTestObjectTable;
import com.softicar.platform.workflow.module.workflow.WorkflowNodeTestPrecondition;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.task.execution.AGWorkflowTaskExecution;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class WorkflowTransitionActionExecutorTest extends AbstractTestObjectWorkflowTest {

	private final AGWorkflowNode sourceNode;
	private final AGWorkflowNode votingNode;
	private final AGWorkflowNode nonVotingNode;
	private final AGWorkflowTransition votingTransition;
	private final AGWorkflowTransition nonVotingTransition;
	private final AGWorkflowItem workflowItem;
	private final WorkflowTestObject workflowObject;
	private final List<SideEffectExecution> sideEffectExecutions;

	public WorkflowTransitionActionExecutorTest() {

		this.sourceNode = rootNode;
		this.votingNode = insertWorkflowNode(workflowVersion, "Voting Node");
		this.nonVotingNode = insertWorkflowNode(workflowVersion, "Non Voting Node");
		this.votingTransition = insertWorkflowTransition("Transition", sourceNode, votingNode, "100%", true, WorkflowTestObjectTable.PERMISSION_A);
		this.nonVotingTransition = insertWorkflowTransition("Transition", sourceNode, nonVotingNode, "1", true, WorkflowTestObjectTable.PERMISSION_B);
		this.workflowItem = insertWorkflowItem(sourceNode);
		this.workflowObject = insertWorkflowTestEntity("Workflow Test Object", workflowItem);
		this.sideEffectExecutions = new ArrayList<>();

		// setup side-effect execution
		votingTransition.setSideEffect(WorkflowTransitionTestSideEffect.class);
		nonVotingTransition.setSideEffect(WorkflowTransitionTestSideEffect.class);
		WorkflowTransitionTestSideEffect.setConsumer(this::addSideEffectExecution);
	}

	@Test
	public void testSideEffectWithNonVotingTransition() {

		var user1 = insertUserPermissionBWithoutTask("User #1");
		new WorkflowTransitionActionExecutor(workflowItem, nonVotingTransition, user1).execute();
		assertExecutedSideEffects(1);

		// assert proper side-effect execution
		var execution = assertOne(sideEffectExecutions);
		execution.assertWorkflowObject(workflowObject);
		execution.assertWorkflowTransition(nonVotingTransition);
		execution.assertWorkflowNode(nonVotingNode);

		assertSame(nonVotingNode, workflowItem.getWorkflowNode());
	}

	@Test
	public void testSideEffectExecution() {

		var user1 = insertUserPermissionAWithTask("User #1");
		var user2 = insertUserPermissionAWithTask("User #2");
		var user3 = insertUserPermissionAWithTask("User #3");

		new WorkflowTransitionActionExecutor(workflowItem, votingTransition, user1).execute();
		assertExecutedSideEffects(0);

		new WorkflowTransitionActionExecutor(workflowItem, votingTransition, user2).execute();
		assertExecutedSideEffects(0);

		new WorkflowTransitionActionExecutor(workflowItem, votingTransition, user3).execute();
		assertExecutedSideEffects(1);

		// assert proper side-effect execution
		var execution = assertOne(sideEffectExecutions);
		execution.assertWorkflowObject(workflowObject);
		execution.assertWorkflowTransition(votingTransition);
		execution.assertWorkflowNode(votingNode);

		assertSame(votingNode, workflowItem.getWorkflowNode());
	}

	@Test
	public void testSideEffectExecutionWithExceptionOnSideEffect() {

		var exceptionMessage = "Intentional exception for side-effect.";
		var user = insertUserPermissionAWithTask("User");

		// setup throwing side-effect
		WorkflowTransitionTestSideEffect.setConsumer((object, transition) -> {
			throw new RuntimeException(exceptionMessage);
		});

		// try to execute transition
		assertExceptionMessage(exceptionMessage, () -> {
			new WorkflowTransitionActionExecutor(workflowItem, votingTransition, user).execute();
		});

		assertSame(sourceNode, workflowItem.getWorkflowNode());
		assertEmpty(AGWorkflowTaskExecution.TABLE.loadAll());
		assertOne(AGWorkflowTask.getOpenWorkflowTasks(user, workflowItem));
	}

	@Test
	public void testSideEffectExecutionWithExceptionOnPreconditionTest() {

		var exceptionMessage = "Intentional exception for pre-condition.";
		var user = insertUserPermissionAWithTask("User");

		// setup throwing precondition
		insertWorkflowNodePrecondition(votingNode, WorkflowNodeTestPrecondition.class);
		WorkflowNodeTestPrecondition.setPredicate((object) -> {
			throw new RuntimeException(exceptionMessage);
		});

		// try to execute transition
		assertExceptionMessage(exceptionMessage, () -> {
			new WorkflowTransitionActionExecutor(workflowItem, votingTransition, user).execute();
		});

		assertSame(sourceNode, workflowItem.getWorkflowNode());
		assertEmpty(AGWorkflowTaskExecution.TABLE.loadAll());
		assertOne(AGWorkflowTask.getOpenWorkflowTasks(user, workflowItem));
		assertExecutedSideEffects(0);
	}

	@Test
	public void testAutoTransitionExecution() {

		AGWorkflowNode tailingNodeA = insertWorkflowNode(workflowVersion, "Tailing Node A");
		AGWorkflowNode tailingNodeB = insertWorkflowNode(workflowVersion, "Tailing Node B");
		AGWorkflowNode tailingNodeC = insertWorkflowNode(workflowVersion, "Tailing Node C");
		insertWorkflowAutoTransition("Auto 1", nonVotingNode, tailingNodeA);
		insertWorkflowAutoTransition("Auto 2", tailingNodeA, tailingNodeB);
		insertWorkflowTransition("Tailing Manual Transition", tailingNodeB, tailingNodeC, "1", false, WorkflowTestObjectTable.PERMISSION_B);

		var user1 = insertUserPermissionBWithoutTask("User #1");
		new WorkflowTransitionActionExecutor(workflowItem, nonVotingTransition, user1).execute();

		// assert
		assertSame(tailingNodeB, workflowItem.getWorkflowNode());

		var task = assertOne(AGWorkflowTask.TABLE.loadAll());
		assertSame(workflowItem, task.getWorkflowItem());
		assertSame(user1, task.getUser());
		assertFalse(task.isClosed());
	}

	// ------------------------------ private ------------------------------ //

	private AGUser insertUserPermissionAWithTask(String username) {

		var user = insertUser(username);
		insertPermissionA(user, workflowObject);
		insertWorkflowTaskOpen(user, workflowItem);
		return user;
	}

	private AGUser insertUserPermissionBWithoutTask(String username) {

		var user = insertUser(username);
		insertPermissionB(user, workflowObject);
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
