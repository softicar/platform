package com.softicar.platform.core.module.log.level;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGLogLevelEnum implements IDbEnumTableRowEnum<AGLogLevelEnum, AGLogLevel> {

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
	public void setFields(ISqlFieldValueConsumer<AGLogLevel> consumer) {

		consumer.consumeFieldValue(AGLogLevel.ID, id);
		consumer.consumeFieldValue(AGLogLevel.NAME, name);
	}
}

