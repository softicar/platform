package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbDoubleField<R> extends AbstractDbPrimitiveField<R, Double, DbDoubleField<R>> implements IDbDoubleField<R> {

	public DbDoubleField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, Double> getter, BiConsumer<? super R, Double> setter) {

		super(builder, SqlFieldType.DOUBLE, name, getter, setter);
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbDoubleField<R> getThis() {

		return this;
	}
}
