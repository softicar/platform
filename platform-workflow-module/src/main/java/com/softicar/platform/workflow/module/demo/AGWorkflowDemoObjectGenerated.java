package com.softicar.platform.workflow.module.demo;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

/**
 * This is the automatically generated class AGWorkflowDemoObject for
 * database table <i>Workflow.WorkflowDemoObject</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowDemoObjectGenerated extends AbstractDbObject<AGWorkflowDemoObject> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowDemoObject, AGWorkflowDemoObjectGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowDemoObject", AGWorkflowDemoObject::new, AGWorkflowDemoObject.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_DEMO_OBJECT);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_DEMO_OBJECTS);
	}

	public static final IDbIdField<AGWorkflowDemoObject> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignRowField<AGWorkflowDemoObject, AGWorkflowModuleInstance, AGModuleInstanceBase> MODULE_INSTANCE = BUILDER.addForeignRowField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGWorkflowModuleInstance.BASE).setTitle(WorkflowI18n.MODULE_INSTANCE).setForeignKeyName("WorkflowDemoObject_ibfk_1");
	public static final IDbStringField<AGWorkflowDemoObject> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(WorkflowI18n.NAME).setMaximumLength(50);
	public static final IDbBooleanField<AGWorkflowDemoObject> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGWorkflowDemoObject, AGWorkflowItem> WORKFLOW_ITEM = BUILDER.addForeignField("workflowItem", o->o.m_workflowItem, (o,v)->o.m_workflowItem=v, AGWorkflowItem.ID).setTitle(WorkflowI18n.WORKFLOW_ITEM).setNullable().setDefault(null).setForeignKeyName("WorkflowDemoObject_ibfk_2");
	public static final IDbKey<AGWorkflowDemoObject> UK_MODULE_INSTANCE_NAME = BUILDER.addUniqueKey("moduleInstanceName", MODULE_INSTANCE, NAME);
	public static final IDbKey<AGWorkflowDemoObject> IK_WORKFLOW_ITEM = BUILDER.addIndexKey("workflowItem", WORKFLOW_ITEM);
	public static final AGWorkflowDemoObjectTable TABLE = new AGWorkflowDemoObjectTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowDemoObject> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowDemoObject get(Integer id) {

		return TABLE.get(id);
	}

	public static AGWorkflowDemoObject loadByModuleInstanceAndName(AGWorkflowModuleInstance moduleInstance, String name) {

		return TABLE//
				.createSelect()
				.where(MODULE_INSTANCE.isEqual(moduleInstance))
				.where(NAME.isEqual(name))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGWorkflowModuleInstance getModuleInstance() {

		return getValue(MODULE_INSTANCE);
	}

	public final AGWorkflowDemoObject setModuleInstance(AGWorkflowModuleInstance value) {

		return setValue(MODULE_INSTANCE, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGWorkflowDemoObject setName(String value) {

		return setValue(NAME, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowDemoObject setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getWorkflowItemID() {

		return getValueId(WORKFLOW_ITEM);
	}

	public final AGWorkflowItem getWorkflowItem() {

		return getValue(WORKFLOW_ITEM);
	}

	public final AGWorkflowDemoObject setWorkflowItem(AGWorkflowItem value) {

		return setValue(WORKFLOW_ITEM, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowDemoObjectTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowModuleInstance m_moduleInstance;
	private String m_name;
	private Boolean m_active;
	private AGWorkflowItem m_workflowItem;
}

