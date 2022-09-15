package com.softicar.platform.core.module.date.weekday;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGWeekdayEnum implements IDbEnumTableRowEnum<AGWeekdayEnum, AGWeekday>, IDisplayable {

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
	public IDisplayString toDisplay() {

		switch (this) {
		case MONDAY:
			return CoreI18n.MONDAY;
		case TUESDAY:
			return CoreI18n.TUESDAY;
		case WEDNESDAY:
			return CoreI18n.WEDNESDAY;
		case THURSDAY:
			return CoreI18n.THURSDAY;
		case FRIDAY:
			return CoreI18n.FRIDAY;
		case SATURDAY:
			return CoreI18n.SATURDAY;
		case SUNDAY:
			return CoreI18n.SUNDAY;
		}

		throw new IllegalArgumentException("Unknown enumerator: " + name());
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGWeekday> consumer) {

		consumer.consumeFieldValue(AGWeekday.ID, id);
		consumer.consumeFieldValue(AGWeekday.NAME, name);
	}
}

