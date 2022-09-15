package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGBufferedEmailLog for
 * database table <i>Core.BufferedEmailLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGBufferedEmailLog extends AbstractDbRecord<AGBufferedEmailLog, Tuple2<AGBufferedEmail, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGBufferedEmailLog, AGBufferedEmailLog, Tuple2<AGBufferedEmail, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "BufferedEmailLog", AGBufferedEmailLog::new, AGBufferedEmailLog.class);
	static {
		BUILDER.setTitle(CoreI18n.BUFFERED_EMAIL_LOG);
		BUILDER.setPluralTitle(CoreI18n.BUFFERED_EMAIL_LOGS);
	}

	public static final IDbForeignField<AGBufferedEmailLog, AGBufferedEmail> BUFFERED_EMAIL = BUILDER.addForeignField("bufferedEmail", o->o.m_bufferedEmail, (o,v)->o.m_bufferedEmail=v, AGBufferedEmail.ID).setTitle(CoreI18n.BUFFERED_EMAIL).setCascade(true, true).setForeignKeyName("BufferedEmailLog_ibfk_1");
	public static final IDbForeignField<AGBufferedEmailLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("BufferedEmailLog_ibfk_2");
	public static final IDbBooleanField<AGBufferedEmailLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbTableKey<AGBufferedEmailLog, Tuple2<AGBufferedEmail, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(BUFFERED_EMAIL, TRANSACTION));
	public static final IDbKey<AGBufferedEmailLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGBufferedEmailLog, Tuple2<AGBufferedEmail, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGBufferedEmailLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getBufferedEmailID() {

		return getValueId(BUFFERED_EMAIL);
	}

	public final AGBufferedEmail getBufferedEmail() {

		return getValue(BUFFERED_EMAIL);
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

	public final AGBufferedEmailLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGBufferedEmailLog, Tuple2<AGBufferedEmail, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGBufferedEmail m_bufferedEmail;
	private AGTransaction m_transaction;
	private Boolean m_active;
}

