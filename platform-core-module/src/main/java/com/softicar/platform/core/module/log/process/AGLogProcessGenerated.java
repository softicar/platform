package com.softicar.platform.core.module.log.process;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.log.level.AGLogLevel;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGLogProcess for
 * database table <i>Core.LogProcess</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGLogProcessGenerated extends AbstractDbObject<AGLogProcess> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGLogProcess, AGLogProcessGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "LogProcess", AGLogProcess::new, AGLogProcess.class);
	static {
		BUILDER.setTitle(CoreI18n.LOG_PROCESS);
		BUILDER.setPluralTitle(CoreI18n.LOG_PROCESSES);
	}

	public static final IDbIdField<AGLogProcess> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGLogProcess> SERVER_IP = BUILDER.addStringField("serverIp", o->o.m_serverIp, (o,v)->o.m_serverIp=v).setTitle(CoreI18n.SERVER_IP).setDefault("").setMaximumLength(255);
	public static final IDbStringField<AGLogProcess> CLASS_NAME = BUILDER.addStringField("className", o->o.m_className, (o,v)->o.m_className=v).setTitle(CoreI18n.CLASS_NAME).setMaximumLength(255);
	public static final IDbDayTimeField<AGLogProcess> START_TIME = BUILDER.addDayTimeField("startTime", o->o.m_startTime, (o,v)->o.m_startTime=v).setTitle(CoreI18n.START_TIME).setDefaultNow().setTimestamp();
	public static final IDbForeignField<AGLogProcess, AGLogLevel> WORST_LEVEL = BUILDER.addForeignField("worstLevel", o->o.m_worstLevel, (o,v)->o.m_worstLevel=v, AGLogLevel.ID).setTitle(CoreI18n.WORST_LEVEL).setNullable().setDefault(null).setForeignKeyName("LogProcess_ibfk_1");
	public static final IDbKey<AGLogProcess> IK_START_TIME = BUILDER.addIndexKey("startTime", START_TIME);
	public static final IDbKey<AGLogProcess> IK_CLASS_NAME = BUILDER.addIndexKey("className", CLASS_NAME);
	public static final IDbKey<AGLogProcess> IK_WORST_LEVEL = BUILDER.addIndexKey("worstLevel", WORST_LEVEL);
	public static final DbObjectTable<AGLogProcess> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGLogProcess> createSelect() {

		return TABLE.createSelect();
	}

	public static AGLogProcess get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getServerIp() {

		return getValue(SERVER_IP);
	}

	public final AGLogProcess setServerIp(String value) {

		return setValue(SERVER_IP, value);
	}

	public final String getClassName() {

		return getValue(CLASS_NAME);
	}

	public final AGLogProcess setClassName(String value) {

		return setValue(CLASS_NAME, value);
	}

	public final DayTime getStartTime() {

		return getValue(START_TIME);
	}

	public final AGLogProcess setStartTime(DayTime value) {

		return setValue(START_TIME, value);
	}

	public final Integer getWorstLevelID() {

		return getValueId(WORST_LEVEL);
	}

	public final AGLogLevel getWorstLevel() {

		return getValue(WORST_LEVEL);
	}

	public final AGLogProcess setWorstLevel(AGLogLevel value) {

		return setValue(WORST_LEVEL, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbObjectTable<AGLogProcess> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_serverIp;
	private String m_className;
	private DayTime m_startTime;
	private AGLogLevel m_worstLevel;
}

