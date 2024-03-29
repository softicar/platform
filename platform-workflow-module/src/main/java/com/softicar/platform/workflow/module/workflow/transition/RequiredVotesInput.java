package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.emf.attribute.field.string.EmfStringInput;

class RequiredVotesInput extends EmfStringInput {

	private final AGWorkflowTransition transition;

	public RequiredVotesInput(AGWorkflowTransition transition) {

		this.transition = transition;
		refreshInputConstraints();
	}

	@Override
	public void refreshInputConstraints() {

		if (transition.isAutoTransition()) {
			setValue("0");
			setDisabled(true);
		} else {
			setDisabled(false);
		}
	}
}
