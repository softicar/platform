package com.softicar.platform.workflow.module;

import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.ModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;

public class AGWorkflowModuleInstanceTable extends ModuleInstanceTable<AGWorkflowModuleInstance> {

	public AGWorkflowModuleInstanceTable(IDbSubObjectTableBuilder<AGWorkflowModuleInstance, AGModuleInstance, Integer> builder) {

		super(builder, WorkflowModule.class);
	}
}
