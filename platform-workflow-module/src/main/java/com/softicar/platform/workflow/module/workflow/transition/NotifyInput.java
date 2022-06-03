package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.emf.attribute.field.bool.EmfBooleanInput;

public class NotifyInput extends EmfBooleanInput {

	private final AGWorkflowTransition transition;

	public NotifyInput(AGWorkflowTransition transition) {

		super(false);
		this.transition = transition;
		refreshInputConstraints();
	}

	@Override
	public void refreshInputConstraints() {

		if (transition.isAutoTransition()) {
			setValue(false);
			setDisabled(true);
		} else {
			setDisabled(false);
		}
	}
}
