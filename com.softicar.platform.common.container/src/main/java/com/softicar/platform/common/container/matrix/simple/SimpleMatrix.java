package com.softicar.platform.common.container.matrix.simple;

import com.softicar.platform.common.container.matrix.AsciiTable;
import com.softicar.platform.common.container.matrix.IMatrix;
import com.softicar.platform.common.container.matrix.IMatrixCell;
import com.softicar.platform.common.container.matrix.IMatrixTraits;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * A basic implementation of the {@link IMatrix} interface.
 *
 * @author Oliver Richers
 */
public class SimpleMatrix<R, C, V> implements IMatrix<R, C, V> {

	private final IMatrixTraits<V> traits;
	private final SortedSet<R> rows;
	private final SortedSet<C> columns;
	private final SortedMap<R, Map<C, V>> values;

	/**
	 * Constructs the matrix with the specified traits.
	 */
	public SimpleMatrix(IMatrixTraits<V> traits) {

		this.traits = traits;
		this.rows = new TreeSet<>();
		this.columns = new TreeSet<>();
		this.values = new TreeMap<>();
	}

	/**
	 * Constructs the matrix with the specified traits and rowComparator.
	 */
	public SimpleMatrix(IMatrixTraits<V> traits, Comparator<? super R> rowComparator, Comparator<? super C> colComparator) {

		this.traits = traits;
		if (rowComparator == null) {
			this.rows = new TreeSet<>();
		} else {
			this.rows = new TreeSet<>(rowComparator);
		}
		if (colComparator == null) {
			this.columns = new TreeSet<>();
		} else {
			this.columns = new TreeSet<>(colComparator);
		}
		this.values = new TreeMap<>(rowComparator);
	}

	public SimpleMatrix(SimpleMatrix<R, C, V> other) {

		this(other.traits, other.rows.comparator(), other.columns.comparator());

		for (Map.Entry<R, Map<C, V>> row: other.values.entrySet()) {
			for (Map.Entry<C, V> col: row.getValue().entrySet()) {
				this.setValue(row.getKey(), col.getKey(), col.getValue());
			}
		}
	}

	@Override
	public IMatrix<R, C, V> clone() {

		return new SimpleMatrix<>(this);
	}

	@Override
	public Iterable<IMatrixCell<R, C, V>> getMatrixCells() {

		return new SimpleMatrixCellIterable<>(values);
	}

	@Override
	public V getValue(R row, C column) {

		Map<C, V> rowValues = values.get(row);
		if (rowValues != null) {
			V value = rowValues.get(column);
			if (value != null) {
				return value;
			} else {
				return traits.getDefaultValue();
			}
		} else {
			return traits.getDefaultValue();
		}
	}

	@Override
	public V getTotalValue(R row) {

		V result = getTraits().getDefaultValue();
		for (C column: getColumns()) {
			result = getTraits().plus(result, getValue(row, column));
		}
		return result;
	}

	@Override
	public V getTotalColumnValue(C column) {

		V result = getTraits().getDefaultValue();
		for (R row: getRows()) {
			result = getTraits().plus(result, getValue(row, column));
		}
		return result;
	}

	@Override
	public void setValue(R row, C column, V value) {

		Map<C, V> rowValues = values.get(row);
		if (rowValues == null) {
			values.put(row, rowValues = new TreeMap<>(columns.comparator()));
		}
		rowValues.put(column, value);

		rows.add(row);
		columns.add(column);
	}

	@Override
	public void addValue(R row, C column, V value) {

		setValue(row, column, traits.plus(getValue(row, column), value));
	}

	@Override
	public void removeValue(R row, C column) {

		Map<C, V> rowValues = values.get(row);
		if (rowValues != null) {
			rowValues.remove(column);
			if (rowValues.isEmpty()) {
				values.remove(row);
			}
		}
	}

	@Override
	public V getDefaultValue() {

		return traits.getDefaultValue();
	}

	@Override
	public boolean isSet(R row, C column) {

		Map<C, V> rowValues = values.get(row);
		return rowValues != null && rowValues.get(column) != null;
	}

	public IMatrixTraits<V> getTraits() {

		return traits;
	}

	@Override
	public SortedSet<R> getRows() {

		return rows;
	}

	public Collection<R> getRowsWithNonDefaultValue(C column) {

		Collection<R> rows = new TreeSet<>();

		for (R row: getRows()) {
			if (!getValue(row, column).equals(getDefaultValue())) {
				rows.add(row);
			}
		}

		return rows;
	}

	@Override
	public void addRow(R row) {

		rows.add(row);
	}

	@Override
	public void removeRow(R row) {

		values.remove(row);
		rows.remove(row);
	}

	@Override
	public void resetRow(R row) {

		values.remove(row);
	}

	@Override
	public boolean containsRow(R row) {

		return rows.contains(row);
	}

	@Override
	public R getFirstRow() {

		return rows.first();
	}

	@Override
	public R getLastRow() {

		return rows.last();
	}

	@Override
	public int getRowCount() {

		return rows.size();
	}

	@Override
	public Map<C, V> getRowMap(R row) {

		return new SimpleMatrixRowMap<>(this, row);
	}

