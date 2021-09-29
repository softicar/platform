package com.softicar.platform.workflow.module.demo.preconditions;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.demo.AGWorkflowDemoObject;
import com.softicar.platform.workflow.module.workflow.node.precondition.IWorkflowPrecondition;

@EmfSourceCodeReferencePointUuid("67a1bb5f-b049-4579-92f4-fd6bb2f03ab8")
public class NoFurtherApprovalIsRequiredPrecondition implements IWorkflowPrecondition<AGWorkflowDemoObject> {

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.NO_FURTHER_APPROVAL_IS_REQUIRED;
	}

	@Override
	public boolean test(AGWorkflowDemoObject object) {

		return !object.isFurtherApprovalRequired();
	}

	@Override
	public IDisplayString getErrorMessage() {

		return WorkflowI18n.NO_FURTHER_APPROVAL_IS_REQUIRED;
	}
}
