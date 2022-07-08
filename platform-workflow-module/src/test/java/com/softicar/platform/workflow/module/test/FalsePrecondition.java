package com.softicar.platform.workflow.module.test;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.workflow.module.workflow.node.precondition.IWorkflowPrecondition;

@SourceCodeReferencePointUuid("bfb13fb4-b354-4fa7-838c-109f3befcf77")
public class FalsePrecondition implements IWorkflowPrecondition<WorkflowTestObject> {

	@Override
	public IDisplayString getTitle() {

		return IDisplayString.create("False");
	}

	@Override
	public boolean test(WorkflowTestObject tableRow) {

		return false;
	}

	@Override
	public IDisplayString getErrorMessage() {

		return IDisplayString.create("Always false.");
	}
}
