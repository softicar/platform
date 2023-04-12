package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import java.util.Objects;

/**
 * Executes one or several consecutive workflow auto transitions, i.e. a cascade
 * of auto transitions, for an {@link AGWorkflowItem}.
 *
 * @author Alexander Schmidt
 */
public class WorkflowAutoTransitionCascadedExecutor {

	private static final int DEFAULT_CASCADE_LENGTH_LIMIT = 100;

	private final AGWorkflowItem item;
	private final WorkflowAutoTransitionExecutor executor;
	private int cascadeLengthLimit = DEFAULT_CASCADE_LENGTH_LIMIT;

	/**
	 * Creates a new instance.
	 *
	 * @param item
	 *            the {@link AGWorkflowItem} to execute auto transitions for
	 *            (never <i>null</i>)
	 */
	public WorkflowAutoTransitionCascadedExecutor(AGWorkflowItem item) {

		this.item = Objects.requireNonNull(item);
		this.executor = new WorkflowAutoTransitionExecutor(item);
	}

	/**
	 * Defines the maximum length of an auto transition cascade.
	 *
	 * @param cascadeLengthLimit
	 * @return this
	 */
	public WorkflowAutoTransitionCascadedExecutor setCascadeLengthLimit(int cascadeLengthLimit) {

		this.cascadeLengthLimit = cascadeLengthLimit;
		return this;
	}

	/**
	 * Executes the longest possible cascade of auto transitions for the
	 * {@link AGWorkflowItem}, using consecutive calls of
	 * {@link WorkflowAutoTransitionExecutor#evaluateAndExecute(java.util.Collection)}.
	 * <p>
	 * When the length limit is reached while processing a cascade, an
	 * {@link Exception} is thrown.
	 */
	public void evaluateAndExecuteCascaded() {

		int counter = 0;
		while (evaluateAndExecute()) {
			counter++;
			if (counter >= cascadeLengthLimit) {
				throw new SofticarException("Exceeded the maximum cascade length of %s.", cascadeLengthLimit);
			}
		}
	}

	private boolean evaluateAndExecute() {

		return executor.evaluateAndExecute(new WorkflowAutoTransitionsLoader().loadTransitions(item));
	}
}
