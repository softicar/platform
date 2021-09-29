package com.softicar.platform.common.container.matrix.simple;

import com.softicar.platform.common.container.iterator.AbstractIteratorAdapter;
import com.softicar.platform.common.container.matrix.IMatrixCell;
import java.util.Iterator;
import java.util.Map;

class SimpleMatrixCellIterable<R, C, V> extends AbstractIteratorAdapter<IMatrixCell<R, C, V>> {

	private final Iterator<Map.Entry<R, Map<C, V>>> rowIterator;
	private Iterator<Map.Entry<C, V>> columnIterator;
	private Map.Entry<R, Map<C, V>> row;

	public SimpleMatrixCellIterable(Map<R, Map<C, V>> matrixValues) {

		this.rowIterator = matrixValues.entrySet().iterator();
	}

	@Override
	protected IMatrixCell<R, C, V> fetchNext() {

		if (columnIterator == null || !columnIterator.hasNext()) {
			if (!rowIterator.hasNext()) {
				setFinished();
				return null;
			}

			row = rowIterator.next();
			columnIterator = row.getValue().entrySet().iterator();
			return fetchNext();
		} else {
			Map.Entry<C, V> column = columnIterator.next();
			return new SimpleMatrixCell<>(row.getKey(), column.getKey(), column.getValue());
		}
	}
}
