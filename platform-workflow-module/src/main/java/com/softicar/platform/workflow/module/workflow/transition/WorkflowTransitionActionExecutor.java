package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.dialog.DomModalPromptDialog;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.message.AGWorkflowItemMessage;
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
	private INullaryVoidFunction callbackAfterExecution;

	public WorkflowTransitionActionExecutor(AGWorkflowItem item, AGWorkflowTransition transition, AGUser user) {

		this.item = Objects.requireNonNull(item);
		this.transition = Objects.requireNonNull(transition);
		this.user = Objects.requireNonNull(user);
		this.callbackAfterExecution = INullaryVoidFunction.NO_OPERATION;
	}

	public WorkflowTransitionActionExecutor setCallbackAfterExecution(INullaryVoidFunction callbackAfterExecution) {

		this.callbackAfterExecution = callbackAfterExecution;
		return this;
	}

	public void execute() {

		if (transition.isCommentRequired()) {
			executeTransitionWithRequiredComment();
		} else {
			executeTransition();
		}
	}

	private void executeTransitionWithRequiredComment() {

		new DomModalPromptDialog(this::executeTransitionWithRequiredComment, WorkflowI18n.PLEASE_PROVIDE_A_RATIONALE, "").open();
	}

	private void executeTransitionWithRequiredComment(String comment) {

		if (comment.isBlank()) {
			executeTransitionWithRequiredComment();
		} else {
			try (var transaction = new DbTransaction()) {
				new AGWorkflowItemMessage()//
					.setText(comment)
					.setWorkflowItem(item)
					.save();
				executeTransition();
				transaction.commit();
			}
		}
	}

	private void executeTransition() {

		try (var transaction = new DbTransaction()) {
			item.reloadForUpdate();
			transition.assertInSourceNode(item, () -> new SofticarUserException(WorkflowI18n.THIS_ACTION_IS_NOT_AVAILABLE_ANYMORE));
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
		callbackAfterExecution.apply();
	}

	private void storeTransitionExecutionForAllRelevantTasks() {

		for (AGWorkflowTask task: item.getOpenTasksFor(user)) {
			new AGWorkflowTransitionExecution()//
				.setWorkflowTask(task)
				.setWorkflowTransition(transition)
				.save();
		}
	}
}
