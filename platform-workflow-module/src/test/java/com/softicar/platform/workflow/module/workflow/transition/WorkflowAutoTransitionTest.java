package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.core.module.daemon.watchdog.DaemonWatchdogControllerSingleton;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.cache.DbTableRowCaches;
import com.softicar.platform.emf.validation.EmfValidationException;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.test.FalsePrecondition;
import com.softicar.platform.workflow.module.test.TruePrecondition;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.test.WorkflowTestObjectTable;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.transition.permission.AGWorkflowTransitionPermission;
import com.softicar.platform.workflow.module.workflow.transition.program.WorkflowAutoTransitionExecutionProgram;
import com.softicar.platform.workflow.module.workflow.user.configuration.AGWorkflowUserConfiguration;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Test;

// TODO split this class to better match the actual class under test
public class WorkflowAutoTransitionTest extends AbstractTestObjectWorkflowTest {

	private static final int PROGRAM_EXECUTION_TIMEOUT = 30000;
	private final AGWorkflowNode nodeA;
	private final AGWorkflowNode nodeB;
	private final AGWorkflowTransition autoTransition;

	public WorkflowAutoTransitionTest() {

		this.nodeA = insertWorkflowNode(workflowVersion, "A");
		this.nodeB = insertWorkflowNode(workflowVersion, "B");
		this.autoTransition = insertWorkflowAutoTransition(rootNode, nodeA, "Auto Transition");

		DaemonWatchdogControllerSingleton.get().start();
	}

	@After
	public void cleanup() {

		DaemonWatchdogControllerSingleton.get().stop();
	}

	// ---------- task creation and notification ---------- //

	@Test
	public void testTaskCreationAndNotification() {

		AGUser otherUser = insertUser("Other");
		AGWorkflowUserConfiguration.TABLE//
			.getOrCreate(user)
			.setEmailNotificationsForNewTasks(true)
			.save();

		// setup user transitions for task notifications
		AGWorkflowNode nodeC = insertWorkflowNode(workflowVersion, "C");
		insertWorkflowTransition("User Transition with Notify", nodeA, nodeB, "1", true, WorkflowTestObjectTable.PERMISSION_A);
		insertWorkflowTransition("User Transition without Notify", nodeA, nodeC, "1", false, WorkflowTestObjectTable.PERMISSION_B);

		// setup object and permission ownership
		WorkflowTestObject testObject = insertTestObjectAndStartWorkflow();
		testObject.addPermissionAssignment(user, "A");
		testObject.addPermissionAssignment(user, "B");
		testObject.addPermissionAssignment(otherUser, "B");

		waitForProgramExecutions(1);
		DbTableRowCaches.invalidateAll();

		// assert task for transition with notify
		AGWorkflowTask taskWithNotify = assertOne(AGWorkflowTask.TABLE.createSelect().where(AGWorkflowTask.NOTIFY));
		assertSame(user, taskWithNotify.getUser());

		// assert task for transition without notify
		AGWorkflowTask taskWithoutNotify = assertOne(AGWorkflowTask.TABLE.createSelect().where(AGWorkflowTask.NOTIFY.isFalse()));
		assertSame(otherUser, taskWithoutNotify.getUser());

		// assert email for transition with notify
		AGBufferedEmail email = assertOne(AGBufferedEmail.TABLE.loadAll());
		assertEquals(user.getEmailAddress(), email.getTo());
	}

	// ---------- single and multiple auto transitions ---------- //

	@Test
	public void testAutoTransitionExecution() {

		WorkflowTestObject testObject = insertTestObjectAndStartWorkflow();

		new WorkflowAutoTransitionExecutionProgram().executeProgram();

		assertSame(nodeA, testObject.getWorkflowItem().getWorkflowNode());
	}

	@Test
	public void testSimultaneousAutoTransitionExecutionFailed() {

		WorkflowTestObject testObject = insertTestObjectAndStartWorkflow();
		insertWorkflowAutoTransition(rootNode, nodeB, "Auto Transition");
		try {
			new WorkflowAutoTransitionExecutionProgram().executeProgram();
		} catch (RuntimeException exception) {
			List<IDisplayString> transitionNames = AGWorkflowTransition.TABLE//
				.loadAll()
				.stream()
				.map(AGWorkflowTransition::toDisplayWithoutId)
				.collect(Collectors.toList());
			String errorString = WorkflowI18n.WORKFLOW_ITEM_ARG1_HAS_MORE_THAN_ONE_EXECUTABLE_AUTO_TRANSITION_ARG2
				.toDisplay(testObject.getWorkflowItem().toDisplayWithoutId(), Imploder.implode(transitionNames, "\n"))
				.toString();
			assertTrue(exception.getMessage().contains(errorString));
		}
	}

