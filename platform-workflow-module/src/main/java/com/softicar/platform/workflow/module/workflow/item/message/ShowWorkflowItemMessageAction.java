package com.softicar.platform.workflow.module.workflow.item.message;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;

public class ShowWorkflowItemMessageAction<R extends IWorkflowableObject<R>> implements IEmfManagementAction<R> {

	@Override
	public IEmfPredicate<R> getPrecondition() {

		return new EmfPredicate<>(WorkflowI18n.WORKFLOW_ITEM_PRESENT, r -> r.getWorkflowItem() != null);
	}

	@Override
	public IEmfPermission<R> getRequiredPermission() {

		return EmfPermissions.anybody();
	}

	@Override
	public IResource getIcon() {

		return AGWorkflowItemMessage.TABLE.getIcon();
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.MESSAGES;
	}

	@Override
	public void handleClick(R tableRow) {

		new WorkflowItemMessagePopup(tableRow.getWorkflowItem()).open();
	}
}
