package com.softicar.platform.core.module.event.recipient;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGSystemEventEmailRecipientLog for
 * database table <i>Core.SystemEventEmailRecipientLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGSystemEventEmailRecipientLog extends AbstractDbRecord<AGSystemEventEmailRecipientLog, Tuple2<AGSystemEventEmailRecipient, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGSystemEventEmailRecipientLog, AGSystemEventEmailRecipientLog, Tuple2<AGSystemEventEmailRecipient, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "SystemEventEmailRecipientLog", AGSystemEventEmailRecipientLog::new, AGSystemEventEmailRecipientLog.class);
	static {
		BUILDER.setTitle(CoreI18n.SYSTEM_EVENT_EMAIL_RECIPIENT_LOG);
		BUILDER.setPluralTitle(CoreI18n.SYSTEM_EVENT_EMAIL_RECIPIENT_LOGS);
	}

	public static final IDbForeignField<AGSystemEventEmailRecipientLog, AGSystemEventEmailRecipient> RECIPIENT = BUILDER.addForeignField("recipient", o->o.m_recipient, (o,v)->o.m_recipient=v, AGSystemEventEmailRecipient.ID).setTitle(CoreI18n.RECIPIENT);
	public static final IDbForeignField<AGSystemEventEmailRecipientLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION);
	public static final IDbBooleanField<AGSystemEventEmailRecipientLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbTableKey<AGSystemEventEmailRecipientLog, Tuple2<AGSystemEventEmailRecipient, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(RECIPIENT, TRANSACTION));
	public static final DbRecordTable<AGSystemEventEmailRecipientLog, Tuple2<AGSystemEventEmailRecipient, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGSystemEventEmailRecipientLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getRecipientID() {

		return getValueId(RECIPIENT);
	}

	public final AGSystemEventEmailRecipient getRecipient() {

		return getValue(RECIPIENT);
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

	public final AGSystemEventEmailRecipientLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGSystemEventEmailRecipientLog, Tuple2<AGSystemEventEmailRecipient, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGSystemEventEmailRecipient m_recipient;
	private AGTransaction m_transaction;
	private Boolean m_active;
}

