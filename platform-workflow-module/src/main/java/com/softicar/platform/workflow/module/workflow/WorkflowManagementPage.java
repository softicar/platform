package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.WorkflowModule;

@EmfSourceCodeReferencePointUuid("eb0b7a20-e06e-472d-b8f3-73336ac201d1")
public class WorkflowManagementPage extends AbstractEmfManagementPage<AGWorkflowModuleInstance> {

	@Override
	public Class<WorkflowModule> getModuleClass() {

		return WorkflowModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGWorkflowModuleInstance> getTable() {

		return AGWorkflow.TABLE;
	}
}
