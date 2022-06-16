package com.softicar.platform.workflow.module;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;

/**
 * This is the automatically generated class AGWorkflowModuleInstance for
 * database table <i>Workflow.WorkflowModuleInstance</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowModuleInstanceGenerated extends AbstractDbSubObject<AGWorkflowModuleInstance, AGModuleInstance> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbSubObjectTableBuilder<AGWorkflowModuleInstance, AGWorkflowModuleInstanceGenerated, AGModuleInstance, Integer> BUILDER = new DbSubObjectTableBuilder<>("Workflow", "WorkflowModuleInstance", AGWorkflowModuleInstance::new, AGWorkflowModuleInstance.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_MODULE_INSTANCE);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_MODULE_INSTANCES);
	}

	public static final IDbBaseField<AGWorkflowModuleInstance, AGModuleInstance, Integer> MODULE_INSTANCE = BUILDER.addBaseField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.TABLE).setTitle(WorkflowI18n.MODULE_INSTANCE);
	public static final AGWorkflowModuleInstanceTable TABLE = new AGWorkflowModuleInstanceTable(BUILDER);
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGModuleInstance getModuleInstance() {

		return pk();
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowModuleInstanceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGModuleInstance m_moduleInstance;
}

