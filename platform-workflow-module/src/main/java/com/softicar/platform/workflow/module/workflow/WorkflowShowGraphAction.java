package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.workflow.management.WorkflowVersionManagementAction;
import java.util.Optional;

public class WorkflowShowGraphAction implements IEmfManagementAction<AGWorkflow> {

	@Override
	public IEmfPredicate<AGWorkflow> getPrecondition() {

		return WorkflowPredicates.HAS_CURRENT_VERSION;
	}

	@Override
	public IEmfPermission<AGWorkflow> getRequiredPermission() {

		return EmfPermissions.always();
	}

	@Override
	public IResource getIcon() {

		return WorkflowImages.WORKFLOW_GRAPH.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.SHOW_GRAPH;
	}

	@Override
	public void handleClick(AGWorkflow workflow) {

		Optional//
			.ofNullable(workflow.getCurrentVersion())
			.ifPresent(version -> new WorkflowVersionManagementAction().handleClick(version));
	}
}
