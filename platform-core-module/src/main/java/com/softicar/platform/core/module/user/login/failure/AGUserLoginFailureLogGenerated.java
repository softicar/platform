package com.softicar.platform.core.module.user.login.failure;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.login.failure.type.AGUserLoginFailureType;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGUserLoginFailureLog for
 * database table <i>Core.UserLoginFailureLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGUserLoginFailureLogGenerated extends AbstractDbObject<AGUserLoginFailureLog> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGUserLoginFailureLog, AGUserLoginFailureLogGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "UserLoginFailureLog", AGUserLoginFailureLog::new, AGUserLoginFailureLog.class);
	static {
		BUILDER.setTitle(CoreI18n.USER_LOGIN_FAILURE_LOG);
		BUILDER.setPluralTitle(CoreI18n.USER_LOGIN_FAILURE_LOGS);
	}

	public static final IDbIdField<AGUserLoginFailureLog> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGUserLoginFailureLog, AGUserLoginFailureType> TYPE = BUILDER.addForeignField("type", o->o.m_type, (o,v)->o.m_type=v, AGUserLoginFailureType.ID).setTitle(CoreI18n.TYPE).setNullable().setDefault(null).setForeignKeyName("UserLoginFailureLog_ibfk_1");
	public static final IDbStringField<AGUserLoginFailureLog> USERNAME = BUILDER.addStringField("username", o->o.m_username, (o,v)->o.m_username=v).setTitle(CoreI18n.USERNAME).setDefault("").setMaximumLength(255);
	public static final IDbStringField<AGUserLoginFailureLog> SERVER_IP_ADDRESS = BUILDER.addStringField("serverIpAddress", o->o.m_serverIpAddress, (o,v)->o.m_serverIpAddress=v).setTitle(CoreI18n.SERVER_IP_ADDRESS).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbDayTimeField<AGUserLoginFailureLog> LOGIN_AT = BUILDER.addDayTimeField("loginAt", o->o.m_loginAt, (o,v)->o.m_loginAt=v).setTitle(CoreI18n.LOGIN_AT);
	public static final IDbKey<AGUserLoginFailureLog> IK_USERNAME_LOGIN_AT = BUILDER.addIndexKey("usernameLoginAt", USERNAME, LOGIN_AT);
	public static final IDbKey<AGUserLoginFailureLog> IK_TYPE = BUILDER.addIndexKey("type", TYPE);
	public static final AGUserLoginFailureLogTable TABLE = new AGUserLoginFailureLogTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGUserLoginFailureLog> createSelect() {

		return TABLE.createSelect();
	}

	public static AGUserLoginFailureLog get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTypeID() {

		return getValueId(TYPE);
	}

	public final AGUserLoginFailureType getType() {

		return getValue(TYPE);
	}

	public final AGUserLoginFailureLog setType(AGUserLoginFailureType value) {

		return setValue(TYPE, value);
	}

	public final String getUsername() {

		return getValue(USERNAME);
	}

	public final AGUserLoginFailureLog setUsername(String value) {

		return setValue(USERNAME, value);
	}

	public final String getServerIpAddress() {

		return getValue(SERVER_IP_ADDRESS);
	}

	public final AGUserLoginFailureLog setServerIpAddress(String value) {

		return setValue(SERVER_IP_ADDRESS, value);
	}

	public final DayTime getLoginAt() {

		return getValue(LOGIN_AT);
	}

	public final AGUserLoginFailureLog setLoginAt(DayTime value) {

		return setValue(LOGIN_AT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGUserLoginFailureLogTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUserLoginFailureType m_type;
	private String m_username;
	private String m_serverIpAddress;
	private DayTime m_loginAt;
}

