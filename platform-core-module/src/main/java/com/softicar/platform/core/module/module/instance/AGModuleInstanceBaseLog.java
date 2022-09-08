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
 * This is the automatically generated class AGModuleInstanceBaseLog for
 * database table <i>Core.ModuleInstanceBaseLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGModuleInstanceBaseLog extends AbstractDbRecord<AGModuleInstanceBaseLog, Tuple2<AGModuleInstanceBase, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGModuleInstanceBaseLog, AGModuleInstanceBaseLog, Tuple2<AGModuleInstanceBase, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "ModuleInstanceBaseLog", AGModuleInstanceBaseLog::new, AGModuleInstanceBaseLog.class);
	static {
		BUILDER.setTitle(CoreI18n.MODULE_INSTANCE_BASE_LOG);
		BUILDER.setPluralTitle(CoreI18n.MODULE_INSTANCE_BASE_LOGS);
	}

	public static final IDbForeignField<AGModuleInstanceBaseLog, AGModuleInstanceBase> MODULE_INSTANCE_BASE = BUILDER.addForeignField("moduleInstanceBase", o->o.m_moduleInstanceBase, (o,v)->o.m_moduleInstanceBase=v, AGModuleInstanceBase.ID).setTitle(CoreI18n.MODULE_INSTANCE_BASE).setCascade(false, true).setForeignKeyName("ModuleInstanceBaseLog_ibfk_1");
	public static final IDbForeignField<AGModuleInstanceBaseLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(false, true).setForeignKeyName("ModuleInstanceBaseLog_ibfk_2");
	public static final IDbBooleanField<AGModuleInstanceBaseLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbTableKey<AGModuleInstanceBaseLog, Tuple2<AGModuleInstanceBase, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(MODULE_INSTANCE_BASE, TRANSACTION));
	public static final IDbKey<AGModuleInstanceBaseLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGModuleInstanceBaseLog, Tuple2<AGModuleInstanceBase, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGModuleInstanceBaseLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getModuleInstanceBaseID() {

		return getValueId(MODULE_INSTANCE_BASE);
	}

	public final AGModuleInstanceBase getModuleInstanceBase() {

		return getValue(MODULE_INSTANCE_BASE);
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

	public final AGModuleInstanceBaseLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGModuleInstanceBaseLog, Tuple2<AGModuleInstanceBase, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGModuleInstanceBase m_moduleInstanceBase;
	private AGTransaction m_transaction;
	private Boolean m_active;
}

