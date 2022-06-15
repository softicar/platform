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
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.field.IDbTimeField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGLiveSystemConfigurationLog for
 * database table <i>Core.LiveSystemConfigurationLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGLiveSystemConfigurationLog extends AbstractDbRecord<AGLiveSystemConfigurationLog, Tuple2<AGLiveSystemConfiguration, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGLiveSystemConfigurationLog, AGLiveSystemConfigurationLog, Tuple2<AGLiveSystemConfiguration, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "LiveSystemConfigurationLog", AGLiveSystemConfigurationLog::new, AGLiveSystemConfigurationLog.class);
	static {
		BUILDER.setTitle(CoreI18n.LIVE_SYSTEM_CONFIGURATION_LOG);
		BUILDER.setPluralTitle(CoreI18n.LIVE_SYSTEM_CONFIGURATION_LOGS);
	}

	public static final IDbForeignRowField<AGLiveSystemConfigurationLog, AGLiveSystemConfiguration, AGCoreModuleInstance> LIVE_SYSTEM_CONFIGURATION = BUILDER.addForeignRowField("liveSystemConfiguration", o->o.m_liveSystemConfiguration, (o,v)->o.m_liveSystemConfiguration=v, AGLiveSystemConfiguration.CORE_MODULE_INSTANCE).setTitle(CoreI18n.LIVE_SYSTEM_CONFIGURATION);
	public static final IDbForeignField<AGLiveSystemConfigurationLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION);
	public static final IDbStringField<AGLiveSystemConfigurationLog> SYSTEM_NAME = BUILDER.addStringField("systemName", o->o.m_systemName, (o,v)->o.m_systemName=v).setTitle(CoreI18n.SYSTEM_NAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbForeignField<AGLiveSystemConfigurationLog, AGWeekday> DBMS_DOWN_TIME_WEEKDAY = BUILDER.addForeignField("dbmsDownTimeWeekday", o->o.m_dbmsDownTimeWeekday, (o,v)->o.m_dbmsDownTimeWeekday=v, AGWeekday.ID).setTitle(CoreI18n.DBMS_DOWN_TIME_WEEKDAY).setNullable().setDefault(null);
	public static final IDbTimeField<AGLiveSystemConfigurationLog> DBMS_DOWN_TIME_BEGIN = BUILDER.addTimeField("dbmsDownTimeBegin", o->o.m_dbmsDownTimeBegin, (o,v)->o.m_dbmsDownTimeBegin=v).setTitle(CoreI18n.DBMS_DOWN_TIME_BEGIN).setNullable().setDefault(null);
	public static final IDbTimeField<AGLiveSystemConfigurationLog> DBMS_DOWN_TIME_END = BUILDER.addTimeField("dbmsDownTimeEnd", o->o.m_dbmsDownTimeEnd, (o,v)->o.m_dbmsDownTimeEnd=v).setTitle(CoreI18n.DBMS_DOWN_TIME_END).setNullable().setDefault(null);
	public static final IDbTableKey<AGLiveSystemConfigurationLog, Tuple2<AGLiveSystemConfiguration, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(LIVE_SYSTEM_CONFIGURATION, TRANSACTION));
	public static final IDbKey<AGLiveSystemConfigurationLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGLiveSystemConfigurationLog> IK_DBMS_DOWN_TIME_WEEKDAY = BUILDER.addIndexKey("dbmsDownTimeWeekday", DBMS_DOWN_TIME_WEEKDAY);
	public static final DbRecordTable<AGLiveSystemConfigurationLog, Tuple2<AGLiveSystemConfiguration, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGLiveSystemConfigurationLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGLiveSystemConfiguration getLiveSystemConfiguration() {

		return getValue(LIVE_SYSTEM_CONFIGURATION);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final String getSystemName() {

		return getValue(SYSTEM_NAME);
	}

	public final AGLiveSystemConfigurationLog setSystemName(String value) {

		return setValue(SYSTEM_NAME, value);
	}

	public final Integer getDbmsDownTimeWeekdayID() {

		return getValueId(DBMS_DOWN_TIME_WEEKDAY);
	}

	public final AGWeekday getDbmsDownTimeWeekday() {

		return getValue(DBMS_DOWN_TIME_WEEKDAY);
	}

	public final AGLiveSystemConfigurationLog setDbmsDownTimeWeekday(AGWeekday value) {

		return setValue(DBMS_DOWN_TIME_WEEKDAY, value);
	}

	public final Time getDbmsDownTimeBegin() {

		return getValue(DBMS_DOWN_TIME_BEGIN);
	}

	public final AGLiveSystemConfigurationLog setDbmsDownTimeBegin(Time value) {

		return setValue(DBMS_DOWN_TIME_BEGIN, value);
	}

	public final Time getDbmsDownTimeEnd() {

		return getValue(DBMS_DOWN_TIME_END);
	}

	public final AGLiveSystemConfigurationLog setDbmsDownTimeEnd(Time value) {

		return setValue(DBMS_DOWN_TIME_END, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGLiveSystemConfigurationLog, Tuple2<AGLiveSystemConfiguration, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGLiveSystemConfiguration m_liveSystemConfiguration;
	private AGTransaction m_transaction;
	private String m_systemName;
	private AGWeekday m_dbmsDownTimeWeekday;
	private Time m_dbmsDownTimeBegin;
	private Time m_dbmsDownTimeEnd;
}

