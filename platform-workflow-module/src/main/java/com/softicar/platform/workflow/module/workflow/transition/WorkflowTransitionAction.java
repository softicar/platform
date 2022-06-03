package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.emf.action.AbstractEmfButtonAction;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.node.action.WorkflowNodeActionPredicate;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.task.WorkflowTaskManager;
import com.softicar.platform.workflow.module.workflow.transition.execution.AGWorkflowTransitionExecution;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class WorkflowTransitionAction<R extends IWorkflowableObject<R>> extends AbstractEmfButtonAction<R> {

	private final AGWorkflowTransition transition;

	public WorkflowTransitionAction(AGWorkflowTransition transition) {

		this.transition = Objects.requireNonNull(transition);
	}

	@Override
	public IEmfPredicate<R> getPrecondition() {

		return new WorkflowNodeActionPredicate<>(transition.getSourceNode());
	}

	@Override
	public IEmfPermission<R> getRequiredPermission() {

		//TODO add permissions for all transitions
		return new WorkflowTransitionActionPermission<>(transition.getAllActiveWorkflowTransitionPermissions());
	}

	@Override
	public IResource getIcon() {

		return transition.getIcon();
	}

	@Override
	public IDisplayString getTitle() {

		return transition.toDisplayWithoutId();
	}

	@Override
	public void handleClick(IEmfFormBody<R> formBody) {

		formBody//
			.getTableRow()
			.getWorkflowItemAsOptional()
			.ifPresent(this::processWorkflowItem);

		formBody//
			.getRefreshBus()
			.setChanged(formBody.getTableRow());
	}

	private void processWorkflowItem(AGWorkflowItem item) {

		try (DbTransaction transaction = new DbTransaction()) {
			item.reloadForUpdate();
			transition.executeSideEffect(item);
			storeTransitionExecutionForAllRelevantTasks(item);

			if (new WorkflowTransitionRequiredVotesEvaluator(transition, item).hasEnoughVotes()) {
				List<IDisplayString> errorMessages = transition//
					.getTargetNode()
					.getAllActiveWorkflowNodePreconditions()
					.stream()
					.map(precondition -> precondition.testAndReturnErrorMessage(item))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(Collectors.toList());
				if (!errorMessages.isEmpty()) {
					// FIXME The line break is not shown :/ ... maybe Wiki syntax works?
					throw new SofticarUserException(IDisplayString.create(Imploder.implode(errorMessages, "\n")));
				}
				new WorkflowTaskManager(item).setNextNodeAndGenerateTasks(transition.getTargetNode());
			}
			transaction.commit();
		}
	}

	private void storeTransitionExecutionForAllRelevantTasks(AGWorkflowItem item) {

		for (AGWorkflowTask task: AGWorkflowTask.getAllWorkflowTasksAndDelegationTasksAndSubstituteTasksToCloseForUserAndItem(CurrentUser.get(), item)) {

			new AGWorkflowTransitionExecution()//
				.setWorkflowTask(task)
				.setWorkflowTransition(transition)
				.save();
		}
	}

}
