package com.softicar.platform.core.module.log.message;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.log.level.AGLogLevel;
import com.softicar.platform.core.module.log.process.AGLogProcess;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;

/**
 * This is the automatically generated class AGLogMessage for
 * database table <i>Core.LogMessage</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGLogMessageGenerated extends AbstractDbObject<AGLogMessage> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGLogMessage, AGLogMessageGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "LogMessage", AGLogMessage::new, AGLogMessage.class);
	static {
		BUILDER.setTitle(CoreI18n.LOG_MESSAGE);
		BUILDER.setPluralTitle(CoreI18n.LOG_MESSAGES);
	}

	public static final IDbIdField<AGLogMessage> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGLogMessage, AGLogProcess> PROCESS = BUILDER.addForeignField("process", o->o.m_process, (o,v)->o.m_process=v, AGLogProcess.ID).setTitle(CoreI18n.PROCESS).setOnDelete(DbForeignKeyAction.CASCADE).setOnUpdate(DbForeignKeyAction.NO_ACTION);
	public static final IDbForeignField<AGLogMessage, AGLogLevel> LEVEL = BUILDER.addForeignField("level", o->o.m_level, (o,v)->o.m_level=v, AGLogLevel.ID).setTitle(CoreI18n.LEVEL).setNullable().setDefault(null);
	public static final IDbStringField<AGLogMessage> LOG_TEXT = BUILDER.addStringField("logText", o->o.m_logText, (o,v)->o.m_logText=v).setTitle(CoreI18n.LOG_TEXT).setLengthBits(16);
	public static final IDbDayTimeField<AGLogMessage> LOG_TIME = BUILDER.addDayTimeField("logTime", o->o.m_logTime, (o,v)->o.m_logTime=v).setTitle(CoreI18n.LOG_TIME).setDefaultNow().setTimestamp();
	public static final IDbDayTimeField<AGLogMessage> NOTIFICATION_TIME = BUILDER.addDayTimeField("notificationTime", o->o.m_notificationTime, (o,v)->o.m_notificationTime=v).setTitle(CoreI18n.NOTIFICATION_TIME).setNullable().setDefault(null);
	public static final IDbKey<AGLogMessage> IK_PROCESS = BUILDER.addIndexKey("process", PROCESS);
	public static final IDbKey<AGLogMessage> IK_LEVEL = BUILDER.addIndexKey("level", LEVEL);
	public static final AGLogMessageTable TABLE = new AGLogMessageTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGLogMessage> createSelect() {

		return TABLE.createSelect();
	}

	public static AGLogMessage get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getProcessID() {

		return getValueId(PROCESS);
	}

	public final AGLogProcess getProcess() {

		return getValue(PROCESS);
	}

	public final AGLogMessage setProcess(AGLogProcess value) {

		return setValue(PROCESS, value);
	}

	public final Integer getLevelID() {

		return getValueId(LEVEL);
	}

	public final AGLogLevel getLevel() {

		return getValue(LEVEL);
	}

	public final AGLogMessage setLevel(AGLogLevel value) {

		return setValue(LEVEL, value);
	}

	public final String getLogText() {

		return getValue(LOG_TEXT);
	}

	public final AGLogMessage setLogText(String value) {

		return setValue(LOG_TEXT, value);
	}

	public final DayTime getLogTime() {

		return getValue(LOG_TIME);
	}

	public final AGLogMessage setLogTime(DayTime value) {

		return setValue(LOG_TIME, value);
	}

	public final DayTime getNotificationTime() {

		return getValue(NOTIFICATION_TIME);
	}

	public final AGLogMessage setNotificationTime(DayTime value) {

		return setValue(NOTIFICATION_TIME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGLogMessageTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGLogProcess m_process;
	private AGLogLevel m_level;
	private String m_logText;
	private DayTime m_logTime;
	private DayTime m_notificationTime;
}

