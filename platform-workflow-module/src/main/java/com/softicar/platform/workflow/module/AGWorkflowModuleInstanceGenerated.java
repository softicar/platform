package com.softicar.platform.workflow.module;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;

/**
 * This is the automatically generated class AGWorkflowModuleInstance for
 * database table <i>Workflow.WorkflowModuleInstance</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowModuleInstanceGenerated extends AbstractDbSubObject<AGWorkflowModuleInstance, AGModuleInstanceBase> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbSubObjectTableBuilder<AGWorkflowModuleInstance, AGWorkflowModuleInstanceGenerated, AGModuleInstanceBase, Integer> BUILDER = new DbSubObjectTableBuilder<>("Workflow", "WorkflowModuleInstance", AGWorkflowModuleInstance::new, AGWorkflowModuleInstance.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_MODULE_INSTANCE);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_MODULE_INSTANCES);
	}

	public static final IDbBaseField<AGWorkflowModuleInstance, AGModuleInstanceBase, Integer> BASE = BUILDER.addBaseField("base", o->o.m_base, (o,v)->o.m_base=v, AGModuleInstanceBase.TABLE).setTitle(WorkflowI18n.BASE);
	public static final AGWorkflowModuleInstanceTable TABLE = new AGWorkflowModuleInstanceTable(BUILDER);
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGModuleInstanceBase getBase() {

		return pk();
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowModuleInstanceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGModuleInstanceBase m_base;
}

