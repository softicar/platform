package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.emf.attribute.field.day.EmfDayInput;

public class WorkflowUserSettingsDayInput extends EmfDayInput {

	private final AGWorkflowUserSettings userConfiguration;

	public WorkflowUserSettingsDayInput(AGWorkflowUserSettings userConfiguration) {

		this.userConfiguration = userConfiguration;
	}

	@Override
	public void refreshInputConstraints() {

		if (userConfiguration.getSubstitute() == null) {
			setValue(null);
		}
	}
}
