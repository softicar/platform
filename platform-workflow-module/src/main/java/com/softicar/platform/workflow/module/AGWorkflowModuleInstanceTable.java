package com.softicar.platform.workflow.module;

import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.ModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;

public class AGWorkflowModuleInstanceTable extends ModuleInstanceTable<AGWorkflowModuleInstance> {

	public AGWorkflowModuleInstanceTable(IDbSubObjectTableBuilder<AGWorkflowModuleInstance, AGModuleInstanceBase, Integer> builder) {

		super(builder, WorkflowModule.class);
	}
}
