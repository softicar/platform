package com.softicar.platform.core.module.log.level;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGLogLevelEnum implements IDbEnumTableRowEnum<AGLogLevelEnum, AGLogLevel>, IDisplayable {

	PANIC(1, "PANIC"),
	ERROR(2, "ERROR"),
	WARNING(3, "WARNING"),
	INFO(4, "INFO"),
	VERBOSE(5, "VERBOSE"),
	//
	;

	private Integer id;
	private String name;

	private AGLogLevelEnum(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public DbEnumTable<AGLogLevel, AGLogLevelEnum> getTable() {

		return AGLogLevel.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public IDisplayString toDisplay() {

		switch (this) {
		case PANIC:
			return CoreI18n.PANIC;
		case ERROR:
			return CoreI18n.ERROR;
		case WARNING:
			return CoreI18n.WARNING;
		case INFO:
			return CoreI18n.INFO;
		case VERBOSE:
			return CoreI18n.VERBOSE;
		}

		throw new IllegalArgumentException("Unknown enumerator: " + name());
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGLogLevel> consumer) {

		consumer.consumeFieldValue(AGLogLevel.ID, id);
		consumer.consumeFieldValue(AGLogLevel.NAME, name);
	}
}

