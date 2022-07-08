package com.softicar.platform.workflow.module.demo;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.WorkflowModule;

@SourceCodeReferencePointUuid("dc2c8087-4e37-4146-ae1c-862ff1826a88")
public class WorkflowDemoObjectManagementPage extends AbstractEmfManagementPage<AGWorkflowModuleInstance> {

	@Override
	public Class<WorkflowModule> getModuleClass() {

		return WorkflowModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGWorkflowModuleInstance> getTable() {

		return AGWorkflowDemoObject.TABLE;
	}
}
