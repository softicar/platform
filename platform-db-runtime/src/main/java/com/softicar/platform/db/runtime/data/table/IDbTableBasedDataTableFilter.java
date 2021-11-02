package com.softicar.platform.db.runtime.data.table;

import com.softicar.platform.common.container.data.table.IDataTableFilter;
import com.softicar.platform.db.sql.ISqlBooleanExpression;

public interface IDbTableBasedDataTableFilter<R> extends IDataTableFilter {

	ISqlBooleanExpression<R> getExpression();
}