	@Override
	public SortedSet<C> getColumns() {

		return columns;
	}

	@Override
	public Collection<C> getColumnsWithNonDefaultValue(R row) {

		Collection<C> columns = new TreeSet<>();

		for (C column: getColumns()) {
			if (!getValue(row, column).equals(getDefaultValue())) {
				columns.add(column);
			}
		}

		return columns;
	}

	@Override
	public void addColumn(C column) {

		columns.add(column);
	}

	@Override
	public void removeColumn(C column) {

		for (Map<C, V> rowValues: values.values()) {
			rowValues.remove(column);
		}
		columns.remove(column);
	}

	/**
	 * Adds all values in the source row to the target row and removes the
	 * source row.
	 *
	 * @param sourceRowKey
	 *            the key of the source row
	 * @param targetRowKey
	 *            the key of the target row
	 */
	public void addAndRemoveRow(R sourceRowKey, R targetRowKey) {

		Map<C, V> sourceRow = values.remove(sourceRowKey);
		if (sourceRow != null) {
			for (Entry<C, V> entry: sourceRow.entrySet()) {
				addValue(targetRowKey, entry.getKey(), entry.getValue());
			}
		}

		rows.remove(sourceRowKey);
		rows.add(sourceRowKey);
	}

	/**
	 * Adds all values in the source column to the target column and removes the
	 * source column.
	 *
	 * @param sourceColumn
	 *            the key of the source column
	 * @param targetColumn
	 *            the key of the target column
	 */
	public void addAndRemoveColumn(C sourceColumn, C targetColumn) {

		// TODO: Should we be using m_traits.getDefaultValue()?
		for (Map<C, V> rowValues: values.values()) {
			V source = rowValues.remove(sourceColumn);
			V target = rowValues.get(targetColumn);
			if (source != null) {
				if (target != null) {
					rowValues.put(targetColumn, traits.plus(source, target));
				} else {
					rowValues.put(targetColumn, source);
				}
			}
		}

		columns.remove(sourceColumn);
		columns.add(targetColumn);
	}

	/**
	 * Adds all values of the source columns to the target column and removes
	 * all source columns.
	 *
	 * @param sourceColumns
	 *            the keys of the source columns
	 * @param targetColumn
	 *            the key of the target column
	 */
	public void addAndRemoveColumns(Iterable<C> sourceColumns, C targetColumn) {

		// TODO: Should we be using m_traits.getDefaultValue()?
		for (Map<C, V> rowValues: values.values()) {
			// get target value
			V target = rowValues.get(targetColumn);

			// add source values
			for (C sourceColumn: sourceColumns) {
				V source = rowValues.remove(sourceColumn);
				if (source != null) {
					if (target != null) {
						target = traits.plus(source, target);
					} else {
						target = source;
					}
				}
			}

			// set target value
			if (target != null) {
				rowValues.put(targetColumn, target);
			}
		}

		// remove source columns
		for (C sourceColumn: sourceColumns) {
			columns.remove(sourceColumn);
		}

		// add target column
		columns.add(targetColumn);
	}

	@Override
	public void resetColumn(C column) {

		for (Map<C, V> m_rowValues: values.values()) {
			m_rowValues.remove(column);
		}
	}

	@Override
	public boolean containsColumn(C column) {

		return columns.contains(column);
	}

	@Override
	public C getFirstColumn() {

		return columns.first();
	}

	@Override
	public C getLastColumn() {

		return columns.last();
	}

	@Override
	public int getColumnCount() {

		return columns.size();
	}

	public void merge(SimpleMatrix<R, C, V> other) {

		for (R row: other.getRows()) {
			addRow(row);
		}

		for (C column: other.getColumns()) {
			addColumn(column);
		}

		for (Map.Entry<R, Map<C, V>> rm: other.values.entrySet()) {
			for (Map.Entry<C, V> cm: rm.getValue().entrySet()) {
				setValue(rm.getKey(), cm.getKey(), cm.getValue());
			}
		}
	}

	public <RR extends R, CC extends C> void add(SimpleMatrix<RR, CC, V> other) {

		for (R row: other.getRows()) {
			addRow(row);
		}

		for (C column: other.getColumns()) {
			addColumn(column);
		}

		for (Map.Entry<RR, Map<CC, V>> rm: other.values.entrySet()) {
			for (Map.Entry<CC, V> cm: rm.getValue().entrySet()) {
				addValue(rm.getKey(), cm.getKey(), cm.getValue());
			}
		}
	}

	@Override
	public String toString() {

		String result = "";
		for (R row: rows) {
			result += rowToString(row) + "\n";
		}
		return result;
	}

	public AsciiTable toAsciiTable() {

		AsciiTable a = new AsciiTable(columns.size() + 1);

		// add header
		a.add("");
		for (C column: columns) {
			a.add(column);
		}

		// add body
		for (R row: rows) {
			a.add(row);
			for (C column: columns) {
				a.add(getValue(row, column));
			}
		}
		return a;
	}

	private String rowToString(R row) {

		String result = row + ": {";
		for (C column: columns) {
			result += " " + column + ": " + getValue(row, column) + ";";
		}
		result += " }";
		return result;
	}
}
