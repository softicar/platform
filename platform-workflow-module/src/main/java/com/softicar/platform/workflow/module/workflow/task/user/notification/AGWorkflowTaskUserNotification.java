package com.softicar.platform.workflow.module.workflow.task.user.notification;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

/**
 * This is the automatically generated class AGWorkflowTaskUserNotification for
 * database table <i>Workflow.WorkflowTaskUserNotification</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowTaskUserNotification extends AbstractDbRecord<AGWorkflowTaskUserNotification, AGUser> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowTaskUserNotification, AGWorkflowTaskUserNotification, AGUser> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowTaskUserNotification", AGWorkflowTaskUserNotification::new, AGWorkflowTaskUserNotification.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_TASK_USER_NOTIFICATION);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_TASK_USER_NOTIFICATIONS);
	}

	public static final IDbForeignField<AGWorkflowTaskUserNotification, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(WorkflowI18n.USER);
	public static final IDbBooleanField<AGWorkflowTaskUserNotification> NOTIFY = BUILDER.addBooleanField("notify", o->o.m_notify, (o,v)->o.m_notify=v).setTitle(WorkflowI18n.NOTIFY).setDefault(true);
	public static final IDbTableKey<AGWorkflowTaskUserNotification, AGUser> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(USER));
	public static final DbRecordTable<AGWorkflowTaskUserNotification, AGUser> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowTaskUserNotification() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final Boolean isNotify() {

		return getValue(NOTIFY);
	}

	public final AGWorkflowTaskUserNotification setNotify(Boolean value) {

		return setValue(NOTIFY, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowTaskUserNotification, AGUser> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGUser m_user;
	private Boolean m_notify;
}

