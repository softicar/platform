package com.softicar.platform.core.module.ajax.event;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

/**
 * This is the automatically generated class AGAjaxEventType for
 * database table <i>Core.AjaxEventType</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGAjaxEventType extends AbstractDbEnumTableRow<AGAjaxEventType, AGAjaxEventTypeEnum> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGAjaxEventType, AGAjaxEventType> BUILDER = new DbObjectTableBuilder<>("Core", "AjaxEventType", AGAjaxEventType::new, AGAjaxEventType.class);
	static {
		BUILDER.setTitle(CoreI18n.AJAX_EVENT_TYPE);
		BUILDER.setPluralTitle(CoreI18n.AJAX_EVENT_TYPES);
	}

	public static final IDbIdField<AGAjaxEventType> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGAjaxEventType> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final DbEnumTable<AGAjaxEventType, AGAjaxEventTypeEnum> TABLE = new DbEnumTable<>(BUILDER, AGAjaxEventTypeEnum.values());
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGAjaxEventType setName(String value) {

		return setValue(NAME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbEnumTable<AGAjaxEventType, AGAjaxEventTypeEnum> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
}

