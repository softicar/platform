package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbBooleanField<R> extends AbstractDbPrimitiveField<R, Boolean, DbBooleanField<R>> implements IDbBooleanField<R> {

	public DbBooleanField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, Boolean> getter, BiConsumer<? super R, Boolean> setter) {

		super(builder, SqlFieldType.BOOLEAN, name, getter, setter);
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbBooleanField<R> getThis() {

		return this;
	}
}
