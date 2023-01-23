package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.task.WorkflowTaskManager;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class WorkflowAutoTransitionExecutor {

	private final AGWorkflowItem item;
	private final Collection<AGWorkflowTransition> transitions;

	public WorkflowAutoTransitionExecutor(AGWorkflowItem item, Collection<AGWorkflowTransition> transitions) {

		this.item = item;
		this.transitions = transitions;
	}

	public void evaluateAndExecute() {

		try (DbTransaction transaction = new DbTransaction()) {
			checkSourceNodeOfTransitions();
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
					WorkflowI18n.WORKFLOW_ITEM_ARG1_HAS_MORE_THAN_ONE_EXECUTABLE_AUTO_TRANSITION_ARG2
						.toDisplay(item.toDisplayWithoutId(), Imploder.implode(transitionTitles, "\n")));
			} else if (validTransitions.size() == 1) {
				var transition = validTransitions.get(0);
				var targetNode = transition.getTargetNode();
				Log
					.finfo(//
						"Executing transition '%s' into target node '%s' for item '%s'.",
						transition.toDisplayWithoutId(),
						targetNode.toDisplayWithoutId(),
						item.toDisplay());
				transition.executeSideEffect(item);
				new WorkflowTaskManager(item).setNextNodeAndGenerateTasks(targetNode);
				new AGWorkflowAutoTransitionExecution().setWorkflowItem(item).setWorkflowTransition(transition).save();
			} else {
				Log.fverbose("No executable auto transition found.");
			}
			transaction.commit();
		}
	}

	private void checkSourceNodeOfTransitions() {

		if (transitions.stream().anyMatch(it -> !it.getSourceNode().equals(item.getWorkflowNode()))) {
			throw new SofticarUserException(
				WorkflowI18n.ALL_TRANSITIONS_NEED_TO_HAVE_THE_CORRECT_SOURCE_NODE_ARG1.toDisplay(item.getWorkflowNode().toDisplayWithoutId()));
		}
	}

	private boolean validateNodePreconditions(AGWorkflowItem item, AGWorkflowTransition transition) {

		return transition//
			.getTargetNode()
			.getAllActiveWorkflowNodePreconditions()
			.stream()
			.allMatch(it -> it.test(item));
	}
}
