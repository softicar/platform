package com.softicar.platform.workflow.module.demo.approver;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.demo.AGWorkflowDemoObject;

/**
 * This is the automatically generated class AGWorkflowDemoObjectApprover for
 * database table <i>Workflow.WorkflowDemoObjectApprover</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowDemoObjectApproverGenerated extends AbstractDbObject<AGWorkflowDemoObjectApprover> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowDemoObjectApprover, AGWorkflowDemoObjectApproverGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowDemoObjectApprover", AGWorkflowDemoObjectApprover::new, AGWorkflowDemoObjectApprover.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_DEMO_OBJECT_APPROVER);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_DEMO_OBJECT_APPROVERS);
	}

	public static final IDbIdField<AGWorkflowDemoObjectApprover> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowDemoObjectApprover, AGWorkflowDemoObject> OBJECT = BUILDER.addForeignField("object", o->o.m_object, (o,v)->o.m_object=v, AGWorkflowDemoObject.ID).setTitle(WorkflowI18n.OBJECT).setForeignKeyName("WorkflowDemoObjectApprover_ibfk_1");
	public static final IDbIntegerField<AGWorkflowDemoObjectApprover> APPROVAL_TIER = BUILDER.addIntegerField("approvalTier", o->o.m_approvalTier, (o,v)->o.m_approvalTier=v).setTitle(WorkflowI18n.APPROVAL_TIER).setDefault(0);
	public static final IDbForeignField<AGWorkflowDemoObjectApprover, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(WorkflowI18n.USER).setForeignKeyName("WorkflowDemoObjectApprover_ibfk_2");
	public static final IDbBooleanField<AGWorkflowDemoObjectApprover> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setDefault(true);
	public static final IDbBooleanField<AGWorkflowDemoObjectApprover> APPROVED = BUILDER.addBooleanField("approved", o->o.m_approved, (o,v)->o.m_approved=v).setTitle(WorkflowI18n.APPROVED).setNullable().setDefault(null);
	public static final IDbKey<AGWorkflowDemoObjectApprover> IK_OBJECT = BUILDER.addIndexKey("object", OBJECT);
	public static final IDbKey<AGWorkflowDemoObjectApprover> IK_USER = BUILDER.addIndexKey("user", USER);
	public static final AGWorkflowDemoObjectApproverTable TABLE = new AGWorkflowDemoObjectApproverTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowDemoObjectApprover> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowDemoObjectApprover get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getObjectID() {

		return getValueId(OBJECT);
	}

	public final AGWorkflowDemoObject getObject() {

		return getValue(OBJECT);
	}

	public final AGWorkflowDemoObjectApprover setObject(AGWorkflowDemoObject value) {

		return setValue(OBJECT, value);
	}

	public final Integer getApprovalTier() {

		return getValue(APPROVAL_TIER);
	}

	public final AGWorkflowDemoObjectApprover setApprovalTier(Integer value) {

		return setValue(APPROVAL_TIER, value);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGWorkflowDemoObjectApprover setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowDemoObjectApprover setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Boolean isApproved() {

		return getValue(APPROVED);
	}

	public final AGWorkflowDemoObjectApprover setApproved(Boolean value) {

		return setValue(APPROVED, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowDemoObjectApproverTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowDemoObject m_object;
	private Integer m_approvalTier;
	private AGUser m_user;
	private Boolean m_active;
	private Boolean m_approved;
}

