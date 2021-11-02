package com.softicar.platform.core.module.date.weekday;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGWeekdayEnum implements IDbEnumTableRowEnum<AGWeekdayEnum, AGWeekday> {

	MONDAY(1, "Monday"),
	TUESDAY(2, "Tuesday"),
	WEDNESDAY(3, "Wednesday"),
	THURSDAY(4, "Thursday"),
	FRIDAY(5, "Friday"),
	SATURDAY(6, "Saturday"),
	SUNDAY(7, "Sunday"),
	//
	;

	private Integer id;
	private String name;

	private AGWeekdayEnum(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public DbEnumTable<AGWeekday, AGWeekdayEnum> getTable() {

		return AGWeekday.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGWeekday> consumer) {

		consumer.consumeFieldValue(AGWeekday.ID, id);
		consumer.consumeFieldValue(AGWeekday.NAME, name);
	}
}

