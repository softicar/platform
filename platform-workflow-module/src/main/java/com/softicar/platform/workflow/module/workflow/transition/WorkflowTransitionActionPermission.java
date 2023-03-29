package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.permission.EmfAnyPermission;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.transition.permission.AGWorkflowTransitionPermission;
import java.util.Collection;
import java.util.HashSet;

public class WorkflowTransitionActionPermission<R extends IWorkflowableObject<R>> implements IEmfPermission<R> {

	private final AGWorkflowTransition transition;
	private final EmfAnyPermission<R> permission;

	public WorkflowTransitionActionPermission(AGWorkflowTransition transition) {

		Collection<IEmfPermission<R>> permissions = new HashSet<>();

		for (AGWorkflowTransitionPermission workflowTransitionPermission: transition.getAllActiveWorkflowTransitionPermissions()) {
			workflowTransitionPermission//
				.getStaticPermission()
				.ifPresent(permission -> permissions.add(CastUtils.cast(permission)));
		}

		this.transition = transition;
		this.permission = new EmfAnyPermission<>(permissions);
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.WORKFLOW_TRANSITION_ACTION_PERMISSION;
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		if (transition.isVotingTransition()) {
			// voting transitions require the user to have a task
			return hasTask(tableRow, AGUser.get(user.getId()));
		} else {
			// non-voting transitions are also allowed without task
			return permission.test(tableRow, user) || hasTask(tableRow, AGUser.get(user.getId()));
		}
	}

	/**
	 * Special test function that supports inheritance of workflow-rights by
	 * delegation or substitution
	 *
	 * @param tableRow
	 *            the IWorkflowableObject in question (never <i>null</i>)
	 * @param user
	 *            the user who wants to execute the transition (never
	 *            <i>null</i>)
	 * @return <i>true</i> if the given user has this permissions or if a user
	 *         that delegated the task to the user (or defined the user as
	 *         substitute) has this permission
	 */
	private boolean hasTask(R tableRow, AGUser user) {

		return tableRow//
			.getWorkflowItem()
			.getOpenTasksFor(user)
			.stream()
			.filter(AGWorkflowTask::wasNotExecuted) // FIXME: find a better method to check available actions
			.map(it -> it.getUser())
			.anyMatch(it -> permission.test(tableRow, it));
	}
}
