package com.softicar.platform.core.module.date.weekday;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

/**
 * This is the automatically generated class AGWeekday for
 * database table <i>Core.Weekday</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWeekdayGenerated extends AbstractDbEnumTableRow<AGWeekday, AGWeekdayEnum> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWeekday, AGWeekdayGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "Weekday", AGWeekday::new, AGWeekday.class);
	static {
		BUILDER.setTitle(CoreI18n.WEEKDAY);
		BUILDER.setPluralTitle(CoreI18n.WEEKDAYS);
	}

	public static final IDbIdField<AGWeekday> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGWeekday> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final IDbKey<AGWeekday> UK_NAME = BUILDER.addUniqueKey("name", NAME);
	public static final DbEnumTable<AGWeekday, AGWeekdayEnum> TABLE = new DbEnumTable<>(BUILDER, AGWeekdayEnum.values());
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGWeekday setName(String value) {

		return setValue(NAME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbEnumTable<AGWeekday, AGWeekdayEnum> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
}

