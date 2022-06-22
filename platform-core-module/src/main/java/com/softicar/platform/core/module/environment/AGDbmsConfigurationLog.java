package com.softicar.platform.core.module.environment;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.date.weekday.AGWeekday;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbTimeField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGDbmsConfigurationLog for
 * database table <i>Core.DbmsConfigurationLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDbmsConfigurationLog extends AbstractDbRecord<AGDbmsConfigurationLog, Tuple2<AGDbmsConfiguration, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGDbmsConfigurationLog, AGDbmsConfigurationLog, Tuple2<AGDbmsConfiguration, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "DbmsConfigurationLog", AGDbmsConfigurationLog::new, AGDbmsConfigurationLog.class);
	static {
		BUILDER.setTitle(CoreI18n.DBMS_CONFIGURATION_LOG);
		BUILDER.setPluralTitle(CoreI18n.DBMS_CONFIGURATION_LOGS);
	}

	public static final IDbForeignRowField<AGDbmsConfigurationLog, AGDbmsConfiguration, AGCoreModuleInstance> DBMS_CONFIGURATION = BUILDER.addForeignRowField("dbmsConfiguration", o->o.m_dbmsConfiguration, (o,v)->o.m_dbmsConfiguration=v, AGDbmsConfiguration.CORE_MODULE_INSTANCE).setTitle(CoreI18n.DBMS_CONFIGURATION);
	public static final IDbForeignField<AGDbmsConfigurationLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION);
	public static final IDbForeignField<AGDbmsConfigurationLog, AGWeekday> DBMS_DOWN_TIME_WEEKDAY = BUILDER.addForeignField("dbmsDownTimeWeekday", o->o.m_dbmsDownTimeWeekday, (o,v)->o.m_dbmsDownTimeWeekday=v, AGWeekday.ID).setTitle(CoreI18n.DBMS_DOWN_TIME_WEEKDAY).setNullable().setDefault(null);
	public static final IDbTimeField<AGDbmsConfigurationLog> DBMS_DOWN_TIME_BEGIN = BUILDER.addTimeField("dbmsDownTimeBegin", o->o.m_dbmsDownTimeBegin, (o,v)->o.m_dbmsDownTimeBegin=v).setTitle(CoreI18n.DBMS_DOWN_TIME_BEGIN).setNullable().setDefault(null);
	public static final IDbTimeField<AGDbmsConfigurationLog> DBMS_DOWN_TIME_END = BUILDER.addTimeField("dbmsDownTimeEnd", o->o.m_dbmsDownTimeEnd, (o,v)->o.m_dbmsDownTimeEnd=v).setTitle(CoreI18n.DBMS_DOWN_TIME_END).setNullable().setDefault(null);
	public static final IDbTableKey<AGDbmsConfigurationLog, Tuple2<AGDbmsConfiguration, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(DBMS_CONFIGURATION, TRANSACTION));
	public static final IDbKey<AGDbmsConfigurationLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGDbmsConfigurationLog> IK_DBMS_DOWN_TIME_WEEKDAY = BUILDER.addIndexKey("dbmsDownTimeWeekday", DBMS_DOWN_TIME_WEEKDAY);
	public static final DbRecordTable<AGDbmsConfigurationLog, Tuple2<AGDbmsConfiguration, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGDbmsConfigurationLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGDbmsConfiguration getDbmsConfiguration() {

		return getValue(DBMS_CONFIGURATION);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Integer getDbmsDownTimeWeekdayID() {

		return getValueId(DBMS_DOWN_TIME_WEEKDAY);
	}

	public final AGWeekday getDbmsDownTimeWeekday() {

		return getValue(DBMS_DOWN_TIME_WEEKDAY);
	}

	public final AGDbmsConfigurationLog setDbmsDownTimeWeekday(AGWeekday value) {

		return setValue(DBMS_DOWN_TIME_WEEKDAY, value);
	}

	public final Time getDbmsDownTimeBegin() {

		return getValue(DBMS_DOWN_TIME_BEGIN);
	}

	public final AGDbmsConfigurationLog setDbmsDownTimeBegin(Time value) {

		return setValue(DBMS_DOWN_TIME_BEGIN, value);
	}

	public final Time getDbmsDownTimeEnd() {

		return getValue(DBMS_DOWN_TIME_END);
	}

	public final AGDbmsConfigurationLog setDbmsDownTimeEnd(Time value) {

		return setValue(DBMS_DOWN_TIME_END, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGDbmsConfigurationLog, Tuple2<AGDbmsConfiguration, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGDbmsConfiguration m_dbmsConfiguration;
	private AGTransaction m_transaction;
	private AGWeekday m_dbmsDownTimeWeekday;
	private Time m_dbmsDownTimeBegin;
	private Time m_dbmsDownTimeEnd;
}

