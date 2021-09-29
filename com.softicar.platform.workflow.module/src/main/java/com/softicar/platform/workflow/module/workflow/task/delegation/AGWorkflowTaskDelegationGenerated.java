package com.softicar.platform.workflow.module.workflow.task.delegation;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;

/**
 * This is the automatically generated class AGWorkflowTaskDelegation for
 * database table <i>Workflow.WorkflowTaskDelegation</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowTaskDelegationGenerated extends AbstractDbRecord<AGWorkflowTaskDelegation, AGWorkflowTask> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowTaskDelegation, AGWorkflowTaskDelegationGenerated, AGWorkflowTask> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowTaskDelegation", AGWorkflowTaskDelegation::new, AGWorkflowTaskDelegation.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_TASK_DELEGATION);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_TASK_DELEGATIONS);
	}

	public static final IDbForeignField<AGWorkflowTaskDelegation, AGWorkflowTask> WORKFLOW_TASK = BUILDER.addForeignField("workflowTask", o->o.m_workflowTask, (o,v)->o.m_workflowTask=v, AGWorkflowTask.ID).setTitle(WorkflowI18n.WORKFLOW_TASK);
	public static final IDbForeignField<AGWorkflowTaskDelegation, AGUser> TARGET_USER = BUILDER.addForeignField("targetUser", o->o.m_targetUser, (o,v)->o.m_targetUser=v, AGUser.ID).setTitle(WorkflowI18n.TARGET_USER);
	public static final IDbForeignField<AGWorkflowTaskDelegation, AGUser> DELEGATED_BY = BUILDER.addForeignField("delegatedBy", o->o.m_delegatedBy, (o,v)->o.m_delegatedBy=v, AGUser.ID).setTitle(WorkflowI18n.DELEGATED_BY);
	public static final IDbBooleanField<AGWorkflowTaskDelegation> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setDefault(true);
	public static final IDbTableKey<AGWorkflowTaskDelegation, AGWorkflowTask> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_TASK));
	public static final AGWorkflowTaskDelegationTable TABLE = new AGWorkflowTaskDelegationTable(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowTaskDelegationGenerated() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowTaskID() {

		return getValueId(WORKFLOW_TASK);
	}

	public final AGWorkflowTask getWorkflowTask() {

		return getValue(WORKFLOW_TASK);
	}

	public final Integer getTargetUserID() {

		return getValueId(TARGET_USER);
	}

	public final AGUser getTargetUser() {

		return getValue(TARGET_USER);
	}

	public final AGWorkflowTaskDelegation setTargetUser(AGUser value) {

		return setValue(TARGET_USER, value);
	}

	public final Integer getDelegatedByID() {

		return getValueId(DELEGATED_BY);
	}

	public final AGUser getDelegatedBy() {

		return getValue(DELEGATED_BY);
	}

	public final AGWorkflowTaskDelegation setDelegatedBy(AGUser value) {

		return setValue(DELEGATED_BY, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowTaskDelegation setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowTaskDelegationTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowTask m_workflowTask;
	private AGUser m_targetUser;
	private AGUser m_delegatedBy;
	private Boolean m_active;
}

