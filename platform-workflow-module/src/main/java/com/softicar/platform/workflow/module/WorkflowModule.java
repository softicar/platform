package com.softicar.platform.workflow.module;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.AbstractModule;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.message.AGWorkflowItemMessage;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.transition.execution.AGWorkflowTransitionExecution;
import com.softicar.platform.workflow.module.workflow.transition.execution.auto.AGWorkflowAutoTransitionExecution;
import java.util.Collection;
import java.util.List;

@SourceCodeReferencePointUuid("6bb6a3d0-84d3-4594-8e56-baf896e039d3")
public class WorkflowModule extends AbstractModule<AGWorkflowModuleInstance> {

	@Override
	public IModuleInstanceTable<AGWorkflowModuleInstance> getModuleInstanceTable() {

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

	@Override
	public Collection<IDbTable<?, ?>> getTransactionalDataTables() {

		return List
			.of(//
				AGWorkflowAutoTransitionExecution.TABLE,
				AGWorkflowItem.TABLE,
				AGWorkflowItemMessage.TABLE,
				AGWorkflowTask.TABLE,
				AGWorkflowTransitionExecution.TABLE);
	}
}
