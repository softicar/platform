package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowModule;

@SourceCodeReferencePointUuid("33c52ac9-b8a2-4c04-b696-986d72853b84")
public class WorkflowTaskPage implements IEmfPage<AGWorkflowModuleInstance> {

	@Override
	public Class<WorkflowModule> getModuleClass() {

		return WorkflowModule.class;
	}

	@Override
	public IDisplayString getTitle(AGWorkflowModuleInstance moduleInstance) {

		return WorkflowI18n.MY_TASKS;
	}

	@Override
	public IEmfPermission<AGWorkflowModuleInstance> getRequiredPermission() {

		return EmfDefaultModulePermissions.getModuleOperation();
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return EmfPagePath.EMPTY_PATH;
	}

	@Override
	public IDomNode createContentNode(AGWorkflowModuleInstance moduleInstance) {

		return new WorkflowTaskDiv();
	}
}
