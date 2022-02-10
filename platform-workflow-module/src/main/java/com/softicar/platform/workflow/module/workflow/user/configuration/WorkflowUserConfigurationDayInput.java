package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.emf.attribute.field.day.EmfDayInput;

public class WorkflowUserConfigurationDayInput extends EmfDayInput {

	private final AGWorkflowUserConfiguration userConfiguration;

	public WorkflowUserConfigurationDayInput(AGWorkflowUserConfiguration userConfiguration) {

		this.userConfiguration = userConfiguration;
	}

	@Override
	public void refreshInputConstraints() {

		if (userConfiguration.getSubstitute() == null) {
			setValue(null);
		}
	}
}
