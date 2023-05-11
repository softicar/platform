package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.workflow.module.workflow.node.precondition.IWorkflowPrecondition;

@SourceCodeReferencePointUuid("5a9dca2f-f089-467c-9089-5835f21a58dd")
public class WorkflowTestDocumentQuantityLowPrecondition implements IWorkflowPrecondition<WorkflowTestDocument> {

	@Override
	public IDisplayString getTitle() {

		return IDisplayString.create("Quantity Low");
	}

	@Override
	public boolean test(WorkflowTestDocument document) {

		return document.getQuantity() < 10;
	}

	@Override
	public IDisplayString getErrorMessage() {

		return IDisplayString.create("Quantity was too high.");
	}
}
