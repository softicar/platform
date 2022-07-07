package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

/**
 * This is the automatically generated class AGWorkflow for
 * database table <i>Workflow.Workflow</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowGenerated extends AbstractDbObject<AGWorkflow> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflow, AGWorkflowGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "Workflow", AGWorkflow::new, AGWorkflow.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOWS);
	}

	public static final IDbIdField<AGWorkflow> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignRowField<AGWorkflow, AGWorkflowModuleInstance, AGModuleInstanceBase> MODULE_INSTANCE = BUILDER.addForeignRowField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGWorkflowModuleInstance.BASE).setTitle(WorkflowI18n.MODULE_INSTANCE);
	public static final IDbStringField<AGWorkflow> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(WorkflowI18n.NAME).setMaximumLength(255);
	public static final IDbForeignField<AGWorkflow, AGUuid> ENTITY_TABLE = BUILDER.addForeignField("entityTable", o->o.m_entityTable, (o,v)->o.m_entityTable=v, AGUuid.ID).setTitle(WorkflowI18n.ENTITY_TABLE);
	public static final IDbBooleanField<AGWorkflow> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGWorkflow, AGWorkflowVersion> CURRENT_VERSION = BUILDER.addForeignField("currentVersion", o->o.m_currentVersion, (o,v)->o.m_currentVersion=v, AGWorkflowVersion.ID).setTitle(WorkflowI18n.CURRENT_VERSION).setNullable().setDefault(null);
	public static final AGWorkflowTable TABLE = new AGWorkflowTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflow> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflow get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGWorkflowModuleInstance getModuleInstance() {

		return getValue(MODULE_INSTANCE);
	}

	public final AGWorkflow setModuleInstance(AGWorkflowModuleInstance value) {

		return setValue(MODULE_INSTANCE, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGWorkflow setName(String value) {

		return setValue(NAME, value);
	}

	public final Integer getEntityTableID() {

		return getValueId(ENTITY_TABLE);
	}

	public final AGUuid getEntityTable() {

		return getValue(ENTITY_TABLE);
	}

	public final AGWorkflow setEntityTable(AGUuid value) {

		return setValue(ENTITY_TABLE, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflow setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getCurrentVersionID() {

		return getValueId(CURRENT_VERSION);
	}

	public final AGWorkflowVersion getCurrentVersion() {

		return getValue(CURRENT_VERSION);
	}

	public final AGWorkflow setCurrentVersion(AGWorkflowVersion value) {

		return setValue(CURRENT_VERSION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowModuleInstance m_moduleInstance;
	private String m_name;
	private AGUuid m_entityTable;
	private Boolean m_active;
	private AGWorkflowVersion m_currentVersion;
}

