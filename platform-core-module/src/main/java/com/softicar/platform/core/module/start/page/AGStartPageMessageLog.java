package com.softicar.platform.core.module.start.page;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGStartPageMessageLog for
 * database table <i>Core.StartPageMessageLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGStartPageMessageLog extends AbstractDbRecord<AGStartPageMessageLog, Tuple2<AGStartPageMessage, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGStartPageMessageLog, AGStartPageMessageLog, Tuple2<AGStartPageMessage, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "StartPageMessageLog", AGStartPageMessageLog::new, AGStartPageMessageLog.class);
	static {
		BUILDER.setTitle(CoreI18n.START_PAGE_MESSAGE_LOG);
		BUILDER.setPluralTitle(CoreI18n.START_PAGE_MESSAGE_LOGS);
	}

	public static final IDbForeignField<AGStartPageMessageLog, AGStartPageMessage> START_PAGE_MESSAGE = BUILDER.addForeignField("startPageMessage", o->o.m_startPageMessage, (o,v)->o.m_startPageMessage=v, AGStartPageMessage.ID).setTitle(CoreI18n.START_PAGE_MESSAGE).setCascade(true, true).setForeignKeyName("StartPageMessageLog_ibfk_1");
	public static final IDbForeignField<AGStartPageMessageLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("StartPageMessageLog_ibfk_2");
	public static final IDbBooleanField<AGStartPageMessageLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbStringField<AGStartPageMessageLog> MESSAGE = BUILDER.addStringField("message", o->o.m_message, (o,v)->o.m_message=v).setTitle(CoreI18n.MESSAGE).setNullable().setDefault(null).setLengthBits(8);
	public static final IDbTableKey<AGStartPageMessageLog, Tuple2<AGStartPageMessage, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(START_PAGE_MESSAGE, TRANSACTION));
	public static final DbRecordTable<AGStartPageMessageLog, Tuple2<AGStartPageMessage, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGStartPageMessageLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getStartPageMessageID() {

		return getValueId(START_PAGE_MESSAGE);
	}

	public final AGStartPageMessage getStartPageMessage() {

		return getValue(START_PAGE_MESSAGE);
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

	public final AGStartPageMessageLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getMessage() {

		return getValue(MESSAGE);
	}

	public final AGStartPageMessageLog setMessage(String value) {

		return setValue(MESSAGE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGStartPageMessageLog, Tuple2<AGStartPageMessage, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGStartPageMessage m_startPageMessage;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private String m_message;
}

