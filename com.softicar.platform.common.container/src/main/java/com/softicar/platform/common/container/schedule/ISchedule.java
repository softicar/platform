package com.softicar.platform.common.container.schedule;

import com.softicar.platform.common.container.matrix.IMatrix;
import java.util.Collection;

/**
 * A schedule is an {@link IMatrix} with a special backorder column.
 * <p>
 * The backorder column is treated like a regular column
 * 
 * @author Oliver Richers
 */
public interface ISchedule<R, C, V> extends IMatrix<R, C, V> {

	/**
	 * @return the column which represents the backorder
	 */
	C getBackorderColumn();

	/**
	 * Returns the backorder for the specified row.
	 * <p>
	 * If the value was not set yet, this returns the default value.
	 */
	V getBackorder(R row);

	/**
	 * Sets the backorder value for the specified row.
	 */
	void setBackorder(R row, V value);

	/**
	 * Increases the backorder value by the specified value.
	 * <p>
	 * If the backorder was not set yet, this adds the specified value to the
	 * default value.
	 */
	void addBackorder(R row, V value);

	/**
	 * Returns a collection with all columns except the backorder column.
	 */
	Collection<C> getColumnsWithoutBackorder();

	/**
	 * Merges the specified column into the backorder.
	 * <p>
	 * This adds the values of the specified column to the backorder and removes
	 * the column from this schedule.
	 * 
	 * @param column
	 *            the column to merge into backorder, must not be the backorder
	 *            column itself
	 */
	void makeBackorder(C column);

	/**
	 * Calls {@link #makeBackorder(Object)} for every specified column.
	 * 
	 * @param columns
	 *            all columns to merge into backorder
	 */
	void makeBackorder(Collection<C> columns);

	/**
	 * Calls {@link #makeBackorder(Object)} for every column less or equals to
	 * the specified marker column.
	 * 
	 * @param markerCol
	 *            the column to use as boundary
	 */
	void makeBackorderBefore(C markerCol);
}
