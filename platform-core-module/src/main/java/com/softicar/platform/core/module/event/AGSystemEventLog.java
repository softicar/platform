package com.softicar.platform.core.module.event;

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
 * This is the automatically generated class AGSystemEventLog for
 * database table <i>Core.SystemEventLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGSystemEventLog extends AbstractDbRecord<AGSystemEventLog, Tuple2<AGSystemEvent, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGSystemEventLog, AGSystemEventLog, Tuple2<AGSystemEvent, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "SystemEventLog", AGSystemEventLog::new, AGSystemEventLog.class);
	static {
		BUILDER.setTitle(CoreI18n.SYSTEM_EVENT_LOG);
		BUILDER.setPluralTitle(CoreI18n.SYSTEM_EVENT_LOGS);
	}

	public static final IDbForeignField<AGSystemEventLog, AGSystemEvent> SYSTEM_EVENT = BUILDER.addForeignField("systemEvent", o->o.m_systemEvent, (o,v)->o.m_systemEvent=v, AGSystemEvent.ID).setTitle(CoreI18n.SYSTEM_EVENT);
	public static final IDbForeignField<AGSystemEventLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION);
	public static final IDbBooleanField<AGSystemEventLog> NEEDS_CONFIRMATION = BUILDER.addBooleanField("needsConfirmation", o->o.m_needsConfirmation, (o,v)->o.m_needsConfirmation=v).setTitle(CoreI18n.NEEDS_CONFIRMATION).setNullable().setDefault(null);
	public static final IDbTableKey<AGSystemEventLog, Tuple2<AGSystemEvent, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(SYSTEM_EVENT, TRANSACTION));
	public static final DbRecordTable<AGSystemEventLog, Tuple2<AGSystemEvent, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGSystemEventLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getSystemEventID() {

		return getValueId(SYSTEM_EVENT);
	}

	public final AGSystemEvent getSystemEvent() {

		return getValue(SYSTEM_EVENT);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Boolean isNeedsConfirmation() {

		return getValue(NEEDS_CONFIRMATION);
	}

	public final AGSystemEventLog setNeedsConfirmation(Boolean value) {

		return setValue(NEEDS_CONFIRMATION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGSystemEventLog, Tuple2<AGSystemEvent, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGSystemEvent m_systemEvent;
	private AGTransaction m_transaction;
	private Boolean m_needsConfirmation;
}

