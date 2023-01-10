package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.demo.AGWorkflowDemoObject;
import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegationLog;
import com.softicar.platform.workflow.module.workflow.transients.field.WorkflowUserField;
import java.util.Set;
import org.junit.Test;

public class WorkflowUserFieldTest extends AbstractTestObjectWorkflowTest {

	private final AGWorkflowTask task;
	private final AGWorkflowItem item;
	private final AGUser user2;
	private final AGWorkflowDemoObject object;
	private final int TEST_AGE = 3;

	public WorkflowUserFieldTest() {

		item = insertWorkflowItem(rootNode);
		this.task = insertWorkflowTaskOpen(user, item);

		object = new AGWorkflowDemoObject() //
			.setModuleInstance(item.getWorkflow().getModuleInstance())
			.setName("TestObject")
			.setWorkflowItem(item)
			.save();

		user2 = insertUser("Max", "Mustermann")//
			.setEmailAddress("max@example.com")
			.save();
	}

	@Test
	public void testWithoutDelegation() {

		WorkflowUserField<AGWorkflowDemoObject> workflowUserField = new WorkflowUserField<>(AGWorkflowDemoObject.WORKFLOW_ITEM);
		Set<String> valueSet = workflowUserField.getValue(object);

		assertEquals(1, valueSet.size());
		String text = valueSet.iterator().next();
		assertContains(user.getFirstName(), text);
		assertContains(user.getLastName(), text);
		assertContains("0 Days", text);
	}

	private void delegationSetup(boolean active) {

		AGWorkflowTaskDelegation delegation =
				AGWorkflowTaskDelegation.TABLE.getOrCreate(task).setDelegatedBy(user).setActive(active).setTargetUser(user2).save();
		AGWorkflowTaskDelegationLog.TABLE
			.createSelect()
			.where(AGWorkflowTaskDelegationLog.WORKFLOW_TASK_DELEGATION.isEqual(delegation))
			.getOne()
			.getTransaction()
			.setAt(DayTime.now().minusDays(TEST_AGE));
	}

	@Test
	public void testWithDelegation() {

		delegationSetup(true);

		WorkflowUserField<AGWorkflowDemoObject> workflowUserField = new WorkflowUserField<>(AGWorkflowDemoObject.WORKFLOW_ITEM);
		Set<String> valueSet = workflowUserField.getValue(object);
		String text = valueSet.iterator().next();
		assertEquals(1, valueSet.size());
		assertContains(user.getFirstName(), text);
		assertContains(user.getLastName(), text);
		assertContains(user2.getFirstName(), text);
		assertContains(user2.getLastName(), text);
		assertContains("" + TEST_AGE, text);
	}

	@Test
	public void testWithInactiveDelegation() {

		delegationSetup(false);

		WorkflowUserField<AGWorkflowDemoObject> workflowUserField = new WorkflowUserField<>(AGWorkflowDemoObject.WORKFLOW_ITEM);
		Set<String> valueSet = workflowUserField.getValue(object);
		assertEquals(1, valueSet.size());
		String text = valueSet.iterator().next();
		assertContains(user.getFirstName(), text);
		assertContains(user.getLastName(), text);
		assertContains("0 Days", text);
	}

	@Test
	public void testWithMultipleTasks() {

		delegationSetup(true);

		insertWorkflowTaskOpen(user2, item);

		WorkflowUserField<AGWorkflowDemoObject> workflowUserField = new WorkflowUserField<>(AGWorkflowDemoObject.WORKFLOW_ITEM);
		Set<String> valueSet = workflowUserField.getValue(object);
		assertEquals(2, valueSet.size());
		String text = valueSet.toString();
		assertContains(user.getFirstName(), text);
		assertContains(user.getLastName(), text);
		assertContains(user2.getFirstName(), text);
		assertContains(user2.getLastName(), text);
		assertContains("" + TEST_AGE, text);
		assertContains("0 Days", text);
	}
}
