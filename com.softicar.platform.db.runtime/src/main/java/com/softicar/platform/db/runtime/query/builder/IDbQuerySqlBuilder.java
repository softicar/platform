package com.softicar.platform.db.runtime.query.builder;

import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.query.filters.IDbQueryFilterList;
import com.softicar.platform.db.runtime.query.sorters.IDbQuerySorter;
import com.softicar.platform.db.runtime.select.IDbSqlBuilder;
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import java.util.List;

public interface IDbQuerySqlBuilder extends IDbSqlBuilder {

	void setFilters(IDbQueryFilterList<?> filter);

	void setSorters(List<IDbQuerySorter> sorters);

	void setLimit(int limit);

	void setOffset(int offset);

	IDbSqlSelect buildSelect();

	IDbSqlSelect buildCountAllSelect();

	IDbSqlSelect buildDistinctColumnValuesSelect(IDbQueryColumn<?, ?> column, int limit);
}
