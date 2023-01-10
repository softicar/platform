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
	private final String DAY_SUFFIX = " Days";

	public WorkflowUserFieldTest() {

		this.item = insertWorkflowItem(rootNode);
		this.task = insertWorkflowTaskOpen(user, item);

		this.object = new AGWorkflowDemoObject() //
			.setModuleInstance(item.getWorkflow().getModuleInstance())
			.setName("TestObject")
			.setWorkflowItem(item)
			.save();

		this.user2 = insertUser("Max", "Mustermann")//
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
		assertContains("0" + DAY_SUFFIX, text);
	}

	@Test
	public void testWithDelegation() {

		insertDelegation(true);

		WorkflowUserField<AGWorkflowDemoObject> workflowUserField = new WorkflowUserField<>(AGWorkflowDemoObject.WORKFLOW_ITEM);
		Set<String> valueSet = workflowUserField.getValue(object);
		String text = valueSet.iterator().next();
		assertEquals(1, valueSet.size());
		assertContains(user.getFirstName(), text);
		assertContains(user.getLastName(), text);
		assertContains(user2.getFirstName(), text);
		assertContains(user2.getLastName(), text);
		assertContains(TEST_AGE + DAY_SUFFIX, text);
	}

	@Test
	public void testWithInactiveDelegation() {

		insertDelegation(false);

		WorkflowUserField<AGWorkflowDemoObject> workflowUserField = new WorkflowUserField<>(AGWorkflowDemoObject.WORKFLOW_ITEM);
		Set<String> valueSet = workflowUserField.getValue(object);
		assertEquals(1, valueSet.size());
		String text = valueSet.iterator().next();
		assertContains(user.getFirstName(), text);
		assertContains(user.getLastName(), text);
		assertContains("0" + DAY_SUFFIX, text);
	}

	@Test
	public void testWithMultipleTasks() {

		insertDelegation(true);

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
		assertContains("0" + DAY_SUFFIX, text);
	}

	private void insertDelegation(boolean active) {

		AGWorkflowTaskDelegation delegation =
				AGWorkflowTaskDelegation.TABLE.getOrCreate(task).setDelegatedBy(user).setActive(active).setTargetUser(user2).save();
		AGWorkflowTaskDelegationLog.TABLE
			.createSelect()
			.where(AGWorkflowTaskDelegationLog.WORKFLOW_TASK_DELEGATION.isEqual(delegation))
			.getOne()
			.getTransaction()
			.setAt(DayTime.now().minusDays(TEST_AGE));
	}
}
