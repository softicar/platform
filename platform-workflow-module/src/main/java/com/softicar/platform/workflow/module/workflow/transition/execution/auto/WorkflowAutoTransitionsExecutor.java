package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.exception.MultiException;
import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.task.WorkflowTasksAndDelegationsUpdater;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.execution.WorkflowTransitionExecutor;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Executes automatic workflow transitions for one or several workflow items.
 *
 * @author Alexander Schmidt
 */
public class WorkflowAutoTransitionsExecutor {

	private static final int DEFAULT_CASCADE_LENGTH_LIMIT = 100;
	private static final boolean DEFAULT_COLLECT_EXCEPTIONS = true;

	private final ExceptionsCollector exceptionsCollector;
	private final Map<AGWorkflowItem, Integer> cascadeLengthMap;
	private final WorkflowAutoTransitionsLoader autoTransitionsLoader;
	private int cascadeLengthLimit;
	private boolean collectExceptions;

	public WorkflowAutoTransitionsExecutor() {

		this.exceptionsCollector = new ExceptionsCollector();
		this.cascadeLengthMap = new TreeMap<>();
		this.autoTransitionsLoader = new WorkflowAutoTransitionsLoader();
		this.cascadeLengthLimit = DEFAULT_CASCADE_LENGTH_LIMIT;
		this.collectExceptions = DEFAULT_COLLECT_EXCEPTIONS;
	}

	/**
	 * Restricts auto transitions to only be executed for the given item.
	 * <p>
	 * By default, auto transitions are potentially executed for all items.
	 *
	 * @param item
	 *            the {@link AGWorkflowItem} for which auto transitions shall be
	 *            executed (never <i>null</i>)
	 * @return this
	 */
	public WorkflowAutoTransitionsExecutor setWorkflowItemWhitelist(AGWorkflowItem item) {

		this.autoTransitionsLoader.setWhitelist(Set.of(item));
		return this;
	}

	/**
	 * Defines the maximum length of an auto transition cascade.
	 *
	 * @param cascadeLengthLimit
	 *            the maximum number of consecutive auto transitions to execute
	 *            per workflow item
	 * @return this
	 */
	public WorkflowAutoTransitionsExecutor setCascadeLengthLimit(int cascadeLengthLimit) {

		this.cascadeLengthLimit = cascadeLengthLimit;
		return this;
	}

	/**
	 * Specifies if exceptions should be collected.
	 * <p>
	 * Note that the default value is {@value #DEFAULT_COLLECT_EXCEPTIONS}.
	 *
	 * @param collectExceptions
	 *            <i>true</i> to collect all exceptions and throw a
	 *            {@link MultiException} at the end; <i>false</i> to immediately
	 *            throw any occurring exception
	 * @return this
	 */
	public WorkflowAutoTransitionsExecutor setCollectExceptions(boolean collectExceptions) {

		this.collectExceptions = collectExceptions;
		return this;
	}

	/**
	 * Executes the longest possible cascade of auto transitions for one or
	 * several workflow items, using {@link WorkflowTransitionExecutor}.
	 * <p>
	 * Throws an exception if an exception was caught while trying to execute at
	 * least one auto transition, or while creating tasks.
	 *
	 * @return the result of the execution (never <i>null</i>)
	 */
	public WorkflowAutoTransitionsResult executeTransitions() {

		var overallResult = executeMainLoop();
		updateTasksAndDelegations(overallResult);
		exceptionsCollector.throwIfNotEmpty();
		return overallResult;
	}

	private WorkflowAutoTransitionsResult executeMainLoop() {

		var overallResult = new WorkflowAutoTransitionsResult();

		Log.fverbose("Entering main loop.");
		for (int currentBurst = 1;; currentBurst++) {
			Log.fverbose("---- Burst #%s ----", currentBurst);

			WorkflowAutoTransitionsMap autoTransitionsMap = autoTransitionsLoader.loadTransitionsMap();
			if (!autoTransitionsMap.isEmpty()) {
				Log.fverbose("Items to process: %s", autoTransitionsMap.size());
				var burstResult = processBurst(autoTransitionsMap);
				overallResult.addAll(burstResult);

				// restrict subsequent iterations to successfully transitioned items
				autoTransitionsLoader.addToWhitelist(burstResult.getTransitioned());

				autoTransitionsLoader.addToBlacklist(burstResult.getFailed());
				autoTransitionsLoader.addToBlacklist(burstResult.getOmitted());
			} else {
				Log.fverbose("No more items to process.");
				break;
			}
		}
		Log.fverbose("Left main loop.");
		return overallResult;
	}

	private WorkflowAutoTransitionsResult processBurst(WorkflowAutoTransitionsMap autoTransitionsMap) {

		var result = new WorkflowAutoTransitionsResult();

		for (var entry: autoTransitionsMap.entrySet()) {
			AGWorkflowItem item = entry.getKey();
			List<AGWorkflowTransition> transitions = entry.getValue();
			try {
				Log.fverbose("Evaluating transition for item: '%s'", item.toDisplayWithoutId());

				if (cascadeLimitReached(item)) {
					throw new SofticarException(
						"Exceeded the maximum auto transition cascade length of %s while processing item #%s. The item might be stuck in a cycle.",
						cascadeLengthLimit,
						item.getId());
				}

				if (new WorkflowTransitionExecutor(item).evaluateAndExecute(transitions)) {
					Log.fverbose("Execution successful.");
					result.addTransitioned(item);
					cascadeLengthMap.merge(item, 1, Integer::sum);
				} else {
					Log.fverbose("Nothing to do.");
					result.addOmitted(item);
				}
			} catch (Throwable throwable) {
				if (collectExceptions) {
					Log.fverbose("FAILURE -- see exceptions below");
					exceptionsCollector.add(new WorkflowAutoTransitionFailedException(throwable, item));
					result.addFailed(item);
				} else {
					throw throwable;
				}
			}
		}

		return result;
	}

	private void updateTasksAndDelegations(WorkflowAutoTransitionsResult result) {

		Log.fverbose("Updating tasks and delegations...");

		new WorkflowTasksAndDelegationsUpdater()//
			.setExceptionsCollector(exceptionsCollector)
			.updateAll(result.getTransitioned());

		Log.fverbose("Updated tasks and delegations.");
	}

	private boolean cascadeLimitReached(AGWorkflowItem item) {

		return cascadeLengthMap.getOrDefault(item, 0) > cascadeLengthLimit;
	}
}
