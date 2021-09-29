package com.softicar.platform.db.runtime.table.statements;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.sql.operations.SqlParameterBuildable;
import com.softicar.platform.db.sql.operations.SqlTuple;

public class DbTableKeyValueSqlTuple<R extends IDbTableRow<R, P>, P> extends SqlTuple {

	public DbTableKeyValueSqlTuple(IDbTableKey<R, P> key, P keyValue) {

		for (IDbField<R, ?> field: key.getFields()) {
			add(new SqlParameterBuildable(key.getValue(field, keyValue)));
		}
	}
}
