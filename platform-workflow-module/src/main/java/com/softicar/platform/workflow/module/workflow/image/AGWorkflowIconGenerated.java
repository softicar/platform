package com.softicar.platform.workflow.module.workflow.image;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
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

/**
 * This is the automatically generated class AGWorkflowIcon for
 * database table <i>Workflow.WorkflowIcon</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowIconGenerated extends AbstractDbObject<AGWorkflowIcon> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowIcon, AGWorkflowIconGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowIcon", AGWorkflowIcon::new, AGWorkflowIcon.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_ICON);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_ICONS);
	}

	public static final IDbIdField<AGWorkflowIcon> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignRowField<AGWorkflowIcon, AGWorkflowModuleInstance, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignRowField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGWorkflowModuleInstance.MODULE_INSTANCE).setTitle(WorkflowI18n.MODULE_INSTANCE);
	public static final IDbStringField<AGWorkflowIcon> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(WorkflowI18n.NAME).setMaximumLength(255);
	public static final IDbForeignField<AGWorkflowIcon, AGStoredFile> ICON = BUILDER.addForeignField("icon", o->o.m_icon, (o,v)->o.m_icon=v, AGStoredFile.ID).setTitle(WorkflowI18n.ICON);
	public static final IDbKey<AGWorkflowIcon> UK_MODULE_INSTANCE_NAME = BUILDER.addUniqueKey("moduleInstanceName", MODULE_INSTANCE, NAME);
	public static final AGWorkflowIconTable TABLE = new AGWorkflowIconTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowIcon> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowIcon get(Integer id) {

		return TABLE.get(id);
	}

	public static AGWorkflowIcon loadByModuleInstanceAndName(AGWorkflowModuleInstance moduleInstance, String name) {

		return TABLE//
				.createSelect()
				.where(MODULE_INSTANCE.equal(moduleInstance))
				.where(NAME.equal(name))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGWorkflowModuleInstance getModuleInstance() {

		return getValue(MODULE_INSTANCE);
	}

	public final AGWorkflowIcon setModuleInstance(AGWorkflowModuleInstance value) {

		return setValue(MODULE_INSTANCE, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGWorkflowIcon setName(String value) {

		return setValue(NAME, value);
	}

	public final Integer getIconID() {

		return getValueId(ICON);
	}

	public final AGStoredFile getIcon() {

		return getValue(ICON);
	}

	public final AGWorkflowIcon setIcon(AGStoredFile value) {

		return setValue(ICON, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowIconTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowModuleInstance m_moduleInstance;
	private String m_name;
	private AGStoredFile m_icon;
}

