package com.softicar.platform.core.module.start.page;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGStartPageMessage for
 * database table <i>Core.StartPageMessage</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGStartPageMessageGenerated extends AbstractDbObject<AGStartPageMessage> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGStartPageMessage, AGStartPageMessageGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "StartPageMessage", AGStartPageMessage::new, AGStartPageMessage.class);
	static {
		BUILDER.setTitle(CoreI18n.START_PAGE_MESSAGE);
		BUILDER.setPluralTitle(CoreI18n.START_PAGE_MESSAGES);
	}

	public static final IDbIdField<AGStartPageMessage> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbBooleanField<AGStartPageMessage> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbStringField<AGStartPageMessage> MESSAGE = BUILDER.addStringField("message", o->o.m_message, (o,v)->o.m_message=v).setTitle(CoreI18n.MESSAGE).setLengthBits(8);
	public static final IDbDayTimeField<AGStartPageMessage> MESSAGE_DATE = BUILDER.addDayTimeField("messageDate", o->o.m_messageDate, (o,v)->o.m_messageDate=v).setTitle(CoreI18n.MESSAGE_DATE).setDefaultNow();
	public static final AGStartPageMessageTable TABLE = new AGStartPageMessageTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGStartPageMessage> createSelect() {

		return TABLE.createSelect();
	}

	public static AGStartPageMessage get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGStartPageMessage setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getMessage() {

		return getValue(MESSAGE);
	}

	public final AGStartPageMessage setMessage(String value) {

		return setValue(MESSAGE, value);
	}

	public final DayTime getMessageDate() {

		return getValue(MESSAGE_DATE);
	}

	public final AGStartPageMessage setMessageDate(DayTime value) {

		return setValue(MESSAGE_DATE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGStartPageMessageTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private Boolean m_active;
	private String m_message;
	private DayTime m_messageDate;
}

