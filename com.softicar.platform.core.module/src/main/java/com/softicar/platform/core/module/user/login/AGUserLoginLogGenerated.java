package com.softicar.platform.core.module.user.login;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.password.AGUserPassword;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGUserLoginLog for
 * database table <i>Core.UserLoginLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGUserLoginLogGenerated extends AbstractDbObject<AGUserLoginLog> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGUserLoginLog, AGUserLoginLogGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "UserLoginLog", AGUserLoginLog::new, AGUserLoginLog.class);
	static {
		BUILDER.setTitle(CoreI18n.USER_LOGIN_LOG);
		BUILDER.setPluralTitle(CoreI18n.USER_LOGIN_LOGS);
	}

	public static final IDbIdField<AGUserLoginLog> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGUserLoginLog, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER);
	public static final IDbForeignField<AGUserLoginLog, AGUserPassword> PASSWORD = BUILDER.addForeignField("password", o->o.m_password, (o,v)->o.m_password=v, AGUserPassword.ID).setTitle(CoreI18n.PASSWORD);
	public static final IDbDayTimeField<AGUserLoginLog> LOGIN_AT = BUILDER.addDayTimeField("loginAt", o->o.m_loginAt, (o,v)->o.m_loginAt=v).setTitle(CoreI18n.LOGIN_AT);
	public static final IDbKey<AGUserLoginLog> IK_PASSWORD = BUILDER.addIndexKey("password", PASSWORD);
	public static final IDbKey<AGUserLoginLog> IK_LOGIN_AT = BUILDER.addIndexKey("loginAt", LOGIN_AT);
	public static final IDbKey<AGUserLoginLog> IK_USER_LOGIN_AT = BUILDER.addIndexKey("userLoginAt", USER, LOGIN_AT);
	public static final AGUserLoginLogTable TABLE = new AGUserLoginLogTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGUserLoginLog> createSelect() {

		return TABLE.createSelect();
	}

	public static AGUserLoginLog get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGUserLoginLog setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final Integer getPasswordID() {

		return getValueId(PASSWORD);
	}

	public final AGUserPassword getPassword() {

		return getValue(PASSWORD);
	}

	public final AGUserLoginLog setPassword(AGUserPassword value) {

		return setValue(PASSWORD, value);
	}

	public final DayTime getLoginAt() {

		return getValue(LOGIN_AT);
	}

	public final AGUserLoginLog setLoginAt(DayTime value) {

		return setValue(LOGIN_AT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGUserLoginLogTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUser m_user;
	private AGUserPassword m_password;
	private DayTime m_loginAt;
}

