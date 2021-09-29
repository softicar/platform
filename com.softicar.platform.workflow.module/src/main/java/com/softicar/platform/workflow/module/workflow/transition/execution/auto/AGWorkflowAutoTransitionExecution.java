package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.WorkflowI18n;

public class AGWorkflowAutoTransitionExecution extends AGWorkflowAutoTransitionExecutionGenerated implements IEmfObject<AGWorkflowAutoTransitionExecution> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return WorkflowI18n.AUTO_TRANSITION_ARG1_ARG2
			.toDisplay(//
				getWorkflowTransition().getSourceNode().toDisplayWithoutId(),
				getWorkflowTransition().getTargetNode().toDisplayWithoutId());
	}

	public IDisplayString getWorkflowTransitionToDisplay() {

		return getWorkflowTransition().toDisplay();
	}

	public DayTime getAt() {

		return getTransaction().getAt();
	}
}
