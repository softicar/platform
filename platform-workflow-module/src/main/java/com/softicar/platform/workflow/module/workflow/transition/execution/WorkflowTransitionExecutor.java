package com.softicar.platform.workflow.module.workflow.transition.execution;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.execution.auto.AGWorkflowAutoTransitionExecution;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Executes a transition for a given workflow item.
 * <p>
 * Executes the transition side-effect but does <b>not</b> touch any workflow
 * tasks.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class WorkflowTransitionExecutor {

	private final AGWorkflowItem item;

	public WorkflowTransitionExecutor(AGWorkflowItem item) {

		this.item = Objects.requireNonNull(item);
	}

	/**
	 * Executes an {@link AGWorkflowTransition} for the {@link AGWorkflowItem}.
	 * <p>
	 * Throws {@link SofticarUserException} if more than one
	 * {@link AGWorkflowTransition} has a valid precondition.
	 * <p>
	 * Returns <i>true</i> if there is only one {@link AGWorkflowTransition}
	 * with a valid precondition and a source node equal to the current node of
	 * the item, and if that transition was successfully executed.
	 * <p>
	 * Returns <i>false</i> otherwise.
	 *
	 * @param transitions
	 *            the potential transitions to execute (never <i>null</i>)
	 * @return <i>true</i> if a sole {@link AGWorkflowTransition} was
	 *         successfully executed; <i>false</i> otherwise
	 */
	public boolean evaluateAndExecute(Collection<AGWorkflowTransition> transitions) {

		Objects.requireNonNull(transitions);
		boolean executed = false;
		try (DbTransaction transaction = new DbTransaction()) {
			boolean reloaded = item.reloadForUpdate();
			if (!reloaded) {
				Log.fwarning("WARNING: Workflow item %s could not be reloaded.", item.getId());
			} else if (allTransitionsHaveExpectedSourceNode(transitions)) {
				executed = loadAndExecuteTransitions(transitions);
			}
			transaction.commit();
		}
		return executed;
	}

	private boolean loadAndExecuteTransitions(Collection<AGWorkflowTransition> transitions) {

		List<AGWorkflowTransition> validTransitions = transitions//
			.stream()
			.filter(it -> validateNodePreconditions(item, it))
			.collect(Collectors.toList());

		if (validTransitions.size() > 1) {
			List<IDisplayString> transitionTitles = validTransitions//
				.stream()
				.map(AGWorkflowTransition::toDisplayWithoutId)
				.collect(Collectors.toList());
			throw new SofticarUserException(//
				WorkflowI18n.WORKFLOW_ITEM_ARG1_HAS_MORE_THAN_ONE_EXECUTABLE_TRANSITION_ARG2
					.toDisplay(item.toDisplayWithoutId(), Imploder.implode(transitionTitles, "\n")));
		} else if (validTransitions.size() == 1) {
			var transition = validTransitions.get(0);
			var sourceNode = transition.getSourceNode();
			var targetNode = transition.getTargetNode();
			Log
				.finfo(//
					"Executing transition '%s' from source node '%s' into target node '%s' for item '%s'.",
					transition.toDisplayWithoutId(),
					sourceNode.toDisplayWithoutId(),
					targetNode.toDisplayWithoutId(),
					item.toDisplay());
			transition.executeSideEffect(item);
			item.setWorkflowNode(targetNode).save();
			logTransitionExecution(transition);
			return true;
		} else {
			Log.fverbose("No executable transition found.");
			return false;
		}
	}

	private boolean allTransitionsHaveExpectedSourceNode(Collection<AGWorkflowTransition> transitions) {

		for (AGWorkflowTransition transition: transitions) {
			var sourceNode = transition.getSourceNode();
			if (!sourceNode.equals(item.getWorkflowNode())) {
				Log
					.fwarning(
						"WARNING: Skipping workflow item #%s because it is no longer in expected source node '%s' but in '%s'.",
						item.getId(),
						sourceNode.toDisplay(),
						item.getWorkflowNode().toDisplay());
				return false;
			}
		}
		return true;
	}

	private boolean validateNodePreconditions(AGWorkflowItem item, AGWorkflowTransition transition) {

		return transition//
			.getTargetNode()
			.getAllActiveWorkflowNodePreconditions()
			.stream()
			.allMatch(it -> it.test(item));
	}

	private void logTransitionExecution(AGWorkflowTransition transition) {

		if (transition.isAutoTransition()) {
			new AGWorkflowAutoTransitionExecution().setWorkflowItem(item).setWorkflowTransition(transition).save();
		}
	}
}
