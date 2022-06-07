package com.softicar.platform.workflow.module.workflow.management;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public class WorkflowVersionManagementAction implements IEmfManagementAction<AGWorkflowVersion> {

	@Override
	public IEmfPredicate<AGWorkflowVersion> getPrecondition() {

		return EmfPredicates.always();
	}

	@Override
	public IEmfPermission<AGWorkflowVersion> getRequiredPermission() {

		return EmfPermissions.always();
	}

	@Override
	public IResource getIcon() {

		return WorkflowImages.MANAGE_WORKFLOW.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.MANAGE_WORKFLOW;
	}

	@Override
	public void handleClick(AGWorkflowVersion workflow) {

		DomPopupManager//
			.getInstance()
			.getPopup(workflow, WorkflowVersionManagementPopup.class, WorkflowVersionManagementPopup::new)
			.open();
	}
}
