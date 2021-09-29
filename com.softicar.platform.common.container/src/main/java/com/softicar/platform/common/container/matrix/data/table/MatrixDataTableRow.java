package com.softicar.platform.common.container.matrix.data.table;

import java.util.Map;

public class MatrixDataTableRow<R, C, V> {

	private final R row;
	private final Map<C, V> valueMap;

	public MatrixDataTableRow(R row, Map<C, V> valueMap) {

		this.row = row;
		this.valueMap = valueMap;
	}

	public R getRow() {

		return row;
	}

	public V getValue(C col) {

		return valueMap.get(col);
	}
}
