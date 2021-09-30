package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.runtime.utils.DbObjectList;
import com.softicar.platform.db.runtime.utils.IDbObjectList;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbForeignField<R, F extends IDbObject<F>> extends AbstractDbForeignRowField<R, F, Integer, DbForeignField<R, F>> implements IDbForeignField<R, F> {

	public DbForeignField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, F> getter, BiConsumer<? super R, F> setter,
			IDbPrimitiveField<F, Integer> targetField) {

		super(builder, name, getter, setter, targetField);
	}

	@Override
	public Integer getValueId(R object) {

		F value = getValue(object);
		return value != null? value.getId() : null;
	}

	@Override
	public IDbObjectList<F> prefetch(Iterable<? extends R> objects) {

		return prefetch(objects, DbObjectList::new);
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbForeignField<R, F> getThis() {

		return this;
	}
}
