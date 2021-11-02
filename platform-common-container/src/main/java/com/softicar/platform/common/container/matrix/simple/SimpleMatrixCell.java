package com.softicar.platform.common.container.matrix.simple;

import com.softicar.platform.common.container.matrix.IMatrixCell;
import com.softicar.platform.common.core.utils.CastUtils;
import java.util.Objects;

class SimpleMatrixCell<R, C, V> implements IMatrixCell<R, C, V> {

	private final R row;
	private final C column;
	private final V value;

	public SimpleMatrixCell(R row, C column, V value) {

		this.row = row;
		this.column = column;
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof IMatrixCell<?, ?, ?>) {
			IMatrixCell<R, C, V> otherCell = CastUtils.cast(other);
			return Objects.equals(getRow(), otherCell.getRow()) && Objects.equals(getColumn(), otherCell.getColumn())
					&& Objects.equals(getValue(), otherCell.getValue());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(row, column, value);
	}

	@Override
	public String toString() {

		return new StringBuilder()//
			.append(getRow())
			.append(", ")
			.append(getColumn())
			.append(", ")
			.append(getValue())
			.toString();
	}

	@Override
	public C getColumn() {

		return column;
	}

	@Override
	public R getRow() {

		return row;
	}

	@Override
	public V getValue() {

		return value;
	}
}
