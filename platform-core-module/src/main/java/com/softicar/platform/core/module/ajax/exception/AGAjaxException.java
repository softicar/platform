package com.softicar.platform.core.module.ajax.exception;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.ajax.session.AGAjaxSession;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;

/**
 * This is the automatically generated class AGAjaxException for
 * database table <i>Core.AjaxException</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGAjaxException extends AbstractDbObject<AGAjaxException> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGAjaxException, AGAjaxException> BUILDER = new DbObjectTableBuilder<>("Core", "AjaxException", AGAjaxException::new, AGAjaxException.class);
	static {
		BUILDER.setTitle(CoreI18n.AJAX_EXCEPTION);
		BUILDER.setPluralTitle(CoreI18n.AJAX_EXCEPTIONS);
	}

	public static final IDbIdField<AGAjaxException> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGAjaxException, AGAjaxSession> SESSION = BUILDER.addForeignField("session", o->o.m_session, (o,v)->o.m_session=v, AGAjaxSession.ID).setTitle(CoreI18n.SESSION).setOnDelete(DbForeignKeyAction.CASCADE).setOnUpdate(DbForeignKeyAction.NO_ACTION);
	public static final IDbForeignField<AGAjaxException, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER).setNullable().setDefault(null);
	public static final IDbDayTimeField<AGAjaxException> EXCEPTION_DATE = BUILDER.addDayTimeField("exceptionDate", o->o.m_exceptionDate, (o,v)->o.m_exceptionDate=v).setTitle(CoreI18n.EXCEPTION_DATE).setDefault(new DayTime(Day.get(719528), 0, 0, 0));
	public static final IDbStringField<AGAjaxException> EXCEPTION_TYPE = BUILDER.addStringField("exceptionType", o->o.m_exceptionType, (o,v)->o.m_exceptionType=v).setTitle(CoreI18n.EXCEPTION_TYPE).setMaximumLength(255);
	public static final IDbStringField<AGAjaxException> EXCEPTION_TEXT = BUILDER.addStringField("exceptionText", o->o.m_exceptionText, (o,v)->o.m_exceptionText=v).setTitle(CoreI18n.EXCEPTION_TEXT).setNullable().setLengthBits(16);
	public static final IDbStringField<AGAjaxException> EXCEPTION_STACK_TRACE = BUILDER.addStringField("exceptionStackTrace", o->o.m_exceptionStackTrace, (o,v)->o.m_exceptionStackTrace=v).setTitle(CoreI18n.EXCEPTION_STACK_TRACE).setLengthBits(16);
	public static final IDbKey<AGAjaxException> IK_EXCEPTION_DATE = BUILDER.addIndexKey("exceptionDate", EXCEPTION_DATE);
	public static final IDbKey<AGAjaxException> IK_SESSION = BUILDER.addIndexKey("session", SESSION);
	public static final IDbKey<AGAjaxException> IK_USER = BUILDER.addIndexKey("user", USER);
	public static final DbObjectTable<AGAjaxException> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGAjaxException> createSelect() {

		return TABLE.createSelect();
	}

	public static AGAjaxException get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getSessionID() {

		return getValueId(SESSION);
	}

	public final AGAjaxSession getSession() {

		return getValue(SESSION);
	}

	public final AGAjaxException setSession(AGAjaxSession value) {

		return setValue(SESSION, value);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGAjaxException setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final DayTime getExceptionDate() {

		return getValue(EXCEPTION_DATE);
	}

	public final AGAjaxException setExceptionDate(DayTime value) {

		return setValue(EXCEPTION_DATE, value);
	}

	public final String getExceptionType() {

		return getValue(EXCEPTION_TYPE);
	}

	public final AGAjaxException setExceptionType(String value) {

		return setValue(EXCEPTION_TYPE, value);
	}

	public final String getExceptionText() {

		return getValue(EXCEPTION_TEXT);
	}

	public final AGAjaxException setExceptionText(String value) {

		return setValue(EXCEPTION_TEXT, value);
	}

	public final String getExceptionStackTrace() {

		return getValue(EXCEPTION_STACK_TRACE);
	}

	public final AGAjaxException setExceptionStackTrace(String value) {

		return setValue(EXCEPTION_STACK_TRACE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbObjectTable<AGAjaxException> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGAjaxSession m_session;
	private AGUser m_user;
	private DayTime m_exceptionDate;
	private String m_exceptionType;
	private String m_exceptionText;
	private String m_exceptionStackTrace;
}

