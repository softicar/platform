package com.softicar.platform.workflow.module;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.AbstractStandardModule;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstanceTable;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("6bb6a3d0-84d3-4594-8e56-baf896e039d3")
public class WorkflowModule extends AbstractStandardModule<AGWorkflowModuleInstance> {

	@Override
	public IStandardModuleInstanceTable<AGWorkflowModuleInstance> getModuleInstanceTable() {

		return AGWorkflowModuleInstance.TABLE;
	}

	@Override
	public IDisplayString toDisplay() {

		return WorkflowI18n.WORKFLOW;
	}

	@Override
	public EmfPagePath getDefaultPagePath(AGWorkflowModuleInstance moduleInstance) {

		return CoreModule.getParentPagePath().append(toDisplay());
	}
}
