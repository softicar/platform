package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;
import com.softicar.platform.workflow.module.workflow.task.user.notification.AGWorkflowTaskUserNotification;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.program.WorkflowAutoTransitionExecutionProgram;
import com.softicar.platform.workflow.module.workflow.transition.role.AGWorkflowTransitionRole;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WorkflowTaskManager {

	private final AGWorkflowItem item;

	public WorkflowTaskManager(AGWorkflowItem item) {

		this.item = item;
	}

	// FIXME the non-task related logic should not be part of this class
	public void setNextNodeAndGenerateTasks(AGWorkflowNode nextNode) {

		AGWorkflowNode oldNodeOfWorkflowItem = item.getWorkflowNode();

		try (DbTransaction transaction = new DbTransaction()) {

			checkConcurrentModificationOfWorkflowItem(oldNodeOfWorkflowItem);

			closeAllTasks();
			item.setWorkflowNode(nextNode).save();
			insertTasks();
			Programs.enqueueExecution(WorkflowAutoTransitionExecutionProgram.class);

			transaction.commit();
		}
	}

	public void closeAllTasks() {

		try (DbTransaction transaction = new DbTransaction()) {
			List<AGWorkflowTask> taskList = AGWorkflowTask.createSelect().where(AGWorkflowTask.WORKFLOW_ITEM.equal(item)).list();
			taskList.forEach(task -> task.setClosed(true));
			AGWorkflowTask.TABLE.saveAll(taskList);
			AGWorkflowTaskDelegation.TABLE
				.createSelect()
				.where(AGWorkflowTaskDelegation.WORKFLOW_TASK.in(taskList))
				.stream()
				.forEach(delegation -> delegation.setActive(false).save());
			transaction.commit();
		}
	}

	private void checkConcurrentModificationOfWorkflowItem(AGWorkflowNode oldNodeOfWorkflowItem) {

		item.reload(SqlSelectLock.FOR_UPDATE);

		AGWorkflowNode currentNodeOfWorkflowItem = item.getWorkflowNode();

		if (!oldNodeOfWorkflowItem.is(currentNodeOfWorkflowItem)) {
			throw new SofticarUserException(
				WorkflowI18n.WORKFLOW_ITEM_HAS_ALREADY_BEEN_CHANGED
					.concat(" ")
					.concat(WorkflowI18n.PLEASE_REFRESH_THE_INPUT_ELEMENT_OR_PRESS_F5_TO_RELOAD_THE_SCREEN));
		}
	}

	private void insertTasks() {

		// gather involved users
		Map<AGUser, Boolean> userMap = new TreeMap<>();
		for (AGWorkflowTransition transition: AGWorkflowTransition
			.createSelect()
			.where(AGWorkflowTransition.ACTIVE)
			.where(AGWorkflowTransition.SOURCE_NODE.equal(item.getWorkflowNode()))) {
			for (AGWorkflowTransitionRole role: AGWorkflowTransitionRole
				.createSelect()
				.where(AGWorkflowTransitionRole.ACTIVE)
				.where(AGWorkflowTransitionRole.TRANSITION.equal(transition))) {
				for (AGUser user: AGUser.getAllActive()) {
					if (role.testUserAssignmentForItem(user, item)) {
						userMap.merge(user, transition.isNotify(), (a, b) -> a || b);
					}
				}
			}
		}

		// create one task per user
		userMap//
			.entrySet()
			.forEach(entry -> insertTask(entry.getKey(), entry.getValue()));
	}

	private void insertTask(AGUser user, boolean notify) {

		AGWorkflowTask task = new AGWorkflowTask()//
			.setNotify(notify)
			.setWorkflowItem(item)
			.setUser(user)
			.save();
		if (notify && AGWorkflowTaskUserNotification.TABLE.getOrCreate(user).isNotify()) {
			new WorkflowTaskNotificationSubmitter(task).submit();
		}
	}
}
