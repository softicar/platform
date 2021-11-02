package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.authorization.role.EmfAnyRole;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.transition.role.AGWorkflowTransitionRole;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class WorkflowTransitionActionRole<R extends IWorkflowableObject<R>> implements IEmfRole<R> {

	private final EmfAnyRole<R> anyRole;

	public WorkflowTransitionActionRole(List<AGWorkflowTransitionRole> workflowTransitionRoles) {

		Collection<IEmfRole<R>> roles = new HashSet<>();

		for (AGWorkflowTransitionRole workflowTransitionRole: workflowTransitionRoles) {
			var role = workflowTransitionRole.getStaticRole();

			if (role.isEmpty()) {
				continue;
			}

			// TODO: fix ugly cast
			roles.add(CastUtils.cast(role.get()));
		}
		anyRole = new EmfAnyRole<>(roles);
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.WORKFLOW_TRANSITION_ACTION_ROLE;
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		return testWithInheritedRoles(tableRow, user);
	}

	/**
	 * Special test function that supports inheritance of workflow-rights by
	 * delegation or substitution
	 *
	 * @param tableRow
	 *            the IWorkflowableObject in question (never <i>null</i>)
	 * @param user
	 *            the user that want to execute the transition (never
	 *            <i>null</i>)
	 * @return true if the current user is in one of the roles or if a user that
	 *         delegated to him is in one of the roles (or has set him as
	 *         substitute)
	 */
	protected boolean testWithInheritedRoles(R tableRow, IBasicUser user) {

		AGUser currentUser = AGUser.get(user.getId());
		return AGWorkflowTask
			.getAllWorkflowTasksAndDelegationTasksAndSubstituteTasksToCloseForUserAndItem(currentUser, tableRow.getWorkflowItem()) //TODO check if this includes current user tasks (probably)
			.stream()
			.filter(AGWorkflowTask::wasNotExecuted) // FIXME: find a better method to check available actions
			.map(it -> it.getUser())
			.anyMatch(it -> anyRole.test(tableRow, it));
	}
}
