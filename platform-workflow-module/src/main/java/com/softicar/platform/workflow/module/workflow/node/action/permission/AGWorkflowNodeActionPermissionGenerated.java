package com.softicar.platform.workflow.module.workflow.node.action.permission;

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
import com.softicar.platform.workflow.module.workflow.node.action.AGWorkflowNodeAction;

/**
 * This is the automatically generated class AGWorkflowNodeActionPermission for
 * database table <i>Workflow.WorkflowNodeActionPermission</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowNodeActionPermissionGenerated extends AbstractDbObject<AGWorkflowNodeActionPermission> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowNodeActionPermission, AGWorkflowNodeActionPermissionGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowNodeActionPermission", AGWorkflowNodeActionPermission::new, AGWorkflowNodeActionPermission.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_NODE_ACTION_PERMISSION);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_NODE_ACTION_PERMISSIONS);
	}

	public static final IDbIdField<AGWorkflowNodeActionPermission> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowNodeActionPermission, AGWorkflowNodeAction> WORKFLOW_NODE_ACTION = BUILDER.addForeignField("workflowNodeAction", o->o.m_workflowNodeAction, (o,v)->o.m_workflowNodeAction=v, AGWorkflowNodeAction.ID).setTitle(WorkflowI18n.WORKFLOW_NODE_ACTION).setForeignKeyName("WorkflowNodeActionPermission_ibfk_1");
	public static final IDbBooleanField<AGWorkflowNodeActionPermission> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGWorkflowNodeActionPermission, AGUuid> PERMISSION_UUID = BUILDER.addForeignField("permissionUuid", o->o.m_permissionUuid, (o,v)->o.m_permissionUuid=v, AGUuid.ID).setTitle(WorkflowI18n.PERMISSION_UUID).setForeignKeyName("WorkflowNodeActionPermission_ibfk_2");
	public static final IDbKey<AGWorkflowNodeActionPermission> IK_WORKFLOW_NODE_ACTION = BUILDER.addIndexKey("workflowNodeAction", WORKFLOW_NODE_ACTION);
	public static final IDbKey<AGWorkflowNodeActionPermission> IK_PERMISSION_UUID = BUILDER.addIndexKey("permissionUuid", PERMISSION_UUID);
	public static final AGWorkflowNodeActionPermissionTable TABLE = new AGWorkflowNodeActionPermissionTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowNodeActionPermission> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowNodeActionPermission get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowNodeActionID() {

		return getValueId(WORKFLOW_NODE_ACTION);
	}

	public final AGWorkflowNodeAction getWorkflowNodeAction() {

		return getValue(WORKFLOW_NODE_ACTION);
	}

	public final AGWorkflowNodeActionPermission setWorkflowNodeAction(AGWorkflowNodeAction value) {

		return setValue(WORKFLOW_NODE_ACTION, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowNodeActionPermission setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getPermissionUuidID() {

		return getValueId(PERMISSION_UUID);
	}

	public final AGUuid getPermissionUuid() {

		return getValue(PERMISSION_UUID);
	}

	public final AGWorkflowNodeActionPermission setPermissionUuid(AGUuid value) {

		return setValue(PERMISSION_UUID, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowNodeActionPermissionTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowNodeAction m_workflowNodeAction;
	private Boolean m_active;
	private AGUuid m_permissionUuid;
}

