package com.softicar.platform.workflow.module.workflow.transition.program;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.task.WorkflowTaskManager;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.execution.auto.AGWorkflowAutoTransitionExecution;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * TODO add javadoc
 */
@EmfSourceCodeReferencePointUuid("6e79dc47-e910-4726-b789-a570d2ce4b61")
public class WorkflowAutoTransitionExecutionProgram implements IProgram {

	@Override
	public void executeProgram() {

		Log.finfo("Starting auto transition execution.");
		ExceptionsCollector exceptionsCollector = new ExceptionsCollector();
		TreeMap<AGWorkflowItem, List<AGWorkflowTransition>> itemToTransitionsMap = loadItemsAndTransitionsToProcess();
		Log.finfo("Evaluating auto transitions for %s items.", itemToTransitionsMap.size());
		for (AGWorkflowItem item: itemToTransitionsMap.keySet()) {
			Log.finfo("Evaluating auto transition for %s.", item.toDisplayWithoutId());
			try (DbTransaction transaction = new DbTransaction()) {
				evaluateAndExecuteAutoTransition(item, itemToTransitionsMap.get(item));
				transaction.commit();
			} catch (Throwable throwable) {
				Log.finfo("Execution failed.");
				exceptionsCollector.add(throwable);
			}
		}
		exceptionsCollector.throwExceptionIfNotEmpty();
		Log.finfo("Auto transition execution finished.");
	}

	private TreeMap<AGWorkflowItem, List<AGWorkflowTransition>> loadItemsAndTransitionsToProcess() {

		TreeMap<AGWorkflowItem, List<AGWorkflowTransition>> itemToTransitionsMap = new TreeMap<>();
		Sql//
			.from(AGWorkflowItem.TABLE)
			.select(AGWorkflowItem.TABLE)
			.join(AGWorkflowItem.WORKFLOW_NODE)
			.where(AGWorkflowNode.ACTIVE)
			.joinReverse(AGWorkflowTransition.SOURCE_NODE)
			.where(AGWorkflowTransition.AUTO_TRANSITION)
			.select(AGWorkflowTransition.TABLE)
			.forEach(
				it -> itemToTransitionsMap//
					.computeIfAbsent(it.get0(), dummy -> new ArrayList<>())
					.add(it.get1()));
		return itemToTransitionsMap;
	}

	private void evaluateAndExecuteAutoTransition(AGWorkflowItem item, List<AGWorkflowTransition> transitions) {

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
			AGWorkflowTransition transition = validTransitions.get(0);
			Log.finfo("Executing transition %s into target node %s.", transition.toDisplayWithoutId(), transition.getTargetNode().toDisplayWithoutId());
			transition.executeSideEffect(item);
			new WorkflowTaskManager(item).setNextNodeAndGenerateTasks(transition.getTargetNode());
			new AGWorkflowAutoTransitionExecution().setWorkflowItem(item).setWorkflowTransition(transition).save();
		} else {
			Log.finfo("No executable auto transition found.");
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
