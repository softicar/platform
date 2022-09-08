package com.softicar.platform.workflow.module.workflow.image;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

/**
 * This is the automatically generated class AGWorkflowIconLog for
 * database table <i>Workflow.WorkflowIconLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowIconLog extends AbstractDbRecord<AGWorkflowIconLog, Tuple2<AGWorkflowIcon, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowIconLog, AGWorkflowIconLog, Tuple2<AGWorkflowIcon, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowIconLog", AGWorkflowIconLog::new, AGWorkflowIconLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_ICON_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_ICON_LOGS);
	}

	public static final IDbForeignField<AGWorkflowIconLog, AGWorkflowIcon> WORKFLOW_ICON = BUILDER.addForeignField("workflowIcon", o->o.m_workflowIcon, (o,v)->o.m_workflowIcon=v, AGWorkflowIcon.ID).setTitle(WorkflowI18n.WORKFLOW_ICON).setForeignKeyName("WorkflowIconLog_ibfk_1");
	public static final IDbForeignField<AGWorkflowIconLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setForeignKeyName("WorkflowIconLog_ibfk_2");
	public static final IDbStringField<AGWorkflowIconLog> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(WorkflowI18n.NAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbForeignField<AGWorkflowIconLog, AGStoredFile> ICON = BUILDER.addForeignField("icon", o->o.m_icon, (o,v)->o.m_icon=v, AGStoredFile.ID).setTitle(WorkflowI18n.ICON).setNullable().setDefault(null).setForeignKeyName("WorkflowIconLog_ibfk_3");
	public static final IDbTableKey<AGWorkflowIconLog, Tuple2<AGWorkflowIcon, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_ICON, TRANSACTION));
	public static final IDbKey<AGWorkflowIconLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGWorkflowIconLog> IK_ICON = BUILDER.addIndexKey("icon", ICON);
	public static final DbRecordTable<AGWorkflowIconLog, Tuple2<AGWorkflowIcon, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowIconLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowIconID() {

		return getValueId(WORKFLOW_ICON);
	}

	public final AGWorkflowIcon getWorkflowIcon() {

		return getValue(WORKFLOW_ICON);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGWorkflowIconLog setName(String value) {

		return setValue(NAME, value);
	}

	public final Integer getIconID() {

		return getValueId(ICON);
	}

	public final AGStoredFile getIcon() {

		return getValue(ICON);
	}

	public final AGWorkflowIconLog setIcon(AGStoredFile value) {

		return setValue(ICON, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowIconLog, Tuple2<AGWorkflowIcon, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowIcon m_workflowIcon;
	private AGTransaction m_transaction;
	private String m_name;
	private AGStoredFile m_icon;
}

