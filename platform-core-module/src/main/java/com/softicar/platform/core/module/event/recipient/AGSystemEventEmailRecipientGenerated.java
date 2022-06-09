package com.softicar.platform.core.module.event.recipient;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGSystemEventEmailRecipient for
 * database table <i>Core.SystemEventEmailRecipient</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGSystemEventEmailRecipientGenerated extends AbstractDbObject<AGSystemEventEmailRecipient> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGSystemEventEmailRecipient, AGSystemEventEmailRecipientGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "SystemEventEmailRecipient", AGSystemEventEmailRecipient::new, AGSystemEventEmailRecipient.class);
	static {
		BUILDER.setTitle(CoreI18n.SYSTEM_EVENT_EMAIL_RECIPIENT);
		BUILDER.setPluralTitle(CoreI18n.SYSTEM_EVENT_EMAIL_RECIPIENTS);
	}

	public static final IDbIdField<AGSystemEventEmailRecipient> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGSystemEventEmailRecipient, AGUser> RECIPIENT = BUILDER.addForeignField("recipient", o->o.m_recipient, (o,v)->o.m_recipient=v, AGUser.ID).setTitle(CoreI18n.RECIPIENT);
	public static final IDbBooleanField<AGSystemEventEmailRecipient> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final AGSystemEventEmailRecipientTable TABLE = new AGSystemEventEmailRecipientTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGSystemEventEmailRecipient> createSelect() {

		return TABLE.createSelect();
	}

	public static AGSystemEventEmailRecipient get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getRecipientID() {

		return getValueId(RECIPIENT);
	}

	public final AGUser getRecipient() {

		return getValue(RECIPIENT);
	}

	public final AGSystemEventEmailRecipient setRecipient(AGUser value) {

		return setValue(RECIPIENT, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGSystemEventEmailRecipient setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGSystemEventEmailRecipientTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUser m_recipient;
	private Boolean m_active;
}

