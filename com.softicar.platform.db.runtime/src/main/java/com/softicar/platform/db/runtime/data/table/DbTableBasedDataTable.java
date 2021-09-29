package com.softicar.platform.db.runtime.data.table;

import com.softicar.platform.common.container.data.table.DataTableFilterListOperator;
import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.container.data.table.IDataTableSorterList;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DbTableBasedDataTable<R> implements IDataTable<R> {

	private final IDbTable<R, ?> table;
	private final List<DbFieldBasedDataTableColumn<R, ?>> columns;
	private final Map<IDbField<R, ?>, DbFieldBasedDataTableColumn<R, ?>> fieldToColumnMap;
	private final DbTableBasedDataTableFilterList<R> filterList;
	private final DbTableBasedDataTableSorterList<R> sorterList;

	public DbTableBasedDataTable(IDbTable<R, ?> table) {

		this.table = table;
		this.columns = table//
			.getAllFields()
			.stream()
			.map((IDbField<R, ?> field) -> new DbFieldBasedDataTableColumn<>(field))
			.collect(Collectors.toList());
		this.fieldToColumnMap = columns//
			.stream()
			.collect(Collectors.toMap(column -> column.getField(), column -> column));
		this.filterList = new DbTableBasedDataTableFilterList<>(this, DataTableFilterListOperator.AND);
		this.sorterList = new DbTableBasedDataTableSorterList<>(this);
	}

	@Override
	public DataTableIdentifier getIdentifier() {

		return new DataTableIdentifier(table.getFullName().getCanonicalName());
	}

	@Override
	public List<? extends IDataTableColumn<R, ?>> getTableColumns() {

		return columns;
	}

	@Override
	public <V> List<V> getDistinctColumnValues(IDataTableColumn<R, V> column, int limit) {

		IDbField<R, V> field = getField(column);
		return Sql//
			.from(table)
			.select(field)
			.where(filterList.getExpression())
			.groupBy(field)
			.list(0, limit);
	}

	@Override
	public Iterator<R> iterator(int offset, int limit) {

		ISqlSelect<R> select = table//
			.createSelect()
			.where(filterList.getExpression());
		sorterList.applyTo(select);
		return select.iterator(offset, limit);
	}

	@Override
	public int count() {

		return table//
			.createSelect()
			.where(filterList.getExpression())
			.count();
	}

	@Override
	public IDataTableFilterList<R> getFilters() {

		return filterList;
	}

	@Override
	public IDataTableSorterList<R> getSorters() {

		return sorterList;
	}

	public <V> IDataTableColumn<R, V> getColumn(IDbField<R, V> field) {

		return CastUtils.cast(fieldToColumnMap.get(field));
	}

	public <V> IDbField<R, V> getField(IDataTableColumn<R, V> column) {

		DbFieldBasedDataTableColumn<R, V> fieldColumn = (DbFieldBasedDataTableColumn<R, V>) column;
		return fieldColumn.getField();
	}
}
