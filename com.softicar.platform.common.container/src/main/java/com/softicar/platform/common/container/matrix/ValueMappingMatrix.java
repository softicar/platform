package com.softicar.platform.common.container.matrix;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ValueMappingMatrix<R, C, U, V> implements IImmutableMatrix<R, C, V> {

	private final IImmutableMatrix<R, C, U> matrix;
	private final Function<U, V> valueMapper;

	public ValueMappingMatrix(IImmutableMatrix<R, C, U> matrix, Function<U, V> valueMapper) {

		this.matrix = matrix;
		this.valueMapper = valueMapper;
	}

	@Override
	public Iterable<IMatrixCell<R, C, V>> getMatrixCells() {

		return StreamSupport
			.stream(matrix.getMatrixCells().spliterator(), false)//
			.map(MatrixCell::new)
			.collect(Collectors.toList());
	}

	@Override
	public IImmutableMatrix<R, C, V> clone() {

		return new ValueMappingMatrix<>(matrix.clone(), valueMapper);
	}

	@Override
	public V getValue(R row, C column) {

		return valueMapper.apply(matrix.getValue(row, column));
	}

	@Override
	public V getTotalValue(R row) {

		return valueMapper.apply(matrix.getTotalValue(row));
	}

	@Override
	public V getTotalColumnValue(C column) {

		return valueMapper.apply(matrix.getTotalColumnValue(column));
	}

	@Override
	public boolean isSet(R row, C column) {

		return matrix.isSet(row, column);
	}

	@Override
	public V getDefaultValue() {

		return valueMapper.apply(matrix.getDefaultValue());
	}

	@Override
	public SortedSet<R> getRows() {

		return matrix.getRows();
	}

	@Override
	public boolean containsRow(R row) {

		return matrix.containsRow(row);
	}

	@Override
	public R getFirstRow() {

		return matrix.getFirstRow();
	}

	@Override
	public R getLastRow() {

		return matrix.getLastRow();
	}

	@Override
	public int getRowCount() {

		return matrix.getRowCount();
	}

	@Override
	public Map<C, V> getRowMap(R row) {

		return matrix//
			.getRowMap(row)
			.entrySet()
			.stream()
			.collect(Collectors.toMap(Entry::getKey, entry -> valueMapper.apply(entry.getValue())));
	}

	@Override
	public SortedSet<C> getColumns() {

		return matrix.getColumns();
	}

	@Override
	public boolean containsColumn(C column) {

		return matrix.containsColumn(column);
	}

	@Override
	public C getFirstColumn() {

		return matrix.getFirstColumn();
	}

	@Override
	public C getLastColumn() {

		return matrix.getLastColumn();
	}

	@Override
	public int getColumnCount() {

		return matrix.getColumnCount();
	}

	@Override
	public Collection<C> getColumnsWithNonDefaultValue(R row) {

		return matrix.getColumnsWithNonDefaultValue(row);
	}

	private class MatrixCell implements IMatrixCell<R, C, V> {

		private final IMatrixCell<R, C, U> matrixCell;

		public MatrixCell(IMatrixCell<R, C, U> matrixCell) {

			this.matrixCell = matrixCell;
		}

		@Override
		public R getRow() {

			return matrixCell.getRow();
		}

		@Override
		public C getColumn() {

			return matrixCell.getColumn();
		}

		@Override
		public V getValue() {

			return valueMapper.apply(matrixCell.getValue());
		}
	}
}
