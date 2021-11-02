package com.softicar.platform.core.module.log.level;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

/**
 * This is the automatically generated class AGLogLevel for
 * database table <i>Core.LogLevel</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGLogLevelGenerated extends AbstractDbEnumTableRow<AGLogLevel, AGLogLevelEnum> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGLogLevel, AGLogLevelGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "LogLevel", AGLogLevel::new, AGLogLevel.class);
	static {
		BUILDER.setTitle(CoreI18n.LOG_LEVEL);
		BUILDER.setPluralTitle(CoreI18n.LOG_LEVELS);
	}

	public static final IDbIdField<AGLogLevel> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGLogLevel> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final DbEnumTable<AGLogLevel, AGLogLevelEnum> TABLE = new DbEnumTable<>(BUILDER, AGLogLevelEnum.values());
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGLogLevel setName(String value) {

		return setValue(NAME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbEnumTable<AGLogLevel, AGLogLevelEnum> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
}

