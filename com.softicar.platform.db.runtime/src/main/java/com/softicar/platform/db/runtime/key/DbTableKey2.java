package com.softicar.platform.db.runtime.key;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.utils.DbResultSetFieldReader;

public class DbTableKey2<R, V0, V1> extends AbstractDbCompoundKey<R, Tuple2<V0, V1>> {

	private final IDbField<R, V0> field0;
	private final IDbField<R, V1> field1;

	public DbTableKey2(IDbField<R, V0> field0, IDbField<R, V1> field1) {

		this.field0 = field0;
		this.field1 = field1;
		this.addField(field0, Tuple2::get0);
		this.addField(field1, Tuple2::get1);
	}

	@Override
	public Tuple2<V0, V1> getFromRow(R row) {

		return new Tuple2<>(field0.getValueDirectly(row), field1.getValueDirectly(row));
	}

	@Override
	public Tuple2<V0, V1> getFromResultSet(DbResultSet resultSet, int baseIndex) {

		return new DbResultSetFieldReader(resultSet, baseIndex).read(field0, field1);
	}
}
