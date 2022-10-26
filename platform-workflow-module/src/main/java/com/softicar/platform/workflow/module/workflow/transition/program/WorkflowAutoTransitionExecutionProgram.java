package com.softicar.platform.workflow.module.workflow.transition.program;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.execution.auto.WorkflowAutoTransitionExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Executes all possible auto-transitions on all workflow items.
 */
@SourceCodeReferencePointUuid("6e79dc47-e910-4726-b789-a570d2ce4b61")
public class WorkflowAutoTransitionExecutionProgram implements IProgram {

	@Override
	public void executeProgram() {

		Log.finfo("Looking for items with possible auto transitions.");
		var itemToTransitionsMap = loadItemsAndTransitionsToProcess();

		if (itemToTransitionsMap.isEmpty()) {
			Log.finfo("No items with auto transitions found.");
		} else {
			Log.finfo("Evaluating auto transitions for %s items.", itemToTransitionsMap.size());
			executeAutoTransitions(itemToTransitionsMap);
		}
	}

	private void executeAutoTransitions(TreeMap<AGWorkflowItem, List<AGWorkflowTransition>> itemToTransitionsMap) {

		ExceptionsCollector exceptionsCollector = new ExceptionsCollector();
		for (AGWorkflowItem item: itemToTransitionsMap.keySet()) {
			try {
				Log.finfo("Evaluating auto transition for %s.", item.toDisplayWithoutId());
				new WorkflowAutoTransitionExecutor(item, itemToTransitionsMap.get(item)).evaluateAndExecute();
				Log.finfo("Execution successful.");
			} catch (Throwable throwable) {
				Log.finfo("FAILURE -- see exceptions below");
				exceptionsCollector.add(throwable);
			}
		}
		exceptionsCollector.throwExceptionIfNotEmpty();
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

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("* * * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional.of(WorkflowI18n.EXECUTES_WORKFLOW_TRANSITIONS);
	}
}
