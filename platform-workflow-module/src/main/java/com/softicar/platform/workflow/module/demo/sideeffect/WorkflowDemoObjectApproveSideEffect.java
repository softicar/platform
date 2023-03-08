package com.softicar.platform.workflow.module.demo.sideeffect;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.workflow.module.demo.AGWorkflowDemoObject;
import com.softicar.platform.workflow.module.demo.approver.AGWorkflowDemoObjectApprover;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.side.effect.IWorkflowTransitionSideEffect;

@SourceCodeReferencePointUuid("355fb0e3-2d1c-44e0-beb3-afb372d0a769")
public class WorkflowDemoObjectApproveSideEffect implements IWorkflowTransitionSideEffect<AGWorkflowDemoObject> {

	@Override
	public void execute(AGWorkflowDemoObject object, AGWorkflowTransition transition) {

		AGWorkflowDemoObjectApprover.TABLE//
			.createSelect()
			.where(AGWorkflowDemoObjectApprover.OBJECT.isEqual(object))
			.where(AGWorkflowDemoObjectApprover.USER.isEqual(CurrentUser.get()))
			.forEach(approver -> approver.setApproved(true).save());
	}

	@Override
	public Class<AGWorkflowDemoObject> getValueClass() {

		return AGWorkflowDemoObject.class;
	}
}
