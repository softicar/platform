package com.softicar.platform.workflow.module.workflow.node.action.role;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.node.action.AGWorkflowNodeAction;

/**
 * This is the automatically generated class AGWorkflowNodeActionRole for
 * database table <i>Workflow.WorkflowNodeActionRole</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowNodeActionRoleGenerated extends AbstractDbObject<AGWorkflowNodeActionRole> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowNodeActionRole, AGWorkflowNodeActionRoleGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowNodeActionRole", AGWorkflowNodeActionRole::new, AGWorkflowNodeActionRole.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_NODE_ACTION_ROLE);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_NODE_ACTION_ROLES);
	}

	public static final IDbIdField<AGWorkflowNodeActionRole> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowNodeActionRole, AGWorkflowNodeAction> WORKFLOW_NODE_ACTION = BUILDER.addForeignField("workflowNodeAction", o->o.m_workflowNodeAction, (o,v)->o.m_workflowNodeAction=v, AGWorkflowNodeAction.ID).setTitle(WorkflowI18n.WORKFLOW_NODE_ACTION);
	public static final IDbBooleanField<AGWorkflowNodeActionRole> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGWorkflowNodeActionRole, AGUuid> ROLE_UUID = BUILDER.addForeignField("roleUuid", o->o.m_roleUuid, (o,v)->o.m_roleUuid=v, AGUuid.ID).setTitle(WorkflowI18n.ROLE_UUID);
	public static final AGWorkflowNodeActionRoleTable TABLE = new AGWorkflowNodeActionRoleTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowNodeActionRole> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowNodeActionRole get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowNodeActionID() {

		return getValueId(WORKFLOW_NODE_ACTION);
	}

	public final AGWorkflowNodeAction getWorkflowNodeAction() {

		return getValue(WORKFLOW_NODE_ACTION);
	}

	public final AGWorkflowNodeActionRole setWorkflowNodeAction(AGWorkflowNodeAction value) {

		return setValue(WORKFLOW_NODE_ACTION, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowNodeActionRole setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getRoleUuidID() {

		return getValueId(ROLE_UUID);
	}

	public final AGUuid getRoleUuid() {

		return getValue(ROLE_UUID);
	}

	public final AGWorkflowNodeActionRole setRoleUuid(AGUuid value) {

		return setValue(ROLE_UUID, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowNodeActionRoleTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowNodeAction m_workflowNodeAction;
	private Boolean m_active;
	private AGUuid m_roleUuid;
}

