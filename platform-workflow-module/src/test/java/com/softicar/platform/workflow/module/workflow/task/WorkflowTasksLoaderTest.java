package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.workflow.user.configuration.AGWorkflowUserConfiguration;
import java.util.List;
import org.junit.Test;

public class WorkflowTasksLoaderTest extends AbstractTestObjectWorkflowTest {

	private final AGUser alice;
	private final AGUser bob;
	private final AGUser oscar;

	public WorkflowTasksLoaderTest() {

		this.alice = insertUser("alice");
		this.bob = insertUser("bob");
		this.oscar = insertUser("oscar");
	}

	@Test
	public void testWithoutTasks() {

		var item = insertWorkflowItem(rootNode);

		var tasks = new WorkflowTasksLoader(item).getOpenTasksFor(user);

		assertEmpty(tasks);
	}

	@Test
	public void testWithAssignedAndDelegatedTasks() {

		var item = insertWorkflowItem(rootNode);

		// insert assigned tasks
		var alicesTask1 = insertWorkflowTaskOpen(alice, item);
		var alicesTask2 = insertWorkflowTaskOpen(alice, item);
		var alicesClosedTask = insertWorkflowTaskClosed(alice, item);

		// insert delegated tasks
		var bobsTaskDelegatedToAlice = insertWorkflowTaskOpen(bob, item);
		var bobsTaskNotDelegatedToAlice = insertWorkflowTaskOpen(bob, item);
		var bobsClosedTaskDelegatedToAlice = insertWorkflowTaskClosed(bob, item);
		insertWorkflowTaskDelegation(bobsTaskDelegatedToAlice, alice);
		insertWorkflowTaskDelegation(bobsTaskNotDelegatedToAlice, alice).setActive(false).save();
		insertWorkflowTaskDelegation(bobsClosedTaskDelegatedToAlice, alice);

		var tasks = new WorkflowTasksLoader(item).getOpenTasksFor(alice);

		assertSameElements(List.of(alicesTask1, alicesTask2, bobsTaskDelegatedToAlice), tasks);
		DevNull.swallow(alicesClosedTask);
	}

	@Test
	public void testWithSubstitutionTasks() {

		var item = insertWorkflowItem(rootNode);

		// insert Alice's tasks
		var alicesTask = insertWorkflowTaskOpen(alice, item);
		var alicesTaskDelegatedToBob = insertWorkflowTaskOpen(alice, item);
		insertWorkflowTaskDelegation(alicesTaskDelegatedToBob, bob);

		// insert Bob's tasks
		var bobsTask = insertWorkflowTaskOpen(bob, item);

		// insert Oscar's tasks
		var oscarsTask = insertWorkflowTaskOpen(oscar, item);

		// insert substitution
		AGWorkflowUserConfiguration.TABLE//
			.getOrCreate(alice)
			.setSubstitute(bob)
			.setSubstituteFrom(Day.today())
			.setSubstituteTo(Day.today())
			.save();

		var tasks = new WorkflowTasksLoader(item).getOpenTasksFor(bob);

		assertSameElements(List.of(alicesTask, alicesTaskDelegatedToBob, bobsTask), tasks);
		DevNull.swallow(oscarsTask);
	}
}
