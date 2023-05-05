package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.workflow.module.workflow.node.precondition.IWorkflowPrecondition;

@SourceCodeReferencePointUuid("89e9e5f9-e53e-4b4f-aac8-866b181b2acc")
public class WorkflowTestDocumentTruePrecondition implements IWorkflowPrecondition<WorkflowTestDocument> {

	@Override
	public IDisplayString getTitle() {

		return IDisplayString.create("True");
	}

	@Override
	public boolean test(WorkflowTestDocument tableRow) {

		return true;
	}

	@Override
	public IDisplayString getErrorMessage() {

		return IDisplayString.create("Always true.");
	}
}
