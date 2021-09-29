package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the structure of an {@link IDataTable}.
 * <p>
 * The table structure primarily involves the definition of the table columns
 * represented by {@link IDataTableColumn}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class InMemoryDataTableStructure<R> {

	private final List<IDataTableColumn<R, ?>> columns = new ArrayList<>();

	/**
	 * Initiates the creation of a new table column.
	 * <p>
	 * If the given class implements the {@link Comparable} interface, this will
	 * automatically use it for comparison.
	 *
	 * @param valueClass
	 *            the value class of the column
	 * @return a column builder instance
	 */
	public <V> InMemoryDataTableColumnBuilder<R, V>.GetterSetter newColumn(Class<V> valueClass) {

		return new InMemoryDataTableColumnBuilder<>(this, valueClass).new GetterSetter();
	}

	public List<IDataTableColumn<R, ?>> getTableColumns() {

		return columns;
	}

	protected <V> IDataTableColumn<R, V> addColumn(InMemoryDataTableColumnBuilder<R, V> columnBuilder) {

		InMemoryDataTableColumn<R, V> column = new InMemoryDataTableColumn<>(columnBuilder);
		columns.add(column);
		return column;
	}
}
