package com.softicar.platform.core.module.environment;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.date.weekday.AGWeekday;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.field.IDbTimeField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGLiveSystemConfiguration for
 * database table <i>Core.LiveSystemConfiguration</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGLiveSystemConfigurationGenerated extends AbstractDbRecord<AGLiveSystemConfiguration, AGCoreModuleInstance> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGLiveSystemConfiguration, AGLiveSystemConfigurationGenerated, AGCoreModuleInstance> BUILDER = new DbTableBuilder<>("Core", "LiveSystemConfiguration", AGLiveSystemConfiguration::new, AGLiveSystemConfiguration.class);
	static {
		BUILDER.setTitle(CoreI18n.LIVE_SYSTEM_CONFIGURATION);
		BUILDER.setPluralTitle(CoreI18n.LIVE_SYSTEM_CONFIGURATIONS);
	}

	public static final IDbForeignRowField<AGLiveSystemConfiguration, AGCoreModuleInstance, AGModuleInstance> CORE_MODULE_INSTANCE = BUILDER.addForeignRowField("coreModuleInstance", o->o.m_coreModuleInstance, (o,v)->o.m_coreModuleInstance=v, AGCoreModuleInstance.MODULE_INSTANCE).setTitle(CoreI18n.CORE_MODULE_INSTANCE).setCascade(true, true);
	public static final IDbStringField<AGLiveSystemConfiguration> SYSTEM_NAME = BUILDER.addStringField("systemName", o->o.m_systemName, (o,v)->o.m_systemName=v).setTitle(CoreI18n.SYSTEM_NAME).setDefault("SoftiCAR").setMaximumLength(255);
	public static final IDbForeignField<AGLiveSystemConfiguration, AGWeekday> DBMS_DOWN_TIME_WEEKDAY = BUILDER.addForeignField("dbmsDownTimeWeekday", o->o.m_dbmsDownTimeWeekday, (o,v)->o.m_dbmsDownTimeWeekday=v, AGWeekday.ID).setTitle(CoreI18n.DBMS_DOWN_TIME_WEEKDAY).setNullable().setDefault(null);
	public static final IDbTimeField<AGLiveSystemConfiguration> DBMS_DOWN_TIME_BEGIN = BUILDER.addTimeField("dbmsDownTimeBegin", o->o.m_dbmsDownTimeBegin, (o,v)->o.m_dbmsDownTimeBegin=v).setTitle(CoreI18n.DBMS_DOWN_TIME_BEGIN).setNullable().setDefault(null);
	public static final IDbTimeField<AGLiveSystemConfiguration> DBMS_DOWN_TIME_END = BUILDER.addTimeField("dbmsDownTimeEnd", o->o.m_dbmsDownTimeEnd, (o,v)->o.m_dbmsDownTimeEnd=v).setTitle(CoreI18n.DBMS_DOWN_TIME_END).setNullable().setDefault(null);
	public static final IDbTableKey<AGLiveSystemConfiguration, AGCoreModuleInstance> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(CORE_MODULE_INSTANCE));
	public static final IDbKey<AGLiveSystemConfiguration> IK_DBMS_DOWN_TIME_WEEKDAY = BUILDER.addIndexKey("dbmsDownTimeWeekday", DBMS_DOWN_TIME_WEEKDAY);
	public static final AGLiveSystemConfigurationTable TABLE = new AGLiveSystemConfigurationTable(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGLiveSystemConfigurationGenerated() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGCoreModuleInstance getCoreModuleInstance() {

		return getValue(CORE_MODULE_INSTANCE);
	}

	public final String getSystemName() {

		return getValue(SYSTEM_NAME);
	}

	public final AGLiveSystemConfiguration setSystemName(String value) {

		return setValue(SYSTEM_NAME, value);
	}

	public final Integer getDbmsDownTimeWeekdayID() {

		return getValueId(DBMS_DOWN_TIME_WEEKDAY);
	}

	public final AGWeekday getDbmsDownTimeWeekday() {

		return getValue(DBMS_DOWN_TIME_WEEKDAY);
	}

	public final AGLiveSystemConfiguration setDbmsDownTimeWeekday(AGWeekday value) {

		return setValue(DBMS_DOWN_TIME_WEEKDAY, value);
	}

	public final Time getDbmsDownTimeBegin() {

		return getValue(DBMS_DOWN_TIME_BEGIN);
	}

	public final AGLiveSystemConfiguration setDbmsDownTimeBegin(Time value) {

		return setValue(DBMS_DOWN_TIME_BEGIN, value);
	}

	public final Time getDbmsDownTimeEnd() {

		return getValue(DBMS_DOWN_TIME_END);
	}

	public final AGLiveSystemConfiguration setDbmsDownTimeEnd(Time value) {

		return setValue(DBMS_DOWN_TIME_END, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGLiveSystemConfigurationTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGCoreModuleInstance m_coreModuleInstance;
	private String m_systemName;
	private AGWeekday m_dbmsDownTimeWeekday;
	private Time m_dbmsDownTimeBegin;
	private Time m_dbmsDownTimeEnd;
}

