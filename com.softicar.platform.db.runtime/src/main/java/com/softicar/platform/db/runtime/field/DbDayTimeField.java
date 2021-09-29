package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbDayTimeField<R> extends AbstractDbPrimitiveField<R, DayTime, DbDayTimeField<R>> implements IDbDayTimeField<R> {

	private boolean defaultNow;
	private boolean onUpdateNow;
	private boolean timestamp;

	public DbDayTimeField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, DayTime> getter, BiConsumer<? super R, DayTime> setter) {

		super(builder, SqlFieldType.DAY_TIME, name, getter, setter);

		this.defaultNow = false;
		this.onUpdateNow = false;
		this.timestamp = false;
	}

	@Override
	public boolean hasDefault() {

		return defaultNow || super.hasDefault();
	}

	@Override
	public boolean isDefaultNow() {

		return defaultNow;
	}

	@Override
	public boolean isOnUpdateNow() {

		return onUpdateNow;
	}

	@Override
	public boolean isTimestamp() {

		return timestamp;
	}

	@Override
	public DayTime getDefault() {

		if (defaultNow) {
			return DayTime.now();
		} else {
			return super.getDefault();
		}
	}

	// ------------------------------ setters ------------------------------ //

	public DbDayTimeField<R> setDefaultNow() {

		this.defaultNow = true;
		return this;
	}

	public DbDayTimeField<R> setOnUpdateNow() {

		this.onUpdateNow = true;
		return this;
	}

	public DbDayTimeField<R> setTimestamp() {

		this.timestamp = true;
		return this;
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbDayTimeField<R> getThis() {

		return this;
	}
}
