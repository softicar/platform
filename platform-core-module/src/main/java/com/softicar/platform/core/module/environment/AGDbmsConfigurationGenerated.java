package com.softicar.platform.core.module.environment;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.date.weekday.AGWeekday;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbTimeField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGDbmsConfiguration for
 * database table <i>Core.DbmsConfiguration</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDbmsConfigurationGenerated extends AbstractDbRecord<AGDbmsConfiguration, AGCoreModuleInstance> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGDbmsConfiguration, AGDbmsConfigurationGenerated, AGCoreModuleInstance> BUILDER = new DbTableBuilder<>("Core", "DbmsConfiguration", AGDbmsConfiguration::new, AGDbmsConfiguration.class);
	static {
		BUILDER.setTitle(CoreI18n.DBMS_CONFIGURATION);
		BUILDER.setPluralTitle(CoreI18n.DBMS_CONFIGURATIONS);
	}

	public static final IDbForeignRowField<AGDbmsConfiguration, AGCoreModuleInstance, AGModuleInstance> CORE_MODULE_INSTANCE = BUILDER.addForeignRowField("coreModuleInstance", o->o.m_coreModuleInstance, (o,v)->o.m_coreModuleInstance=v, AGCoreModuleInstance.MODULE_INSTANCE).setTitle(CoreI18n.CORE_MODULE_INSTANCE).setCascade(true, true);
	public static final IDbForeignField<AGDbmsConfiguration, AGWeekday> DBMS_DOWN_TIME_WEEKDAY = BUILDER.addForeignField("dbmsDownTimeWeekday", o->o.m_dbmsDownTimeWeekday, (o,v)->o.m_dbmsDownTimeWeekday=v, AGWeekday.ID).setTitle(CoreI18n.DBMS_DOWN_TIME_WEEKDAY).setNullable().setDefault(null);
	public static final IDbTimeField<AGDbmsConfiguration> DBMS_DOWN_TIME_BEGIN = BUILDER.addTimeField("dbmsDownTimeBegin", o->o.m_dbmsDownTimeBegin, (o,v)->o.m_dbmsDownTimeBegin=v).setTitle(CoreI18n.DBMS_DOWN_TIME_BEGIN).setNullable().setDefault(null);
	public static final IDbTimeField<AGDbmsConfiguration> DBMS_DOWN_TIME_END = BUILDER.addTimeField("dbmsDownTimeEnd", o->o.m_dbmsDownTimeEnd, (o,v)->o.m_dbmsDownTimeEnd=v).setTitle(CoreI18n.DBMS_DOWN_TIME_END).setNullable().setDefault(null);
	public static final IDbTableKey<AGDbmsConfiguration, AGCoreModuleInstance> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(CORE_MODULE_INSTANCE));
	public static final IDbKey<AGDbmsConfiguration> IK_DBMS_DOWN_TIME_WEEKDAY = BUILDER.addIndexKey("dbmsDownTimeWeekday", DBMS_DOWN_TIME_WEEKDAY);
	public static final AGDbmsConfigurationTable TABLE = new AGDbmsConfigurationTable(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGDbmsConfigurationGenerated() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGCoreModuleInstance getCoreModuleInstance() {

		return getValue(CORE_MODULE_INSTANCE);
	}

	public final Integer getDbmsDownTimeWeekdayID() {

		return getValueId(DBMS_DOWN_TIME_WEEKDAY);
	}

	public final AGWeekday getDbmsDownTimeWeekday() {

		return getValue(DBMS_DOWN_TIME_WEEKDAY);
	}

	public final AGDbmsConfiguration setDbmsDownTimeWeekday(AGWeekday value) {

		return setValue(DBMS_DOWN_TIME_WEEKDAY, value);
	}

	public final Time getDbmsDownTimeBegin() {

		return getValue(DBMS_DOWN_TIME_BEGIN);
	}

	public final AGDbmsConfiguration setDbmsDownTimeBegin(Time value) {

		return setValue(DBMS_DOWN_TIME_BEGIN, value);
	}

	public final Time getDbmsDownTimeEnd() {

		return getValue(DBMS_DOWN_TIME_END);
	}

	public final AGDbmsConfiguration setDbmsDownTimeEnd(Time value) {

		return setValue(DBMS_DOWN_TIME_END, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDbmsConfigurationTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGCoreModuleInstance m_coreModuleInstance;
	private AGWeekday m_dbmsDownTimeWeekday;
	private Time m_dbmsDownTimeBegin;
	private Time m_dbmsDownTimeEnd;
}

