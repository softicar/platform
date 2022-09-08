package com.softicar.platform.core.module.program;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGProgramLog for
 * database table <i>Core.ProgramLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGProgramLog extends AbstractDbRecord<AGProgramLog, Tuple2<AGProgram, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGProgramLog, AGProgramLog, Tuple2<AGProgram, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "ProgramLog", AGProgramLog::new, AGProgramLog.class);
	static {
		BUILDER.setTitle(CoreI18n.PROGRAM_LOG);
		BUILDER.setPluralTitle(CoreI18n.PROGRAM_LOGS);
	}

	public static final IDbForeignField<AGProgramLog, AGProgram> PROGRAM = BUILDER.addForeignField("program", o->o.m_program, (o,v)->o.m_program=v, AGProgram.ID).setTitle(CoreI18n.PROGRAM).setForeignKeyName("ProgramLog_ibfk_1");
	public static final IDbForeignField<AGProgramLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setForeignKeyName("ProgramLog_ibfk_2");
	public static final IDbIntegerField<AGProgramLog> EXECUTION_RETENTION_DAYS = BUILDER.addIntegerField("executionRetentionDays", o->o.m_executionRetentionDays, (o,v)->o.m_executionRetentionDays=v).setTitle(CoreI18n.EXECUTION_RETENTION_DAYS).setNullable().setDefault(null);
	public static final IDbTableKey<AGProgramLog, Tuple2<AGProgram, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(PROGRAM, TRANSACTION));
	public static final IDbKey<AGProgramLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGProgramLog, Tuple2<AGProgram, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGProgramLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getProgramID() {

		return getValueId(PROGRAM);
	}

	public final AGProgram getProgram() {

		return getValue(PROGRAM);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Integer getExecutionRetentionDays() {

		return getValue(EXECUTION_RETENTION_DAYS);
	}

	public final AGProgramLog setExecutionRetentionDays(Integer value) {

		return setValue(EXECUTION_RETENTION_DAYS, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGProgramLog, Tuple2<AGProgram, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGProgram m_program;
	private AGTransaction m_transaction;
	private Integer m_executionRetentionDays;
}

