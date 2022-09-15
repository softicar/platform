package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGScheduledProgramExecutionLog for
 * database table <i>Core.ScheduledProgramExecutionLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGScheduledProgramExecutionLog extends AbstractDbRecord<AGScheduledProgramExecutionLog, Tuple2<AGScheduledProgramExecution, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGScheduledProgramExecutionLog, AGScheduledProgramExecutionLog, Tuple2<AGScheduledProgramExecution, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "ScheduledProgramExecutionLog", AGScheduledProgramExecutionLog::new, AGScheduledProgramExecutionLog.class);
	static {
		BUILDER.setTitle(CoreI18n.SCHEDULED_PROGRAM_EXECUTION_LOG);
		BUILDER.setPluralTitle(CoreI18n.SCHEDULED_PROGRAM_EXECUTION_LOGS);
	}

	public static final IDbForeignField<AGScheduledProgramExecutionLog, AGScheduledProgramExecution> SCHEDULED_PROGRAM_EXECUTION = BUILDER.addForeignField("scheduledProgramExecution", o->o.m_scheduledProgramExecution, (o,v)->o.m_scheduledProgramExecution=v, AGScheduledProgramExecution.ID).setTitle(CoreI18n.SCHEDULED_PROGRAM_EXECUTION).setCascade(true, true).setForeignKeyName("ScheduledProgramExecutionLog_ibfk_1");
	public static final IDbForeignField<AGScheduledProgramExecutionLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("ScheduledProgramExecutionLog_ibfk_2");
	public static final IDbBooleanField<AGScheduledProgramExecutionLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbStringField<AGScheduledProgramExecutionLog> CRON_EXPRESSION = BUILDER.addStringField("cronExpression", o->o.m_cronExpression, (o,v)->o.m_cronExpression=v).setTitle(CoreI18n.CRON_EXPRESSION).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbIntegerField<AGScheduledProgramExecutionLog> MAXIMUM_RUNTIME = BUILDER.addIntegerField("maximumRuntime", o->o.m_maximumRuntime, (o,v)->o.m_maximumRuntime=v).setTitle(CoreI18n.MAXIMUM_RUNTIME).setNullable().setDefault(null);
	public static final IDbBooleanField<AGScheduledProgramExecutionLog> AUTOMATIC_ABORT = BUILDER.addBooleanField("automaticAbort", o->o.m_automaticAbort, (o,v)->o.m_automaticAbort=v).setTitle(CoreI18n.AUTOMATIC_ABORT).setNullable().setDefault(null);
	public static final IDbTableKey<AGScheduledProgramExecutionLog, Tuple2<AGScheduledProgramExecution, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(SCHEDULED_PROGRAM_EXECUTION, TRANSACTION));
	public static final IDbKey<AGScheduledProgramExecutionLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGScheduledProgramExecutionLog, Tuple2<AGScheduledProgramExecution, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGScheduledProgramExecutionLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getScheduledProgramExecutionID() {

		return getValueId(SCHEDULED_PROGRAM_EXECUTION);
	}

	public final AGScheduledProgramExecution getScheduledProgramExecution() {

		return getValue(SCHEDULED_PROGRAM_EXECUTION);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGScheduledProgramExecutionLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getCronExpression() {

		return getValue(CRON_EXPRESSION);
	}

	public final AGScheduledProgramExecutionLog setCronExpression(String value) {

		return setValue(CRON_EXPRESSION, value);
	}

	public final Integer getMaximumRuntime() {

		return getValue(MAXIMUM_RUNTIME);
	}

	public final AGScheduledProgramExecutionLog setMaximumRuntime(Integer value) {

		return setValue(MAXIMUM_RUNTIME, value);
	}

	public final Boolean isAutomaticAbort() {

		return getValue(AUTOMATIC_ABORT);
	}

	public final AGScheduledProgramExecutionLog setAutomaticAbort(Boolean value) {

		return setValue(AUTOMATIC_ABORT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGScheduledProgramExecutionLog, Tuple2<AGScheduledProgramExecution, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGScheduledProgramExecution m_scheduledProgramExecution;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private String m_cronExpression;
	private Integer m_maximumRuntime;
	private Boolean m_automaticAbort;
}

