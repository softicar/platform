package com.softicar.platform.core.module.ajax.event;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.ajax.session.AGAjaxSession;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGAjaxEvent for
 * database table <i>Core.AjaxEvent</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGAjaxEventGenerated extends AbstractDbObject<AGAjaxEvent> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGAjaxEvent, AGAjaxEventGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "AjaxEvent", AGAjaxEvent::new, AGAjaxEvent.class);
	static {
		BUILDER.setTitle(CoreI18n.AJAX_EVENT);
		BUILDER.setPluralTitle(CoreI18n.AJAX_EVENTS);
	}

	public static final IDbIdField<AGAjaxEvent> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGAjaxEvent, AGAjaxSession> SESSION = BUILDER.addForeignField("session", o->o.m_session, (o,v)->o.m_session=v, AGAjaxSession.ID).setTitle(CoreI18n.SESSION).setCascade(true, true);
	public static final IDbForeignField<AGAjaxEvent, AGAjaxEventType> TYPE = BUILDER.addForeignField("type", o->o.m_type, (o,v)->o.m_type=v, AGAjaxEventType.ID).setTitle(CoreI18n.TYPE).setNullable().setDefault(null);
	public static final IDbDayTimeField<AGAjaxEvent> EVENT_DATE = BUILDER.addDayTimeField("eventDate", o->o.m_eventDate, (o,v)->o.m_eventDate=v).setTitle(CoreI18n.EVENT_DATE).setDefault(new DayTime(Day.get(719528), 0, 0, 0));
	public static final IDbForeignField<AGAjaxEvent, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER).setNullable().setDefault(null);
	public static final IDbForeignField<AGAjaxEvent, AGUuid> PAGE_UUID = BUILDER.addForeignField("pageUuid", o->o.m_pageUuid, (o,v)->o.m_pageUuid=v, AGUuid.ID).setTitle(CoreI18n.PAGE_UUID).setCascade(true, true);
	public static final AGAjaxEventTable TABLE = new AGAjaxEventTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGAjaxEvent> createSelect() {

		return TABLE.createSelect();
	}

	public static AGAjaxEvent get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getSessionID() {

		return getValueId(SESSION);
	}

	public final AGAjaxSession getSession() {

		return getValue(SESSION);
	}

	public final AGAjaxEvent setSession(AGAjaxSession value) {

		return setValue(SESSION, value);
	}

	public final Integer getTypeID() {

		return getValueId(TYPE);
	}

	public final AGAjaxEventType getType() {

		return getValue(TYPE);
	}

	public final AGAjaxEvent setType(AGAjaxEventType value) {

		return setValue(TYPE, value);
	}

	public final DayTime getEventDate() {

		return getValue(EVENT_DATE);
	}

	public final AGAjaxEvent setEventDate(DayTime value) {

		return setValue(EVENT_DATE, value);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGAjaxEvent setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final Integer getPageUuidID() {

		return getValueId(PAGE_UUID);
	}

	public final AGUuid getPageUuid() {

		return getValue(PAGE_UUID);
	}

	public final AGAjaxEvent setPageUuid(AGUuid value) {

		return setValue(PAGE_UUID, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGAjaxEventTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGAjaxSession m_session;
	private AGAjaxEventType m_type;
	private DayTime m_eventDate;
	private AGUser m_user;
	private AGUuid m_pageUuid;
}

