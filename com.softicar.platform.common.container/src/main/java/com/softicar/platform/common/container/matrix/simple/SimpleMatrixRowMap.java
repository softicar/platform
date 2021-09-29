package com.softicar.platform.common.container.matrix.simple;

import com.softicar.platform.common.core.utils.CastUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

class SimpleMatrixRowMap<R, C, V> implements Map<C, V> {

	private final SimpleMatrix<R, C, V> matrix;
	private final R row;

	public SimpleMatrixRowMap(SimpleMatrix<R, C, V> matrix, R row) {

		this.matrix = matrix;
		this.row = row;
	}

	@Override
	public int size() {

		return matrix.getColumnCount();
	}

	@Override
	public boolean isEmpty() {

		return matrix.getColumnCount() != 0;
	}

	@Override
	public boolean containsKey(Object column) {

		return matrix.containsColumn(CastUtils.<C> cast(column));
	}

	@Override
	public boolean containsValue(Object value) {

		throw new UnsupportedOperationException();
	}

	@Override
	public V get(Object column) {

		return matrix.getValue(row, CastUtils.<C> cast(column));
	}

	@Override
	public V put(C column, V value) {

		V previous = matrix.getValue(row, column);
		matrix.setValue(row, column, value);
		return previous;
	}

	@Override
	public V remove(Object key) {

		V value = get(key);
		matrix.removeValue(row, CastUtils.<C> cast(key));
		return value;
	}

	@Override
	public void putAll(Map<? extends C, ? extends V> m) {

		for (Entry<? extends C, ? extends V> entry: m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clear() {

		throw new UnsupportedOperationException();
	}

	@Override
	public Set<C> keySet() {

		return matrix.getColumns();
	}

	@Override
	public Collection<V> values() {

		Collection<V> values = new ArrayList<>();
		for (C column: keySet()) {
			values.add(matrix.getValue(row, column));
		}
		return values;
	}

	@Override
	public Set<Entry<C, V>> entrySet() {

		throw new UnsupportedOperationException();
	}
}
