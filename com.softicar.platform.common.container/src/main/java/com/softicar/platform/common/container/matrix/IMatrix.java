package com.softicar.platform.common.container.matrix;

/**
 * Extends {@link IImmutableMatrix} with mutating methods.
 * 
 * @param <R>
 *            the type to identify a row of the matrix
 * @param <C>
 *            the type to identify a column of the matrix
 * @param <V>
 *            the value type of a matrix cell
 * @author Oliver Richers
 */
public interface IMatrix<R, C, V> extends IImmutableMatrix<R, C, V> {

	@Override
	IMatrix<R, C, V> clone();

	// -------------------- values -------------------- //

	/**
	 * Puts the specified value at the given matrix position. If there's already
	 * a value, it will be overwritten. If the specified row and/or column are
	 * not yet part of the matrix, they will be added.
	 */
	void setValue(R row, C col, V value);

	/**
	 * Adds the specified value to the already present value. The function used
	 * to add the value depends on the implementation of the schedule.
	 */
	void addValue(R row, C col, V value);

	/**
	 * Removes the value from the specified matrix position.
	 * <p>
	 * If there is not such value, this method does nothing.
	 */
	void removeValue(R row, C col);

	// -------------------- rows -------------------- //

	/**
	 * Adds the specified row to the matrix.
	 */
	void addRow(R row);

	/**
	 * Removes the specified row from the matrix.
	 * <p>
	 * All values of the row will be removed, too.
	 */
	void removeRow(R row);

	/**
	 * Resets all values to default for this row.
	 */
	void resetRow(R row);

	// -------------------- columns -------------------- //

	/**
	 * Adds the specified column to the matrix.
	 */
	void addColumn(C col);

	/**
	 * Removes the specified column from the matrix.
	 * <p>
	 * All values of the column will be removed, too.
	 */
	void removeColumn(C col);

	/**
	 * Resets the values to default for the given column.
	 */
	void resetColumn(C col);
}
