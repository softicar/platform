package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.workflow.module.workflow.node.precondition.IWorkflowPrecondition;

@SourceCodeReferencePointUuid("f0695df4-bc64-457a-9cc5-215cf70b42cb")
public class WorkflowTestDocumentQuantityHighPrecondition implements IWorkflowPrecondition<WorkflowTestDocument> {

	@Override
	public IDisplayString getTitle() {

		return IDisplayString.create("Quantity High");
	}

	@Override
	public boolean test(WorkflowTestDocument document) {

		return document.getQuantity() >= 10;
	}

	@Override
	public IDisplayString getErrorMessage() {

		return IDisplayString.create("Quantity was too low.");
	}
}
