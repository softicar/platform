package com.softicar.platform.workflow.module.workflow.item.message.severity;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;

public class AGWorkflowMessageSeverity extends AGWorkflowMessageSeverityGenerated implements IDisplayable {

	@Override
	public IDisplayString toDisplay() {

		return getEnum().toDisplay();
	}

	public boolean isMoreSevereThan(AGWorkflowMessageSeverityEnum severityEnum) {

		return getEnum().ordinal() > severityEnum.ordinal();
	}
}
