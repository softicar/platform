package com.softicar.platform.workflow.module.workflow.export;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.WorkflowPredicates;
import com.softicar.platform.workflow.module.workflow.version.export.WorkflowVersionExporter;

public class WorkflowExportAction implements IEmfManagementAction<AGWorkflow> {

	@Override
	public IEmfPredicate<AGWorkflow> getPrecondition() {

		return WorkflowPredicates.HAS_CURRENT_VERSION;
	}

	@Override
	public IEmfPermission<AGWorkflow> getRequiredPermission() {

		return WorkflowPermissions.VIEWER.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getModuleInstance()));
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
	public void handleClick(AGWorkflow workflow) {

		new WorkflowVersionExporter(workflow.getCurrentVersionOrThrow()).export(CurrentDomDocument.get());
	}
}
