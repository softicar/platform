package com.softicar.platform.workflow.module.workflow.version.export;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public class WorkflowVersionExportAction implements IEmfManagementAction<AGWorkflowVersion> {

	@Override
	public IEmfPredicate<AGWorkflowVersion> getPrecondition() {

		return EmfPredicates.always();
	}

	@Override
	public IEmfPermission<AGWorkflowVersion> getRequiredPermission() {

		return WorkflowPermissions.VIEW.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflow().getModuleInstance()));
	}

	@Override
	public IResource getIcon() {

		return WorkflowImages.STORED_FILE_DOWNLOAD.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.EXPORT;
	}

	@Override
	public void handleClick(AGWorkflowVersion workflowVersion) {

		new WorkflowVersionExporter(workflowVersion).export(CurrentDomDocument.get());
	}
}
