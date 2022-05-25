package com.softicar.platform.core.module.event.severity;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

/**
 * This is the automatically generated class AGSystemEventSeverity for
 * database table <i>Core.SystemEventSeverity</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGSystemEventSeverityGenerated extends AbstractDbEnumTableRow<AGSystemEventSeverity, AGSystemEventSeverityEnum> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGSystemEventSeverity, AGSystemEventSeverityGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "SystemEventSeverity", AGSystemEventSeverity::new, AGSystemEventSeverity.class);
	static {
		BUILDER.setTitle(CoreI18n.SYSTEM_EVENT_SEVERITY);
		BUILDER.setPluralTitle(CoreI18n.SYSTEM_EVENT_SEVERITIES);
	}

	public static final IDbIdField<AGSystemEventSeverity> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGSystemEventSeverity> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final IDbBooleanField<AGSystemEventSeverity> NEEDS_ATTENTION = BUILDER.addBooleanField("needsAttention", o->o.m_needsAttention, (o,v)->o.m_needsAttention=v).setTitle(CoreI18n.NEEDS_ATTENTION);
	public static final DbEnumTable<AGSystemEventSeverity, AGSystemEventSeverityEnum> TABLE = new DbEnumTable<>(BUILDER, AGSystemEventSeverityEnum.values());
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGSystemEventSeverity setName(String value) {

		return setValue(NAME, value);
	}

	public final Boolean isNeedsAttention() {

		return getValue(NEEDS_ATTENTION);
	}

	public final AGSystemEventSeverity setNeedsAttention(Boolean value) {

		return setValue(NEEDS_ATTENTION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbEnumTable<AGSystemEventSeverity, AGSystemEventSeverityEnum> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
	private Boolean m_needsAttention;
}

