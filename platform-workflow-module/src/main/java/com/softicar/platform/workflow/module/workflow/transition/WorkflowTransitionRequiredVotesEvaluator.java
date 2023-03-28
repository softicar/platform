package com.softicar.platform.workflow.module.workflow.transition;

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
					.where(AGWorkflowTask.WORKFLOW_ITEM.isEqual(item))
					.where(AGWorkflowTask.CLOSED.isFalse())
					.joinReverse(AGWorkflowTransitionExecution.WORKFLOW_TASK)
					.where(AGWorkflowTransitionExecution.WORKFLOW_TRANSITION.isEqual(transition))
					.stream()
					// FIXME PLAT-1173 this filter should cause some kind of error notification
					.filter(it -> transition.isUserAllowedToSeeTransition(it.getUser(), item))
					.count());
	}

	private int getRequiredVotes() {

		return new WorkflowTransitionRequiredVotesParser(transition).getRequiredVotes(this::getTotalNumberOfRelevantTasks);
	}

	private int getTotalNumberOfRelevantTasks() {

		return Math
			.toIntExact(
				AGWorkflowTask//
					.createSelect()
					.where(AGWorkflowTask.WORKFLOW_ITEM.isEqual(item))
					.where(AGWorkflowTask.CLOSED.isFalse())
					.stream()
					.filter(it -> transition.isUserAllowedToSeeTransition(it.getUser(), item))
					.count());
	}
}
