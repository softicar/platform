package com.softicar.platform.core.module.event.severity;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGSystemEventSeverityEnum implements IDbEnumTableRowEnum<AGSystemEventSeverityEnum, AGSystemEventSeverity>, IDisplayable {

	ERROR(1, "ERROR", true),
	WARNING(2, "WARNING", true),
	INFORMATION(3, "INFORMATION", false),
	//
	;

	private Integer id;
	private String name;
	private Boolean needsConfirmation;

	private AGSystemEventSeverityEnum(Integer id, String name, Boolean needsConfirmation) {

		this.id = id;
		this.name = name;
		this.needsConfirmation = needsConfirmation;
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
	public IDisplayString toDisplay() {

		switch (this) {
		case ERROR:
			return CoreI18n.ERROR;
		case WARNING:
			return CoreI18n.WARNING;
		case INFORMATION:
			return CoreI18n.INFORMATION;
		}

		throw new IllegalArgumentException("Unknown enumerator: " + name());
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGSystemEventSeverity> consumer) {

		consumer.consumeFieldValue(AGSystemEventSeverity.ID, id);
		consumer.consumeFieldValue(AGSystemEventSeverity.NAME, name);
		consumer.consumeFieldValue(AGSystemEventSeverity.NEEDS_CONFIRMATION, needsConfirmation);
	}
}

