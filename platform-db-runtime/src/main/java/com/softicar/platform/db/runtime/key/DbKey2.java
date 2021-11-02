package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.structure.key.DbKeyType;
import java.util.Arrays;
import java.util.List;

public class DbKey2<R, V0, V1> extends AbstractDbKey<R> implements IDbKey2<R, V0, V1> {

	private final IDbField<R, V0> field0;
	private final IDbField<R, V1> field1;

	public DbKey2(DbKeyType type, String name, IDbField<R, V0> field0, IDbField<R, V1> field1) {

		super(type, name);

		this.field0 = field0;
		this.field1 = field1;
	}

	@Override
	public List<IDbField<R, ?>> getFields() {

		return Arrays.asList(field0, field1);
	}

	@Override
	public IDbField<R, V0> getField0() {

		return field0;
	}

	@Override
	public IDbField<R, V1> getField1() {

		return field1;
	}
}
