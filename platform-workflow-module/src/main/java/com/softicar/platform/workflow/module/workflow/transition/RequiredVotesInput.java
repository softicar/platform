package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.emf.attribute.field.string.EmfStringInput;

class RequiredVotesInput extends EmfStringInput {

	private final AGWorkflowTransition transition;

	public RequiredVotesInput(AGWorkflowTransition transition) {

		this.transition = transition;
	}

	@Override
	public void refreshInputConstraints() {

		if (transition.isAutoTransition()) {
			setValue("0");
			setEnabled(false);
		} else {
			setEnabled(true);
		}
	}
}
