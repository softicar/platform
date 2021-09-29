package com.softicar.platform.db.runtime.key;

import com.softicar.platform.common.container.tuple.Tuple4;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.utils.DbResultSetFieldReader;

public class DbTableKey4<R, V0, V1, V2, V3> extends AbstractDbCompoundKey<R, Tuple4<V0, V1, V2, V3>> {

	private final IDbField<R, V0> field0;
	private final IDbField<R, V1> field1;
	private final IDbField<R, V2> field2;
	private final IDbField<R, V3> field3;

	public DbTableKey4(IDbField<R, V0> field0, IDbField<R, V1> field1, IDbField<R, V2> field2, IDbField<R, V3> field3) {

		this.field0 = field0;
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
		this.addField(field0, Tuple4::get0);
		this.addField(field1, Tuple4::get1);
		this.addField(field2, Tuple4::get2);
		this.addField(field3, Tuple4::get3);
	}

	@Override
	public Tuple4<V0, V1, V2, V3> getFromRow(R row) {

		return new Tuple4<>(//
			field0.getValueDirectly(row),
			field1.getValueDirectly(row),
			field2.getValueDirectly(row),
			field3.getValueDirectly(row));
	}

	@Override
	public Tuple4<V0, V1, V2, V3> getFromResultSet(DbResultSet resultSet, int baseIndex) {

		return new DbResultSetFieldReader(resultSet, baseIndex).read(field0, field1, field2, field3);
	}
}
