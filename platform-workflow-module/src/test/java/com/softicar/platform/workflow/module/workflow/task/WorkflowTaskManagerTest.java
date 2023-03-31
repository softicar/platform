package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.test.WorkflowTestObjectTable;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;
import java.util.stream.Collectors;
import org.junit.Test;

public class WorkflowTaskManagerTest extends AbstractTestObjectWorkflowTest {

	private final AGUser userX;
	private final AGUser userY;
	private final AGUser userZ;
	private final AGWorkflowNode nodeX;
	private final AGWorkflowNode nodeY;
	private final AGWorkflowNode nodeZ;
	private final AGWorkflowItem item;
	private final WorkflowTestObject testObject;

	public WorkflowTaskManagerTest() {

		this.userX = insertUser("x");
		this.userY = insertUser("y");
		this.userZ = insertUser("z");

		this.nodeX = insertWorkflowNode(workflowVersion, "x");
		this.nodeY = insertWorkflowNode(workflowVersion, "y");
		this.nodeZ = insertWorkflowNode(workflowVersion, "z");

		insertWorkflowTransition("x->y", nodeX, nodeY, "1", false, WorkflowTestObjectTable.PERMISSION_A);
		insertWorkflowTransition("x->z", nodeX, nodeZ, "1", false, WorkflowTestObjectTable.PERMISSION_B);

		this.item = insertWorkflowItem(rootNode);
		this.testObject = insertWorkflowTestObject("foo", item);
	}

	@Test
	public void testSetNextNodeAndGenerateTasks() {

		// setup permissions
		insertPermissionA(userX, testObject);
		insertPermissionB(userX, testObject);
		insertPermissionA(userZ, testObject);

		// insert tasks and delegations
		insertWorkflowTaskOpen(userX, item);
		insertWorkflowTaskDelegation(insertWorkflowTaskOpen(userY, item), userZ);

		new WorkflowTaskManager(item).setNextNodeAndGenerateTasks(nodeX);

		// assert existing tasks were closed and new tasks were opened
		var tasks = AGWorkflowTask.TABLE//
			.createSelect()
			.orderBy(AGWorkflowTask.ID)
			.stream()
			.map(task -> "%s %s".formatted(task.getUser().getLoginName(), task.isClosed()? "closed" : "open"))
			.collect(Collectors.toList());
		assertEquals("[x closed, y closed, x open, z open]", tasks.toString());

		// assert all existing delegations were closed
		assertFalse(assertOne(AGWorkflowTaskDelegation.TABLE.loadAll()).isActive());
	}

	@Test
	public void testCloseAllTasksAndDelegations() {

		// insert tasks and delegations
		insertWorkflowTaskOpen(userX, item);
		insertWorkflowTaskDelegation(insertWorkflowTaskOpen(userY, item), userZ);

		new WorkflowTaskManager(item).closeAllTasksAndDelegations();

		// assert all existing tasks were closed
		var tasks = AGWorkflowTask.TABLE.loadAll();
		assertCount(2, tasks);
		tasks.forEach(task -> assertTrue("Expected task to be closed: %s".formatted(task.toString()), task.isClosed()));

		// assert all existing delegations were closed
		assertFalse(assertOne(AGWorkflowTaskDelegation.TABLE.loadAll()).isActive());
	}
}
