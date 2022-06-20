package com.softicar.platform.core.module.module.instance;

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
 * This is the automatically generated class AGModuleInstanceLog for
 * database table <i>Core.ModuleInstanceLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGModuleInstanceLog extends AbstractDbRecord<AGModuleInstanceLog, Tuple2<AGModuleInstance, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGModuleInstanceLog, AGModuleInstanceLog, Tuple2<AGModuleInstance, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "ModuleInstanceLog", AGModuleInstanceLog::new, AGModuleInstanceLog.class);
	static {
		BUILDER.setTitle(CoreI18n.MODULE_INSTANCE_LOG);
		BUILDER.setPluralTitle(CoreI18n.MODULE_INSTANCE_LOGS);
	}

	public static final IDbForeignField<AGModuleInstanceLog, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.ID).setTitle(CoreI18n.MODULE_INSTANCE).setCascade(false, true);
	public static final IDbForeignField<AGModuleInstanceLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(false, true);
	public static final IDbBooleanField<AGModuleInstanceLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbTableKey<AGModuleInstanceLog, Tuple2<AGModuleInstance, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(MODULE_INSTANCE, TRANSACTION));
	public static final IDbKey<AGModuleInstanceLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGModuleInstanceLog, Tuple2<AGModuleInstance, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGModuleInstanceLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getModuleInstanceID() {

		return getValueId(MODULE_INSTANCE);
	}

	public final AGModuleInstance getModuleInstance() {

		return getValue(MODULE_INSTANCE);
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

	public final AGModuleInstanceLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGModuleInstanceLog, Tuple2<AGModuleInstance, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGModuleInstance m_moduleInstance;
	private AGTransaction m_transaction;
	private Boolean m_active;
}

