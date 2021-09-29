package com.softicar.platform.db.runtime.data.table;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableSorterList;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import java.util.ArrayList;
import java.util.List;

public class DbTableBasedDataTableSorterList<R> implements IDataTableSorterList<R> {

	private final DbTableBasedDataTable<R> table;
	private final List<Sorter> sorters;

	public DbTableBasedDataTableSorterList(DbTableBasedDataTable<R> table) {

		this.table = table;
		this.sorters = new ArrayList<>();
	}

	@Override
	public void clear() {

		sorters.clear();
	}

	@Override
	public boolean isEmpty() {

		return sorters.isEmpty();
	}

	@Override
	public void addSorter(IDataTableColumn<R, ?> column, OrderDirection orderDirection) {

		sorters.add(new Sorter(column, orderDirection));
	}

	protected void applyTo(ISqlSelect<R> select) {

		sorters//
			.stream()
			.forEach(sorter -> sorter.applyTo(select));
	}

	private class Sorter {

		private final IDbField<R, ?> field;
		private final OrderDirection orderDirection;

		public Sorter(IDataTableColumn<R, ?> column, OrderDirection orderDirection) {

			this.field = table.getField(column);
			this.orderDirection = orderDirection;
		}

		protected void applyTo(ISqlSelect<R> select) {

			if (orderDirection == OrderDirection.ASCENDING) {
				select.orderBy(field);
			} else {
				select.orderDescendingBy(field);
			}
		}
	}
}
