package com.softicar.platform.workflow.module.workflow.user.configuration.specific;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;

/**
 * This is the automatically generated class AGWorkflowSpecificUserConfiguration for
 * database table <i>Workflow.WorkflowSpecificUserConfiguration</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowSpecificUserConfigurationGenerated extends AbstractDbObject<AGWorkflowSpecificUserConfiguration> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowSpecificUserConfiguration, AGWorkflowSpecificUserConfigurationGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowSpecificUserConfiguration", AGWorkflowSpecificUserConfiguration::new, AGWorkflowSpecificUserConfiguration.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_SPECIFIC_USER_CONFIGURATION);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_SPECIFIC_USER_CONFIGURATIONS);
	}

	public static final IDbIdField<AGWorkflowSpecificUserConfiguration> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowSpecificUserConfiguration, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(WorkflowI18n.USER).setCascade(true, true).setForeignKeyName("WorkflowSpecificUserConfiguration_ibfk_1");
	public static final IDbForeignField<AGWorkflowSpecificUserConfiguration, AGWorkflow> WORKFLOW = BUILDER.addForeignField("workflow", o->o.m_workflow, (o,v)->o.m_workflow=v, AGWorkflow.ID).setTitle(WorkflowI18n.WORKFLOW).setCascade(true, true).setForeignKeyName("WorkflowSpecificUserConfiguration_ibfk_2");
	public static final IDbBooleanField<AGWorkflowSpecificUserConfiguration> SUBSCRIBED = BUILDER.addBooleanField("subscribed", o->o.m_subscribed, (o,v)->o.m_subscribed=v).setTitle(WorkflowI18n.SUBSCRIBED).setDefault(true);
	public static final IDbKey<AGWorkflowSpecificUserConfiguration> UK_USER_WORKFLOW = BUILDER.addUniqueKey("userWorkflow", USER, WORKFLOW);
	public static final IDbKey<AGWorkflowSpecificUserConfiguration> IK_WORKFLOW = BUILDER.addIndexKey("workflow", WORKFLOW);
	public static final AGWorkflowSpecificUserConfigurationTable TABLE = new AGWorkflowSpecificUserConfigurationTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowSpecificUserConfiguration> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowSpecificUserConfiguration get(Integer id) {

		return TABLE.get(id);
	}

	public static AGWorkflowSpecificUserConfiguration loadByUserAndWorkflow(AGUser user, AGWorkflow workflow) {

		return TABLE//
				.createSelect()
				.where(USER.isEqual(user))
				.where(WORKFLOW.isEqual(workflow))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGWorkflowSpecificUserConfiguration setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final Integer getWorkflowID() {

		return getValueId(WORKFLOW);
	}

	public final AGWorkflow getWorkflow() {

		return getValue(WORKFLOW);
	}

	public final AGWorkflowSpecificUserConfiguration setWorkflow(AGWorkflow value) {

		return setValue(WORKFLOW, value);
	}

	public final Boolean isSubscribed() {

		return getValue(SUBSCRIBED);
	}

	public final AGWorkflowSpecificUserConfiguration setSubscribed(Boolean value) {

		return setValue(SUBSCRIBED, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowSpecificUserConfigurationTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUser m_user;
	private AGWorkflow m_workflow;
	private Boolean m_subscribed;
}

