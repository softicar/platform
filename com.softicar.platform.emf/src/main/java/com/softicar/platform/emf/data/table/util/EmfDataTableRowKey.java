package com.softicar.platform.emf.data.table.util;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class EmfDataTableRowKey<R> {

	private final Collection<? extends IDataTableColumn<R, ?>> columns;
	private final R row;
	private final int hashCode;

	public EmfDataTableRowKey(Collection<? extends IDataTableColumn<R, ?>> columns, R row) {

		this.columns = columns;
		this.row = row;
		this.hashCode = computeHashCode();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object other) {

		if (other instanceof EmfDataTableRowKey) {
			EmfDataTableRowKey<R> otherKey = (EmfDataTableRowKey<R>) other;
			return equalValues(row, otherKey.row);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return hashCode;
	}

	private boolean equalValues(R a, R b) {

		for (IDataTableColumn<R, ?> column: columns) {
			if (!Objects.equals(column.getValue(a), column.getValue(b))) {
				return false;
			}
		}
		return true;
	}

	private int computeHashCode() {

		int index = 0;
		Object[] values = new Object[columns.size()];
		for (IDataTableColumn<R, ?> column: columns) {
			values[index] = column.getValue(row);
			index++;
		}
		return Arrays.hashCode(values);
	}
}
