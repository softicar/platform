package com.softicar.platform.db.runtime.query.sorters;

import com.softicar.platform.db.runtime.select.IDbSqlBuilder;

public interface IDbQuerySorter {

	void buildExpression(IDbSqlBuilder builder);
}
