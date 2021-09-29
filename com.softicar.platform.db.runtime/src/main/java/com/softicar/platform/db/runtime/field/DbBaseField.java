package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.db.runtime.entity.IDbEntityTable;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbBaseField<R, B extends IDbEntity<B, P>, P> extends AbstractDbForeignRowField<R, B, P, DbBaseField<R, B, P>> implements IDbBaseField<R, B, P> {

	private final IDbEntityTable<B, P> baseTable;

	public DbBaseField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, B> getter, BiConsumer<? super R, B> setter,
			IDbEntityTable<B, P> baseTable) {

		super(builder, name, getter, setter, baseTable.getPrimaryKeyField());

		this.baseTable = baseTable;
	}

	@Override
	public B getDefault() {

		return baseTable.createObject();
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbBaseField<R, B, P> getThis() {

		return this;
	}
}
