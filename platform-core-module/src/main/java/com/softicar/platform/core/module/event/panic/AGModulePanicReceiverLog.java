package com.softicar.platform.core.module.event.panic;

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
 * This is the automatically generated class AGModulePanicReceiverLog for
 * database table <i>Core.ModulePanicReceiverLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGModulePanicReceiverLog extends AbstractDbRecord<AGModulePanicReceiverLog, Tuple2<AGModulePanicReceiver, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGModulePanicReceiverLog, AGModulePanicReceiverLog, Tuple2<AGModulePanicReceiver, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "ModulePanicReceiverLog", AGModulePanicReceiverLog::new, AGModulePanicReceiverLog.class);
	static {
		BUILDER.setTitle(CoreI18n.MODULE_PANIC_RECEIVER_LOG);
		BUILDER.setPluralTitle(CoreI18n.MODULE_PANIC_RECEIVER_LOGS);
	}

	public static final IDbForeignField<AGModulePanicReceiverLog, AGModulePanicReceiver> MODULE_PANIC_RECEIVER = BUILDER.addForeignField("modulePanicReceiver", o->o.m_modulePanicReceiver, (o,v)->o.m_modulePanicReceiver=v, AGModulePanicReceiver.ID).setTitle(CoreI18n.MODULE_PANIC_RECEIVER).setCascade(true, true).setForeignKeyName("ModulePanicReceiverLog_ibfk_1");
	public static final IDbForeignField<AGModulePanicReceiverLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("ModulePanicReceiverLog_ibfk_2");
	public static final IDbBooleanField<AGModulePanicReceiverLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbTableKey<AGModulePanicReceiverLog, Tuple2<AGModulePanicReceiver, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(MODULE_PANIC_RECEIVER, TRANSACTION));
	public static final IDbKey<AGModulePanicReceiverLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGModulePanicReceiverLog, Tuple2<AGModulePanicReceiver, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGModulePanicReceiverLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getModulePanicReceiverID() {

		return getValueId(MODULE_PANIC_RECEIVER);
	}

	public final AGModulePanicReceiver getModulePanicReceiver() {

		return getValue(MODULE_PANIC_RECEIVER);
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

	public final AGModulePanicReceiverLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGModulePanicReceiverLog, Tuple2<AGModulePanicReceiver, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGModulePanicReceiver m_modulePanicReceiver;
	private AGTransaction m_transaction;
	private Boolean m_active;
}

