package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbForeignRowField<R, F extends IDbTableRow<F, FP>, FP> extends AbstractDbForeignRowField<R, F, FP, DbForeignRowField<R, F, FP>> {

	public DbForeignRowField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, F> getter, BiConsumer<? super R, F> setter,
			IDbField<F, FP> targetField) {

		super(builder, name, getter, setter, targetField);
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbForeignRowField<R, F, FP> getThis() {

		return this;
	}
}
