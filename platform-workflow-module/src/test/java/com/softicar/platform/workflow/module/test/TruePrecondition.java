package com.softicar.platform.workflow.module.test;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.workflow.module.workflow.node.precondition.IWorkflowPrecondition;

@SourceCodeReferencePointUuid("80a7305f-7b3e-4080-9212-a086511f9f40")
public class TruePrecondition implements IWorkflowPrecondition<WorkflowTestObject> {

	@Override
	public IDisplayString getTitle() {

		return IDisplayString.create("True");
	}

	@Override
	public boolean test(WorkflowTestObject tableRow) {

		return true;
	}

	@Override
	public IDisplayString getErrorMessage() {

		return IDisplayString.create("Always true.");
	}
}
