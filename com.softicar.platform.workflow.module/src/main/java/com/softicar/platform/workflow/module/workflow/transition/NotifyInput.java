package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.emf.attribute.field.bool.EmfBooleanInput;

public class NotifyInput extends EmfBooleanInput {

	private final AGWorkflowTransition transition;

	public NotifyInput(AGWorkflowTransition transition) {

		this.transition = transition;
	}

	@Override
	public void refreshInputConstraints() {

		if (transition.isAutoTransition()) {
			setValue(false);
			setEnabled(false);
		} else {
			setEnabled(true);
		}
	}
}
