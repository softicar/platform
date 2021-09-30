package com.softicar.platform.workflow.module.demo.approver;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGWorkflowDemoObjectApprover extends AGWorkflowDemoObjectApproverGenerated implements IEmfObject<AGWorkflowDemoObjectApprover> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getUser().toDisplayWithoutId();
	}
}
