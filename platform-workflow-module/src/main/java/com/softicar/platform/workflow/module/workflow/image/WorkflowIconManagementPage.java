package com.softicar.platform.workflow.module.workflow.image;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowModule;

@SourceCodeReferencePointUuid("100e43d2-9e47-41b4-9ed0-5d01e64af1e3")
public class WorkflowIconManagementPage extends AbstractEmfManagementPage<AGWorkflowModuleInstance> {

	@Override
	public Class<WorkflowModule> getModuleClass() {

		return WorkflowModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGWorkflowModuleInstance> getTable() {

		return AGWorkflowIcon.TABLE;
	}

	@Override
	public EmfPagePath getPagePath(EmfPagePath modulePath) {

		return modulePath.append(WorkflowI18n.MASTER_DATA);
	}
}
