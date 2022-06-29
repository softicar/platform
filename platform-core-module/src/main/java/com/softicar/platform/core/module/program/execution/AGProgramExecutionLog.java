package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGProgramExecutionLog for
 * database table <i>Core.ProgramExecutionLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGProgramExecutionLog extends AbstractDbRecord<AGProgramExecutionLog, Tuple2<AGProgramExecution, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGProgramExecutionLog, AGProgramExecutionLog, Tuple2<AGProgramExecution, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "ProgramExecutionLog", AGProgramExecutionLog::new, AGProgramExecutionLog.class);
	static {
		BUILDER.setTitle(CoreI18n.PROGRAM_EXECUTION_LOG);
		BUILDER.setPluralTitle(CoreI18n.PROGRAM_EXECUTION_LOGS);
	}

	public static final IDbForeignField<AGProgramExecutionLog, AGProgramExecution> PROGRAM_EXECUTION = BUILDER.addForeignField("programExecution", o->o.m_programExecution, (o,v)->o.m_programExecution=v, AGProgramExecution.ID).setTitle(CoreI18n.PROGRAM_EXECUTION);
	public static final IDbForeignField<AGProgramExecutionLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION);
	public static final IDbDayTimeField<AGProgramExecutionLog> STARTED_AT = BUILDER.addDayTimeField("startedAt", o->o.m_startedAt, (o,v)->o.m_startedAt=v).setTitle(CoreI18n.STARTED_AT).setNullable().setDefault(null);
	public static final IDbDayTimeField<AGProgramExecutionLog> TERMINATED_AT = BUILDER.addDayTimeField("terminatedAt", o->o.m_terminatedAt, (o,v)->o.m_terminatedAt=v).setTitle(CoreI18n.TERMINATED_AT).setNullable().setDefault(null);
	public static final IDbBooleanField<AGProgramExecutionLog> FAILED = BUILDER.addBooleanField("failed", o->o.m_failed, (o,v)->o.m_failed=v).setTitle(CoreI18n.FAILED).setNullable().setDefault(null);
	public static final IDbStringField<AGProgramExecutionLog> OUTPUT = BUILDER.addStringField("output", o->o.m_output, (o,v)->o.m_output=v).setTitle(CoreI18n.OUTPUT).setNullable().setDefault(null).setLengthBits(32);
	public static final IDbBooleanField<AGProgramExecutionLog> MAXIMUM_RUNTIME_EXCEEDED = BUILDER.addBooleanField("maximumRuntimeExceeded", o->o.m_maximumRuntimeExceeded, (o,v)->o.m_maximumRuntimeExceeded=v).setTitle(CoreI18n.MAXIMUM_RUNTIME_EXCEEDED).setNullable().setDefault(null);
	public static final IDbForeignField<AGProgramExecutionLog, AGUser> QUEUED_BY = BUILDER.addForeignField("queuedBy", o->o.m_queuedBy, (o,v)->o.m_queuedBy=v, AGUser.ID).setTitle(CoreI18n.QUEUED_BY).setNullable().setDefault(null);
	public static final IDbTableKey<AGProgramExecutionLog, Tuple2<AGProgramExecution, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(PROGRAM_EXECUTION, TRANSACTION));
	public static final DbRecordTable<AGProgramExecutionLog, Tuple2<AGProgramExecution, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGProgramExecutionLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getProgramExecutionID() {

		return getValueId(PROGRAM_EXECUTION);
	}

	public final AGProgramExecution getProgramExecution() {

		return getValue(PROGRAM_EXECUTION);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final DayTime getStartedAt() {

		return getValue(STARTED_AT);
	}

	public final AGProgramExecutionLog setStartedAt(DayTime value) {

		return setValue(STARTED_AT, value);
	}

	public final DayTime getTerminatedAt() {

		return getValue(TERMINATED_AT);
	}

	public final AGProgramExecutionLog setTerminatedAt(DayTime value) {

		return setValue(TERMINATED_AT, value);
	}

	public final Boolean isFailed() {

		return getValue(FAILED);
	}

	public final AGProgramExecutionLog setFailed(Boolean value) {

		return setValue(FAILED, value);
	}

	public final String getOutput() {

		return getValue(OUTPUT);
	}

	public final AGProgramExecutionLog setOutput(String value) {

		return setValue(OUTPUT, value);
	}

	public final Boolean isMaximumRuntimeExceeded() {

		return getValue(MAXIMUM_RUNTIME_EXCEEDED);
	}

	public final AGProgramExecutionLog setMaximumRuntimeExceeded(Boolean value) {

		return setValue(MAXIMUM_RUNTIME_EXCEEDED, value);
	}

	public final Integer getQueuedByID() {

		return getValueId(QUEUED_BY);
	}

	public final AGUser getQueuedBy() {

		return getValue(QUEUED_BY);
	}

	public final AGProgramExecutionLog setQueuedBy(AGUser value) {

		return setValue(QUEUED_BY, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGProgramExecutionLog, Tuple2<AGProgramExecution, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGProgramExecution m_programExecution;
	private AGTransaction m_transaction;
	private DayTime m_startedAt;
	private DayTime m_terminatedAt;
	private Boolean m_failed;
	private String m_output;
	private Boolean m_maximumRuntimeExceeded;
	private AGUser m_queuedBy;
}

