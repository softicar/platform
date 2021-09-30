package com.softicar.platform.workflow.module.demo;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.demo.approver.AGWorkflowDemoObjectApprover;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.transients.field.WorkflowNodeField;
import java.util.Optional;

public class AGWorkflowDemoObject extends AGWorkflowDemoObjectGenerated implements IWorkflowableObject<AGWorkflowDemoObject> {

	public static final WorkflowNodeField<AGWorkflowDemoObject> WORKFLOW_STATUS =
			new WorkflowNodeField<>(AGWorkflowDemoObject.WORKFLOW_ITEM, WorkflowI18n.WORKFLOW_STATUS);

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}

	public boolean isFurtherApprovalRequired() {

		return AGWorkflowDemoObjectApprover.TABLE//
			.createSelect()
			.where(AGWorkflowDemoObjectApprover.OBJECT.isEqual(this))
			.where(AGWorkflowDemoObjectApprover.ACTIVE)
			.where(AGWorkflowDemoObjectApprover.APPROVED.isNull())
			.exists();
	}

	public boolean isApprover(IBasicUser user) {

		return getCurrentApprovalTier()//
			.map(tier -> isApproverOnApprovalTier(user, tier))
			.orElse(false);
	}

	private Optional<Integer> getCurrentApprovalTier() {

		return Sql//
			.from(AGWorkflowDemoObjectApprover.TABLE)
			.select(AGWorkflowDemoObjectApprover.APPROVAL_TIER.min())
			.where(AGWorkflowDemoObjectApprover.OBJECT.isEqual(this))
			.where(AGWorkflowDemoObjectApprover.ACTIVE)
			.where(AGWorkflowDemoObjectApprover.APPROVED.isNull())
			.getOneAsOptional();
	}

	private boolean isApproverOnApprovalTier(IBasicUser user, int approvalTier) {

		return AGWorkflowDemoObjectApprover.TABLE//
			.createSelect()
			.where(AGWorkflowDemoObjectApprover.OBJECT.isEqual(this))
			.where(AGWorkflowDemoObjectApprover.APPROVAL_TIER.isEqual(approvalTier))
			.where(AGWorkflowDemoObjectApprover.USER.isEqual(AGUser.get(user)))
			.where(AGWorkflowDemoObjectApprover.ACTIVE)
			.where(AGWorkflowDemoObjectApprover.APPROVED.isNull())
			.exists();
	}
}
