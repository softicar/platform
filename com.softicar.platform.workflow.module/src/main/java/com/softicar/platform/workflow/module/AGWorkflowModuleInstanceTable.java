package com.softicar.platform.workflow.module;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.StandardModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;

public class AGWorkflowModuleInstanceTable extends StandardModuleInstanceTable<AGWorkflowModuleInstance> {

	public AGWorkflowModuleInstanceTable(IDbSubObjectTableBuilder<AGWorkflowModuleInstance, AGModuleInstance, Integer> builder) {

		super(builder, WorkflowModule.class);
	}
}
