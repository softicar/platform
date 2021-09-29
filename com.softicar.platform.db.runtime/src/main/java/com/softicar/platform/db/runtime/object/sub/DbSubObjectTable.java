package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.db.runtime.entity.IDbEntityTable;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.table.DbTable;
import java.util.function.Consumer;

public class DbSubObjectTable<R extends IDbSubObject<R, B>, B extends IDbEntity<B, P>, P> extends DbTable<R, B> implements IDbSubObjectTable<R, B, P> {

	private final IDbEntityTable<B, P> baseTable;
	private final IDbBaseField<R, B, P> baseField;

	public DbSubObjectTable(IDbSubObjectTableBuilder<R, B, P> builder) {

		super(builder);

		this.baseTable = builder.getBaseTable();
		this.baseField = builder.getBaseField();
	}

	@Override
	public final R getStub(Integer id) {

		return getStub(baseTable.getStub(id));
	}

	@Override
	public R createObject() {

		return getRowFactory().get();
	}

	@Override
	public R createObject(B base) {

		return getRowFactory()//
			.get()
			.initializer()
			.initializeWithBase(base);
	}

	@Override
	public IDbBaseField<R, B, P> getPrimaryKeyField() {

		return baseField;
	}

	@Override
	public IDbEntityTable<B, P> getBaseTable() {

		return baseTable;
	}

	@Override
	public void ifSubObjectTable(Consumer<IDbSubObjectTable<? super R, ?, ?>> consumer) {

		consumer.accept(this);
	}
}
