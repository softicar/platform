package com.softicar.platform.db.runtime.query.filters;

import com.softicar.platform.common.container.data.table.IDataTableFilter;
import com.softicar.platform.db.runtime.select.IDbSqlBuilder;

public interface IDbQueryFilter extends IDataTableFilter {

	void buildCondition(IDbSqlBuilder builder);
}
