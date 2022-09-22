package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.task.WorkflowTaskManager;
import com.softicar.platform.workflow.module.workflow.transition.execution.AGWorkflowTransitionExecution;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class WorkflowTransitionActionExecutor {

	private final AGWorkflowItem item;
	private final AGWorkflowTransition transition;
	private final AGUser user;

	public WorkflowTransitionActionExecutor(AGWorkflowItem item, AGWorkflowTransition transition, AGUser user) {

		this.item = Objects.requireNonNull(item);
		this.transition = Objects.requireNonNull(transition);
		this.user = Objects.requireNonNull(user);
	}

	public void execute() {

		try (DbTransaction transaction = new DbTransaction()) {
			item.reloadForUpdate();
			storeTransitionExecutionForAllRelevantTasks();

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
					throw new SofticarUserException(IDisplayString.create(Imploder.implode(errorMessages, "\n")));
				}
				new WorkflowTaskManager(item).setNextNodeAndGenerateTasks(transition.getTargetNode());
				transition.executeSideEffect(item);
			}
			transaction.commit();
		}
	}

	private void storeTransitionExecutionForAllRelevantTasks() {

		for (AGWorkflowTask task: AGWorkflowTask.getAllWorkflowTasksAndDelegationTasksAndSubstituteTasksToCloseForUserAndItem(user, item)) {

			new AGWorkflowTransitionExecution()//
				.setWorkflowTask(task)
				.setWorkflowTransition(transition)
				.save();
		}
	}
}
