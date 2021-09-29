package com.softicar.platform.common.container.matrix;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;

/**
 * Represents a two dimensional matrix.
 * <p>
 * An {@link IImmutableMatrix} has a number of rows, indexed by the row key of
 * type <i>R</i>, and a number of columns, indexed by the column key <i>C</i>.
 * The intersection of a row and a column is called a cell and its value is of
 * type <i>V</i>.
 * <p>
 * The useful property of this class is that there is a default value for every
 * cell of the matrix. This means that all cells are virtually initialized to
 * this default value and you do not have to check for <i>null</i>, every time
 * you read a cell value.
 * 
 * @param <R>
 *            the type to identify a row of the matrix
 * @param <C>
 *            the type to identify a column of the matrix
 * @param <V>
 *            the value type of a matrix cell
 * @author Oliver Richers
 */
public interface IImmutableMatrix<R, C, V> extends Cloneable {

	Iterable<IMatrixCell<R, C, V>> getMatrixCells();

	IImmutableMatrix<R, C, V> clone();

	// -------------------- values -------------------- //

	/**
	 * Returns the value at the specified matrix position.
	 * <p>
	 * If there is not value at the specified position (either because it was
	 * not set or because row and/or column are not part of the matrix), this
	 * returns {@link #getDefaultValue()}. If row and/or column are not part of
	 * the matrix, this function won't add them.
	 */
	V getValue(R row, C column);

	/**
	 * Returns the sum over all values of the specified row.
	 */
	V getTotalValue(R row);

	/**
	 * Returns the sum over all values of the specified column.
	 */
	V getTotalColumnValue(C column);

	/**
	 * Returns <i>true</i> if value for the specified position is set.
	 */
	boolean isSet(R row, C column);

	/**
	 * Returns the default value of the matrix positions.
	 * <p>
	 * This is the value that will be returned by {@link #getValue} if the
	 * specified position was not set yet. The value returned by this function
	 * depends on the implementation of the matrix.
	 */
	V getDefaultValue();

	// -------------------- rows -------------------- //

	/**
	 * Returns a collection containing all rows of the matrix.
	 * <p>
	 * The ordering of the rows depends on the implementation of the matrix.
	 */
	SortedSet<R> getRows();

	/**
	 * Returns <i>true</i> if the specified row is part of the matrix.
	 */
	boolean containsRow(R row);

	/**
	 * @return the first row of this matrix
	 * @throws java.util.NoSuchElementException
	 *             if there are no rows
	 */
	R getFirstRow();

	/**
	 * @return the last row of this matrix
	 * @throws java.util.NoSuchElementException
	 *             if there are no rows
	 */
	R getLastRow();

	/**
	 * @return The number of rows.
	 */
	int getRowCount();

	Map<C, V> getRowMap(R row);

	// -------------------- columns -------------------- //

	/**
	 * Returns a collection containing all columns of the matrix.
	 * <p>
	 * The ordering of the columns depends on the implementation of the matrix.
	 */
	SortedSet<C> getColumns();

	/**
	 * Returns <i>true</i> if the specified column is part of the matrix.
	 */
	boolean containsColumn(C column);

	/**
	 * @return the first column of this matrix
	 * @throws java.util.NoSuchElementException
	 *             if there are no columns
	 */
	C getFirstColumn();

	/**
	 * @return the last column of this matrix
	 * @throws java.util.NoSuchElementException
	 *             if there are no columns
	 */
	C getLastColumn();

	/**
	 * @return the number of columns.
	 */
	int getColumnCount();

	// -------------------- miscellaneous -------------------- //

	@Override
	String toString();

	Collection<C> getColumnsWithNonDefaultValue(R row);
}
