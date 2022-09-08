package com.softicar.platform.workflow.module.workflow.transition.permission;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;

/**
 * This is the automatically generated class AGWorkflowTransitionPermission for
 * database table <i>Workflow.WorkflowTransitionPermission</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowTransitionPermissionGenerated extends AbstractDbObject<AGWorkflowTransitionPermission> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowTransitionPermission, AGWorkflowTransitionPermissionGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowTransitionPermission", AGWorkflowTransitionPermission::new, AGWorkflowTransitionPermission.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_TRANSITION_PERMISSION);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_TRANSITION_PERMISSIONS);
	}

	public static final IDbIdField<AGWorkflowTransitionPermission> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowTransitionPermission, AGWorkflowTransition> TRANSITION = BUILDER.addForeignField("transition", o->o.m_transition, (o,v)->o.m_transition=v, AGWorkflowTransition.ID).setTitle(WorkflowI18n.TRANSITION).setForeignKeyName("WorkflowTransitionPermission_ibfk_1");
	public static final IDbForeignField<AGWorkflowTransitionPermission, AGUuid> PERMISSION = BUILDER.addForeignField("permission", o->o.m_permission, (o,v)->o.m_permission=v, AGUuid.ID).setTitle(WorkflowI18n.PERMISSION).setForeignKeyName("WorkflowTransitionPermission_ibfk_2");
	public static final IDbBooleanField<AGWorkflowTransitionPermission> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setDefault(true);
	public static final IDbKey<AGWorkflowTransitionPermission> IK_TRANSITION = BUILDER.addIndexKey("transition", TRANSITION);
	public static final IDbKey<AGWorkflowTransitionPermission> IK_PERMISSION = BUILDER.addIndexKey("permission", PERMISSION);
	public static final AGWorkflowTransitionPermissionTable TABLE = new AGWorkflowTransitionPermissionTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowTransitionPermission> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowTransitionPermission get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransitionID() {

		return getValueId(TRANSITION);
	}

	public final AGWorkflowTransition getTransition() {

		return getValue(TRANSITION);
	}

	public final AGWorkflowTransitionPermission setTransition(AGWorkflowTransition value) {

		return setValue(TRANSITION, value);
	}

	public final Integer getPermissionID() {

		return getValueId(PERMISSION);
	}

	public final AGUuid getPermission() {

		return getValue(PERMISSION);
	}

	public final AGWorkflowTransitionPermission setPermission(AGUuid value) {

		return setValue(PERMISSION, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowTransitionPermission setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowTransitionPermissionTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowTransition m_transition;
	private AGUuid m_permission;
	private Boolean m_active;
}

