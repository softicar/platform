package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.structure.key.DbKeyType;
import java.util.Arrays;
import java.util.List;

public class DbKey4<R, V0, V1, V2, V3> extends AbstractDbKey<R> implements IDbKey4<R, V0, V1, V2, V3> {

	private final IDbField<R, V0> field0;
	private final IDbField<R, V1> field1;
	private final IDbField<R, V2> field2;
	private final IDbField<R, V3> field3;

	public DbKey4(DbKeyType type, String name, IDbField<R, V0> field0, IDbField<R, V1> field1, IDbField<R, V2> field2, IDbField<R, V3> field3) {

		super(type, name);

		this.field0 = field0;
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}

	@Override
	public List<IDbField<R, ?>> getFields() {

		return Arrays.asList(field0, field1, field2, field3);
	}

	@Override
	public IDbField<R, V0> getField0() {

		return field0;
	}

	@Override
	public IDbField<R, V1> getField1() {

		return field1;
	}

	@Override
	public IDbField<R, V2> getField2() {

		return field2;
	}

	@Override
	public IDbField<R, V3> getField3() {

		return field3;
	}
}
