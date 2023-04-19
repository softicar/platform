package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Executes all possible auto-transitions on all workflow items.
 */
@SourceCodeReferencePointUuid("6e79dc47-e910-4726-b789-a570d2ce4b61")
public class WorkflowAutoTransitionExecutionProgram implements IProgram {

	@Override
	public void executeProgram() {

		Log.fverbose("Looking for items with possible auto transitions.");
		var itemToTransitionsMap = new WorkflowAutoTransitionsLoader().loadTransitionsByItem();

		if (itemToTransitionsMap.isEmpty()) {
			Log.fverbose("No items with auto transitions found.");
		} else {
			Log.fverbose("Evaluating auto transitions for %s items.", itemToTransitionsMap.size());
			executeAutoTransitions(itemToTransitionsMap);
		}
	}

	private void executeAutoTransitions(Map<AGWorkflowItem, List<AGWorkflowTransition>> itemToTransitionsMap) {

		var exceptionsCollector = new ExceptionsCollector();
		for (AGWorkflowItem item: itemToTransitionsMap.keySet()) {
			try {
				Log.fverbose("Evaluating auto transition for %s.", item.toDisplayWithoutId());
				new WorkflowAutoTransitionExecutor(item).evaluateAndExecute(itemToTransitionsMap.get(item));
				Log.fverbose("Execution successful.");
			} catch (Throwable throwable) {
				Log.fverbose("FAILURE -- see exceptions below");
				exceptionsCollector.add(new WorkflowAutoTransitionFailedException(throwable, item));
			}
		}
		exceptionsCollector.throwIfNotEmpty();
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
