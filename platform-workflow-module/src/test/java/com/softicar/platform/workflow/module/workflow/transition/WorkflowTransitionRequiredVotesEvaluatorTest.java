package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.test.WorkflowTestObjectTable;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import org.junit.Test;

public class WorkflowTransitionRequiredVotesEvaluatorTest extends AbstractTestObjectWorkflowTest {

	private final AGWorkflowNode nextNode;
	private final AGWorkflowTransition transition;
	private final AGWorkflowItem item;
	private final WorkflowTestObject testObject;

	public WorkflowTransitionRequiredVotesEvaluatorTest() {

		this.nextNode = insertWorkflowNode(workflowVersion, "Next Node");
		this.transition = insertWorkflowTransition("Transition", rootNode, nextNode, "100%", true, WorkflowTestObjectTable.ROLE_A);
		this.item = insertWorkflowItem(rootNode);
		this.testObject = insertWorkflowTestObject("Workflow Test Object", item);
		testObject.addRoleMember(user, "A");
	}

	@Test
	public void testFor100Percent() {

		// no tasks
		transition.setRequiredVotes("100%").save();
		assertEnoughVotes();

		// add task without transition execution
		insertOpenTask();
		assertNotEnoughVotes();

		// add task with transition execution
		insertOpenTaskWithTransitionExecution(transition);
		assertNotEnoughVotes();
	}

	@Test
	public void testFor50Percent() {

		// no tasks
		transition.setRequiredVotes("50%").save();
		assertEnoughVotes();

		// add task without transition execution
		insertOpenTask();
		assertNotEnoughVotes();

		// add task with transition execution
		insertOpenTaskWithTransitionExecution(transition);
		assertEnoughVotes();
	}

	@Test
	public void testWithRequiredVoteOne() {

		// no tasks
		transition.setRequiredVotes("1").save();
		assertNotEnoughVotes();

		// add task without transition execution
		insertOpenTask();
		assertNotEnoughVotes();

		// add task with transition execution
		insertOpenTaskWithTransitionExecution(transition);
		assertEnoughVotes();
	}

	@Test
	public void testWithRequiredVoteThree() {

		// no tasks
		transition.setRequiredVotes("3").save();
		assertNotEnoughVotes();

		// add task without transition execution
		insertOpenTask();
		assertNotEnoughVotes();

		// add tasks with transition execution
		insertOpenTaskWithTransitionExecution(transition);
		assertNotEnoughVotes();

		// add tasks with transition execution
		insertOpenTaskWithTransitionExecution(transition);
		assertNotEnoughVotes();

		// add tasks with transition execution
		insertOpenTaskWithTransitionExecution(transition);
		assertEnoughVotes();
	}

	private void assertEnoughVotes() {

		assertTrue(new WorkflowTransitionRequiredVotesEvaluator(transition, item).hasEnoughVotes());
	}

	private void assertNotEnoughVotes() {

		assertFalse(new WorkflowTransitionRequiredVotesEvaluator(transition, item).hasEnoughVotes());
	}

	/**
	 * This tests against a previous defect. So keep it to avoid regressions!
	 */
	@Test
	public void testWithTransitionExecutionOnOtherTransition() {

		// no tasks
		transition.setRequiredVotes("1").save();
		assertNotEnoughVotes();

		// add task with transition execution on other transition
		AGWorkflowTransition otherTransition = insertWorkflowTransition("Other", rootNode, nextNode, "100%", true, WorkflowTestObjectTable.ROLE_A);
		insertOpenTaskWithTransitionExecution(otherTransition);
		assertNotEnoughVotes();

		// add task with transition execution
		insertOpenTaskWithTransitionExecution(transition);
		assertEnoughVotes();
	}

	private AGWorkflowTask insertOpenTask() {

		return insertWorkflowTaskOpen(user, item);
	}

	private void insertOpenTaskWithTransitionExecution(AGWorkflowTransition transition) {

		AGWorkflowTask task = insertOpenTask();
		insertWorkflowTransitionExecution(task, transition);
	}
}
