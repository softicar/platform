package com.softicar.platform.db.runtime.key;

import com.softicar.platform.common.container.tuple.Tuple3;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.utils.DbResultSetFieldReader;

public class DbTableKey3<R, V0, V1, V2> extends AbstractDbCompoundKey<R, Tuple3<V0, V1, V2>> {

	private final IDbField<R, V0> field0;
	private final IDbField<R, V1> field1;
	private final IDbField<R, V2> field2;

	public DbTableKey3(IDbField<R, V0> field0, IDbField<R, V1> field1, IDbField<R, V2> field2) {

		this.field0 = field0;
		this.field1 = field1;
		this.field2 = field2;
		this.addField(field0, Tuple3::get0);
		this.addField(field1, Tuple3::get1);
		this.addField(field2, Tuple3::get2);
	}

	@Override
	public Tuple3<V0, V1, V2> getFromRow(R row) {

		return new Tuple3<>(field0.getValueDirectly(row), field1.getValueDirectly(row), field2.getValueDirectly(row));
	}

	@Override
	public Tuple3<V0, V1, V2> getFromResultSet(DbResultSet resultSet, int baseIndex) {

		return new DbResultSetFieldReader(resultSet, baseIndex).read(field0, field1, field2);
	}
}
