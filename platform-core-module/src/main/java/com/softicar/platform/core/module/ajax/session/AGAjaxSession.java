package com.softicar.platform.core.module.ajax.session;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGAjaxSession for
 * database table <i>Core.AjaxSession</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGAjaxSession extends AbstractDbObject<AGAjaxSession> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGAjaxSession, AGAjaxSession> BUILDER = new DbObjectTableBuilder<>("Core", "AjaxSession", AGAjaxSession::new, AGAjaxSession.class);
	static {
		BUILDER.setTitle(CoreI18n.AJAX_SESSION);
		BUILDER.setPluralTitle(CoreI18n.AJAX_SESSIONS);
	}

	public static final IDbIdField<AGAjaxSession> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGAjaxSession, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER).setNullable().setDefault(null).setCascade(true, true);
	public static final IDbStringField<AGAjaxSession> CLIENT_IP_ADDRESS = BUILDER.addStringField("clientIpAddress", o->o.m_clientIpAddress, (o,v)->o.m_clientIpAddress=v).setTitle(CoreI18n.CLIENT_IP_ADDRESS).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGAjaxSession> LOCAL_NAME = BUILDER.addStringField("localName", o->o.m_localName, (o,v)->o.m_localName=v).setTitle(CoreI18n.LOCAL_NAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGAjaxSession> LOCAL_ADDRESS = BUILDER.addStringField("localAddress", o->o.m_localAddress, (o,v)->o.m_localAddress=v).setTitle(CoreI18n.LOCAL_ADDRESS).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbIntegerField<AGAjaxSession> LOCAL_PORT = BUILDER.addIntegerField("localPort", o->o.m_localPort, (o,v)->o.m_localPort=v).setTitle(CoreI18n.LOCAL_PORT).setDefault(0);
	public static final IDbDayTimeField<AGAjaxSession> ACCESS_DATE = BUILDER.addDayTimeField("accessDate", o->o.m_accessDate, (o,v)->o.m_accessDate=v).setTitle(CoreI18n.ACCESS_DATE).setDefault(new DayTime(Day.get(719528), 0, 0, 0));
	public static final IDbKey<AGAjaxSession> IK_USER = BUILDER.addIndexKey("user", USER);
	public static final DbObjectTable<AGAjaxSession> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGAjaxSession> createSelect() {

		return TABLE.createSelect();
	}

	public static AGAjaxSession get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGAjaxSession setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final String getClientIpAddress() {

		return getValue(CLIENT_IP_ADDRESS);
	}

	public final AGAjaxSession setClientIpAddress(String value) {

		return setValue(CLIENT_IP_ADDRESS, value);
	}

	public final String getLocalName() {

		return getValue(LOCAL_NAME);
	}

	public final AGAjaxSession setLocalName(String value) {

		return setValue(LOCAL_NAME, value);
	}

	public final String getLocalAddress() {

		return getValue(LOCAL_ADDRESS);
	}

	public final AGAjaxSession setLocalAddress(String value) {

		return setValue(LOCAL_ADDRESS, value);
	}

	public final Integer getLocalPort() {

		return getValue(LOCAL_PORT);
	}

	public final AGAjaxSession setLocalPort(Integer value) {

		return setValue(LOCAL_PORT, value);
	}

	public final DayTime getAccessDate() {

		return getValue(ACCESS_DATE);
	}

	public final AGAjaxSession setAccessDate(DayTime value) {

		return setValue(ACCESS_DATE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbObjectTable<AGAjaxSession> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUser m_user;
	private String m_clientIpAddress;
	private String m_localName;
	private String m_localAddress;
	private Integer m_localPort;
	private DayTime m_accessDate;
}

