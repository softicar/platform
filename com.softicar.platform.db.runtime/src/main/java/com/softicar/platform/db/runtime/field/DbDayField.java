package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbDayField<R> extends AbstractDbPrimitiveField<R, Day, DbDayField<R>> implements IDbDayField<R> {

	private boolean defaultToday;

	public DbDayField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, Day> getter, BiConsumer<? super R, Day> setter) {

		super(builder, SqlFieldType.DAY, name, getter, setter);

		this.defaultToday = false;
	}

	@Override
	public boolean hasDefault() {

		return defaultToday || super.hasDefault();
	}

	@Override
	public boolean isDefaultToday() {

		return defaultToday;
	}

	@Override
	public Day getDefault() {

		if (defaultToday) {
			return Day.today();
		} else {
			return super.getDefault();
		}
	}

	// ------------------------------ setters ------------------------------ //

	public DbDayField<R> setDefaultToday() {

		this.defaultToday = true;
		return this;
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbDayField<R> getThis() {

		return this;
	}
}
