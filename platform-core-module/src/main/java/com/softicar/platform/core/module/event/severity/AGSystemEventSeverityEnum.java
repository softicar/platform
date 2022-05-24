package com.softicar.platform.core.module.event.severity;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGSystemEventSeverityEnum implements IDbEnumTableRowEnum<AGSystemEventSeverityEnum, AGSystemEventSeverity> {

	ERROR(1, "ERROR", true),
	WARNING(2, "WARNING", true),
	INFORMATION(3, "INFORMATION", false),
	//
	;

	private Integer id;
	private String name;
	private Boolean needsAttention;

	private AGSystemEventSeverityEnum(Integer id, String name, Boolean needsAttention) {

		this.id = id;
		this.name = name;
		this.needsAttention = needsAttention;
	}

	@Override
	public DbEnumTable<AGSystemEventSeverity, AGSystemEventSeverityEnum> getTable() {

		return AGSystemEventSeverity.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGSystemEventSeverity> consumer) {

		consumer.consumeFieldValue(AGSystemEventSeverity.ID, id);
		consumer.consumeFieldValue(AGSystemEventSeverity.NAME, name);
		consumer.consumeFieldValue(AGSystemEventSeverity.NEEDS_ATTENTION, needsAttention);
	}
}

