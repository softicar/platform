package com.softicar.platform.common.container.schedule;

import com.softicar.platform.common.container.matrix.IMatrixTraits;
import com.softicar.platform.common.container.matrix.simple.SimpleMatrix;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A basic implementation of the {@link ISchedule} interface.
 *
 * @author Oliver Richers
 */
public class SimpleSchedule<R extends Comparable<? super R>, C extends Comparable<? super C>, V> extends SimpleMatrix<R, C, V> implements ISchedule<R, C, V> {

	private final C backorderColumn;

	public SimpleSchedule(IMatrixTraits<V> traits, C backorderColumn) {

		super(traits);
		this.backorderColumn = backorderColumn;
		addColumn(backorderColumn);
	}

	/**
	 * Constructs a SimpleSchedule with the specified traits.
	 *
	 * @param traits
	 *            the traits object to use
	 * @param backorderColumn
	 *            defines the key used to access the backorder column
	 */
	public SimpleSchedule(IMatrixTraits<V> traits, C backorderColumn, Comparator<R> rowComparator, Comparator<C> colComparator) {

		super(traits, rowComparator, colComparator);
		this.backorderColumn = backorderColumn;
		addColumn(backorderColumn);
	}

	@Override
	public V getBackorder(R row) {

		return getValue(row, backorderColumn);
	}

	@Override
	public void setBackorder(R row, V value) {

		setValue(row, backorderColumn, value);
	}

	@Override
	public void addBackorder(R row, V value) {

		setBackorder(row, getTraits().plus(getBackorder(row), value));
	}

	@Override
	public Collection<C> getColumnsWithoutBackorder() {

		Set<C> result = new TreeSet<>();
		for (C col: getColumns()) {
			if (col != null && !col.equals(backorderColumn)) {
				result.add(col);
			}
		}
		return result;
	}

	@Override
	public C getBackorderColumn() {

		return backorderColumn;
	}

	@Override
	public void makeBackorder(C column) {

		assert !column.equals(backorderColumn);

		addAndRemoveColumn(column, backorderColumn);
	}

	@Override
	public void makeBackorder(Collection<C> columns) {

		addAndRemoveColumns(columns, backorderColumn);
	}

	@Override
	public void makeBackorderBefore(C markerCol) {

		// collect all columns to be removed
		List<C> toBeRemoved = new ArrayList<>();
		for (C col: getColumnsWithoutBackorder()) {
			if (col.compareTo(markerCol) < 0) {
				toBeRemoved.add(col);
			}
		}

		makeBackorder(toBeRemoved);
	}
}
