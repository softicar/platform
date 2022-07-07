package com.softicar.platform.workflow.module.demo.preconditions;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.demo.AGWorkflowDemoObject;
import com.softicar.platform.workflow.module.workflow.node.precondition.IWorkflowPrecondition;

@SourceCodeReferencePointUuid("cf634ee4-b291-4e19-9f56-7db54c6c7bf3")
public class FurtherApprovalIsRequiredPrecondition implements IWorkflowPrecondition<AGWorkflowDemoObject> {

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.FURTHER_APPROVAL_IS_REQUIRED;
	}

	@Override
	public boolean test(AGWorkflowDemoObject object) {

		return object.isFurtherApprovalRequired();
	}

	@Override
	public IDisplayString getErrorMessage() {

		return WorkflowI18n.FURTHER_APPROVAL_IS_REQUIRED;
	}
}
