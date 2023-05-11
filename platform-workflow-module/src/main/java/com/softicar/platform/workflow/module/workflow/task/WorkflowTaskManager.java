package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.permission.AGWorkflowTransitionPermission;
import com.softicar.platform.workflow.module.workflow.user.configuration.AGWorkflowUserConfiguration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WorkflowTaskManager {

	private final AGWorkflowItem item;

	public WorkflowTaskManager(AGWorkflowItem item) {

		this.item = item;
	}

	public void closeTasksAndDelegations() {

		try (var transaction = new DbTransaction()) {
			closeAllTasks();
			closeAllDelegations();
			transaction.commit();
		}
	}

	public void insertTasks() {

		try (var transaction = new DbTransaction()) {
			Map<AGUser, Boolean> userMap = new TreeMap<>();
			List<AGUser> activeUsers = AGUser.getAllActive();

			Sql//
				.from(AGWorkflowTransitionPermission.TABLE)
				.select(AGWorkflowTransitionPermission.TABLE)
				.select(AGWorkflowTransitionPermission.TRANSITION)
				.where(AGWorkflowTransitionPermission.ACTIVE)
				.join(AGWorkflowTransitionPermission.TRANSITION)
				.where(AGWorkflowTransition.ACTIVE)
				.where(AGWorkflowTransition.SOURCE_NODE.isEqual(item.getWorkflowNode()))
				.list()
				.forEach(row -> {
					AGWorkflowTransitionPermission permission = row.get0();
					AGWorkflowTransition transition = row.get1();
					for (AGUser user: activeUsers) {
						if (permission.testUserAssignmentForItem(user, item)) {
							userMap.merge(user, transition.isNotify(), (a, b) -> a || b);
						}
					}
				});

			// create one task per user
			userMap//
				.entrySet()
				.forEach(entry -> insertTask(entry.getKey(), entry.getValue()));

			transaction.commit();
		}
	}

	private void closeAllTasks() {

		var tasks = AGWorkflowTask.TABLE//
			.createSelect()
			.where(AGWorkflowTask.CLOSED.isFalse())
			.where(AGWorkflowTask.WORKFLOW_ITEM.isEqual(item))
			.list();
		tasks.forEach(task -> task.setClosed(true));
		AGWorkflowTask.TABLE.saveAll(tasks);
	}

	private void closeAllDelegations() {

		var delegations = AGWorkflowTaskDelegation.TABLE
			.createSelect()
			.where(AGWorkflowTaskDelegation.ACTIVE)
			.join(AGWorkflowTaskDelegation.WORKFLOW_TASK)
			.where(AGWorkflowTask.WORKFLOW_ITEM.isEqual(item))
			.list();
		delegations.forEach(delegation -> delegation.setActive(false));
		AGWorkflowTaskDelegation.TABLE.saveAll(delegations);
	}

	private void insertTask(AGUser user, boolean notify) {

		AGWorkflowTask task = new AGWorkflowTask()//
			.setNotify(notify)
			.setWorkflowItem(item)
			.setUser(user)
			.save();
		if (notify && AGWorkflowUserConfiguration.TABLE.getOrCreate(user).isEmailNotificationsForNewTasks()) {
			new WorkflowTaskNotificationSubmitter(task).submit();
		}
	}
}
