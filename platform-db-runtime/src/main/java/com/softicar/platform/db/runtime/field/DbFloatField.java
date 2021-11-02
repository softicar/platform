package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbFloatField<R> extends AbstractDbPrimitiveField<R, Float, DbFloatField<R>> implements IDbFloatField<R> {

	public DbFloatField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, Float> getter, BiConsumer<? super R, Float> setter) {

		super(builder, SqlFieldType.FLOAT, name, getter, setter);
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbFloatField<R> getThis() {

		return this;
	}
}
