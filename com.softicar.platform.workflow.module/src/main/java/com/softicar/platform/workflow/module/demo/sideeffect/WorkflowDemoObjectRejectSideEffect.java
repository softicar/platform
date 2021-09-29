package com.softicar.platform.workflow.module.demo.sideeffect;

import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.workflow.module.demo.AGWorkflowDemoObject;
import com.softicar.platform.workflow.module.demo.approver.AGWorkflowDemoObjectApprover;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.side.effect.IWorkflowTransitionSideEffect;

@EmfSourceCodeReferencePointUuid("d1eccfc3-7737-41a6-a239-60d1983a7aa9")
public class WorkflowDemoObjectRejectSideEffect implements IWorkflowTransitionSideEffect<AGWorkflowDemoObject> {

	@Override
	public void execute(AGWorkflowDemoObject object, AGWorkflowTransition transition) {

		AGWorkflowDemoObjectApprover.TABLE//
			.createSelect()
			.where(AGWorkflowDemoObjectApprover.OBJECT.isEqual(object))
			.where(AGWorkflowDemoObjectApprover.USER.isEqual(CurrentUser.get()))
			.forEach(approver -> approver.setApproved(false).save());
	}

	@Override
	public Class<AGWorkflowDemoObject> getValueClass() {

		return AGWorkflowDemoObject.class;
	}
}
