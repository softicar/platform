package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.transition.execution.AGWorkflowTransitionExecution;
import java.util.Objects;

public class WorkflowTransitionRequiredVotesEvaluator {

	private final AGWorkflowTransition transition;
	private final AGWorkflowItem item;

	public WorkflowTransitionRequiredVotesEvaluator(AGWorkflowTransition transition, AGWorkflowItem item) {

		this.transition = Objects.requireNonNull(transition);
		this.item = Objects.requireNonNull(item);
	}

	public boolean hasEnoughVotes() {

		return getNumberOfExecutedVotes() >= getRequiredVotes();
	}

	private int getNumberOfExecutedVotes() {

		return Math
			.toIntExact(
				AGWorkflowTask
					.createSelect()
					.where(AGWorkflowTask.WORKFLOW_ITEM.equal(item))
					.where(AGWorkflowTask.CLOSED.isFalse())
					.joinReverse(AGWorkflowTransitionExecution.WORKFLOW_TASK)
					.where(AGWorkflowTransitionExecution.WORKFLOW_TRANSITION.isEqual(transition))
					.stream()
					// FIXME PLAT-1173 this filter should cause some kind of error notification
					.filter(it -> transition.isUserAllowedToSeeTransition(it.getUser(), item))
					.count());
	}

	private int getRequiredVotes() {

		if (transition.getRequiredVotes().contains("%")) {
			return getRequiredVotesFromPercentage();
		} else {
			return Integer.parseInt(transition.getRequiredVotes().trim());
		}
	}

	private int getRequiredVotesFromPercentage() {

		int percentage = Integer.parseInt(transition.getRequiredVotes().replace("%", "").trim());
		if (percentage > 100 || percentage <= 0) {
			throw new SofticarUserException(
				WorkflowI18n.INVALID_WORKFLOW_DEFINITION_IN_TRANSITION_ARG1_VOTING_PERCENTAGE_MUST_BE_GREATER_THAN_0_AND_LESS_EQUAL_TO_100
					.toDisplay(transition.toDisplay()));
		}

		int totalTask = getTotalNumberOfRelevantTasks();

		return (int) Math.round(totalTask * percentage / 100.0);
	}

	private int getTotalNumberOfRelevantTasks() {

		return Math
			.toIntExact(
				AGWorkflowTask//
					.createSelect()
					.where(AGWorkflowTask.WORKFLOW_ITEM.equal(item))
					.where(AGWorkflowTask.CLOSED.isFalse())
					.stream()
					.filter(it -> transition.isUserAllowedToSeeTransition(it.getUser(), item))
					.count());
	}
}
