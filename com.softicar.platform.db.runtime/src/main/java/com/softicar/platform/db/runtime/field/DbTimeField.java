package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbTimeField<R> extends AbstractDbPrimitiveField<R, Time, DbTimeField<R>> implements IDbTimeField<R> {

	public DbTimeField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, Time> getter, BiConsumer<? super R, Time> setter) {

		super(builder, SqlFieldType.TIME, name, getter, setter);
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbTimeField<R> getThis() {

		return this;
	}
}
