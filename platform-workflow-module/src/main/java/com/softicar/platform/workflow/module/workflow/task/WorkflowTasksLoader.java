package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.task.delegation.AGWorkflowTaskDelegation;
import com.softicar.platform.workflow.module.workflow.user.configuration.AGWorkflowUserConfiguration;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Loads open tasks for a given {@link AGWorkflowItem} and {@link AGUser},
 * including delegated tasks and tasks gained through user substitution.
 *
 * @author Oliver Richers
 */
public class WorkflowTasksLoader {

	private final AGWorkflowItem item;

	public WorkflowTasksLoader(AGWorkflowItem item) {

		this.item = item;
	}

	public Collection<AGWorkflowTask> getOpenTasksFor(AGUser user) {

		var tasks = new TreeSet<AGWorkflowTask>();

		tasks.addAll(loadAssignedTasks(user));
		tasks.addAll(loadDelegatedTasks(user));

		AGWorkflowUserConfiguration.loadAllUsersWithSubstitute(user).forEach(originalUser -> {
			tasks.addAll(loadAssignedTasks(originalUser));
			tasks.addAll(loadDelegatedTasks(originalUser));
		});

		return tasks;

	}

	private List<AGWorkflowTask> loadAssignedTasks(AGUser user) {

		return AGWorkflowTask.TABLE//
			.createSelect()
			.where(AGWorkflowTask.CLOSED.isFalse())
			.where(AGWorkflowTask.USER.isEqual(user))
			.where(AGWorkflowTask.WORKFLOW_ITEM.isEqual(item))
			.list();
	}

	private List<AGWorkflowTask> loadDelegatedTasks(AGUser user) {

		return AGWorkflowTask.TABLE
			.createSelect()
			.where(AGWorkflowTask.CLOSED.isFalse())
			.where(AGWorkflowTask.WORKFLOW_ITEM.isEqual(item))
			.joinReverse(AGWorkflowTaskDelegation.WORKFLOW_TASK)
			.where(AGWorkflowTaskDelegation.ACTIVE)
			.where(AGWorkflowTaskDelegation.TARGET_USER.isEqual(user))
			.list();
	}
}
