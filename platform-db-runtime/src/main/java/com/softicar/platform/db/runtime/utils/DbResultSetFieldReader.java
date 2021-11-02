package com.softicar.platform.db.runtime.utils;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.container.tuple.Tuple3;
import com.softicar.platform.common.container.tuple.Tuple4;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.field.IDbField;

public class DbResultSetFieldReader {

	private final DbResultSet resultSet;
	private final int baseIndex;

	public DbResultSetFieldReader(DbResultSet resultSet, int baseIndex) {

		this.resultSet = resultSet;
		this.baseIndex = baseIndex;
	}

	public <V> V read(IDbField<?, V> field) {

		return field.getValueType().getValue(resultSet, baseIndex + field.getIndex());
	}

	public <V0, V1> Tuple2<V0, V1> read(IDbField<?, V0> field0, IDbField<?, V1> field1) {

		return new Tuple2<>(read(field0), read(field1));
	}

	public <V0, V1, V2> Tuple3<V0, V1, V2> read(IDbField<?, V0> field0, IDbField<?, V1> field1, IDbField<?, V2> field2) {

		return new Tuple3<>(read(field0), read(field1), read(field2));
	}

	public <V0, V1, V2, V3> Tuple4<V0, V1, V2, V3> read(IDbField<?, V0> field0, IDbField<?, V1> field1, IDbField<?, V2> field2, IDbField<?, V3> field3) {

		return new Tuple4<>(read(field0), read(field1), read(field2), read(field3));
	}
}
