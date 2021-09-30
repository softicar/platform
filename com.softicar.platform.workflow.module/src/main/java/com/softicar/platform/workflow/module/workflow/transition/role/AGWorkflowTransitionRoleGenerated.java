package com.softicar.platform.workflow.module.workflow.transition.role;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;

/**
 * This is the automatically generated class AGWorkflowTransitionRole for
 * database table <i>Workflow.WorkflowTransitionRole</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowTransitionRoleGenerated extends AbstractDbObject<AGWorkflowTransitionRole> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowTransitionRole, AGWorkflowTransitionRoleGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowTransitionRole", AGWorkflowTransitionRole::new, AGWorkflowTransitionRole.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_TRANSITION_ROLE);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_TRANSITION_ROLES);
	}

	public static final IDbIdField<AGWorkflowTransitionRole> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowTransitionRole, AGWorkflowTransition> TRANSITION = BUILDER.addForeignField("transition", o->o.m_transition, (o,v)->o.m_transition=v, AGWorkflowTransition.ID).setTitle(WorkflowI18n.TRANSITION);
	public static final IDbForeignField<AGWorkflowTransitionRole, AGUuid> ROLE = BUILDER.addForeignField("role", o->o.m_role, (o,v)->o.m_role=v, AGUuid.ID).setTitle(WorkflowI18n.ROLE);
	public static final IDbBooleanField<AGWorkflowTransitionRole> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setDefault(true);
	public static final AGWorkflowTransitionRoleTable TABLE = new AGWorkflowTransitionRoleTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowTransitionRole> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowTransitionRole get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransitionID() {

		return getValueId(TRANSITION);
	}

	public final AGWorkflowTransition getTransition() {

		return getValue(TRANSITION);
	}

	public final AGWorkflowTransitionRole setTransition(AGWorkflowTransition value) {

		return setValue(TRANSITION, value);
	}

	public final Integer getRoleID() {

		return getValueId(ROLE);
	}

	public final AGUuid getRole() {

		return getValue(ROLE);
	}

	public final AGWorkflowTransitionRole setRole(AGUuid value) {

		return setValue(ROLE, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowTransitionRole setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowTransitionRoleTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowTransition m_transition;
	private AGUuid m_role;
	private Boolean m_active;
}