	// ---------- subsequent auto transitions ---------- //

	@Test
	public void testSubsequentAutoTransitions() {

		AGWorkflowNode nodeC = insertWorkflowNode(workflowVersion, "C");
		insertWorkflowAutoTransition(nodeA, nodeB, "Auto Transition");
		insertWorkflowAutoTransition(nodeB, nodeC, "Auto Transition");

		WorkflowTestObject testObject = insertTestObjectAndStartWorkflow();
		waitForProgramExecutions(3);
		DbTableRowCaches.invalidateAll();

		assertSame(nodeC, testObject.getWorkflowItem().getWorkflowNode());
	}

	// ---------- multiple transitions and preconditions ---------- //

	@Test
	public void testOneAutoTransitionWithTruePrecondition() {

		WorkflowTestObject testObject = insertTestObjectAndStartWorkflow();
		insertWorkflowNodePrecondition(nodeA, TruePrecondition.class);

		new WorkflowAutoTransitionExecutionProgram().executeProgram();

		assertSame(nodeA, testObject.getWorkflowItem().getWorkflowNode());
	}

	@Test
	public void testOneAutoTransitionWithFalsePrecondition() {

		WorkflowTestObject testObject = insertTestObjectAndStartWorkflow();
		insertWorkflowAutoTransition(rootNode, nodeB, "Blocked Auto Transition");
		insertWorkflowNodePrecondition(nodeB, FalsePrecondition.class);

		new WorkflowAutoTransitionExecutionProgram().executeProgram();

		assertSame(nodeA, testObject.getWorkflowItem().getWorkflowNode());
	}

	@Test
	public void testAllAutoTransitionWithFalsePrecondition() {

		WorkflowTestObject testObject = insertTestObjectAndStartWorkflow();
		insertWorkflowAutoTransition(rootNode, nodeB, "Blocked Auto Transition");
		insertWorkflowNodePrecondition(nodeB, FalsePrecondition.class);
		insertWorkflowNodePrecondition(nodeA, FalsePrecondition.class);

		new WorkflowAutoTransitionExecutionProgram().executeProgram();

		assertSame(rootNode, testObject.getWorkflowItem().getWorkflowNode());
	}

	// ---------- test validator behavior ---------- //

	@Test(expected = EmfValidationException.class)
	public void testErrorWithAutoTransitionAndRequiredVotes() {

		autoTransition.setRequiredVotes("5").save();
	}

	@Test(expected = EmfValidationException.class)
	public void testErrorWithAutoTransitionAndNotifyTrue() {

		autoTransition.setNotify(true).save();
	}

	@Test(expected = EmfValidationException.class)
	public void testErrorWithAutoTransitionAndPermissions() {

		autoTransition.setAutoTransition(false).save();

		new AGWorkflowTransitionPermission()//
			.setTransition(autoTransition)
			.setPermission(AGUuid.getOrCreate(WorkflowTestObjectTable.PERMISSION_A.getAnnotatedUuid()))
			.save();
		autoTransition.setAutoTransition(true).save();
	}

	// ------------------------------ auxiliary ------------------------------ //

	private WorkflowTestObject insertTestObjectAndStartWorkflow() {

		// create object
		WorkflowTestObject testObject = new WorkflowTestObject()//
			.setName("TestName")
			.save();
		assertNull(testObject.getWorkflowItem());

		// start workflow
		workflow.startWorkflow(testObject);
		assertNotNull(testObject.getWorkflowItem());
		assertSame(rootNode, testObject.getWorkflowItem().getWorkflowNode());

		return testObject;
	}

	// ------------------------------ program execution ------------------------------ //

	private void waitForProgramExecutions(int minimumCount) {

		long timeout = System.currentTimeMillis() + PROGRAM_EXECUTION_TIMEOUT;
		while (System.currentTimeMillis() < timeout) {
			if (getProgramExecutions().size() >= minimumCount) {
				return;
			}
			Sleep.sleep(100);
		}
		throw new AssertionError(
			"Timeout waiting for program executions. Expected %s but found only %s."
				.formatted(//
					minimumCount,
					getProgramExecutions().size()));
	}

	private List<AGProgramExecution> getProgramExecutions() {

		return AGProgramExecution.TABLE//
			.createSelect()
			.where(AGProgramExecution.PROGRAM_UUID.isEqual(AGUuid.getOrCreate(WorkflowAutoTransitionExecutionProgram.class)))
			.where(AGProgramExecution.TERMINATED_AT.isNotNull())
			.list();
	}
}
