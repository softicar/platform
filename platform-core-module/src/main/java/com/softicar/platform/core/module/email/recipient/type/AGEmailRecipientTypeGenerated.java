package com.softicar.platform.core.module.email.recipient.type;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

/**
 * This is the automatically generated class AGEmailRecipientType for
 * database table <i>Core.EmailRecipientType</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGEmailRecipientTypeGenerated extends AbstractDbEnumTableRow<AGEmailRecipientType, AGEmailRecipientTypeEnum> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGEmailRecipientType, AGEmailRecipientTypeGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "EmailRecipientType", AGEmailRecipientType::new, AGEmailRecipientType.class);
	static {
		BUILDER.setTitle(CoreI18n.EMAIL_RECIPIENT_TYPE);
		BUILDER.setPluralTitle(CoreI18n.EMAIL_RECIPIENT_TYPES);
	}

	public static final IDbIdField<AGEmailRecipientType> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGEmailRecipientType> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final DbEnumTable<AGEmailRecipientType, AGEmailRecipientTypeEnum> TABLE = new DbEnumTable<>(BUILDER, AGEmailRecipientTypeEnum.values());
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGEmailRecipientType setName(String value) {

		return setValue(NAME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbEnumTable<AGEmailRecipientType, AGEmailRecipientTypeEnum> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
}

