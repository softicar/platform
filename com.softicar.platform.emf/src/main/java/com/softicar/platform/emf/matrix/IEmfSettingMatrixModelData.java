package com.softicar.platform.emf.matrix;

import java.util.Optional;
import java.util.Set;

/**
 * Data container of an {@link IEmfSettingMatrixModel}.
 *
 * @author Alexander Schmidt
 */
public interface IEmfSettingMatrixModelData<R, C, V> {

	/**
	 * Returns the value at the given row and column coordinates.
	 *
	 * @param row
	 *            the row coordinate (non-null)
	 * @param column
	 *            the column coordinate (non-null)
	 * @return the value at the given row and column coordinates
	 */
	Optional<V> getValue(R row, C column);

	/**
	 * Returns the value at the given row and column coordinates. If no value is
	 * present there, a new default value is returned.
	 * <p>
	 * The returned default value is not inserted into this data container.
	 *
	 * @param row
	 *            the row coordinate (non-null)
	 * @param column
	 *            the column coordinate (non-null)
	 * @return the value at the given row and column coordinates
	 */
	default V getValueOrDefault(R row, C column) {

		return getValue(row, column).orElse(createDefaultValue());
	}

	/**
	 * Returns the wildcard value at the given column coordinate.
	 *
	 * @param column
	 *            the column coordinate (non-null)
	 * @return the wildcard value at the given column coordinate
	 */
	Optional<V> getWildcardValue(C column);

	/**
	 * Returns the wildcard value at the given column coordinate. If no wildcard
	 * value is present there, a new default value is returned.
	 * <p>
	 * The returned default value is not inserted into this data container.
	 *
	 * @param column
	 *            the column coordinate (non-null)
	 * @return the wildcard value at the given column coordinate, or a new
	 *         default value (may be null)
	 */
	default V getWildcardValueOrDefault(C column) {

		return getWildcardValue(column).orElse(createDefaultValue());
	}

	/**
	 * Inserts the given value to this data container, at the given row and
	 * column coordinates. Overwrites an existing value, if any.
	 *
	 * @param row
	 *            the row coordinate (non-null)
	 * @param column
	 *            the column coordinate (non-null)
	 * @param value
	 *            the value to insert (may be null)
	 */
	void setValue(R row, C column, V value);

	/**
	 * Inserts the given value to this data container, at the given row and
	 * column coordinates. Overwrites an existing value, if any.
	 *
	 * @param row
	 *            the row coordinate (non-null)
	 * @param column
	 *            the column coordinate (non-null)
	 * @param value
	 *            the value to insert
	 */
	void setValue(R row, C column, Optional<V> value);

	/**
	 * Inserts the given wildcard value to this data container, at the given
	 * column coordinate. Overwrites an existing value, if any.
	 *
	 * @param column
	 *            the column coordinate (non-null)
	 * @param value
	 *            the value to insert (may be null)
	 */
	void setWildcardValue(C column, V value);

	/**
	 * Inserts the given wildcard value to this data container, at the given
	 * column coordinate. Overwrites an existing value, if any.
	 *
	 * @param column
	 *            the column coordinate (non-null)
	 * @param value
	 *            the value to insert
	 */
	void setWildcardValue(C column, Optional<V> value);

	/**
	 * Returns all rows for which this data container holds values.
	 *
	 * @return all rows for which this data container holds values (never null)
	 */
	Set<R> getRows();

	/**
	 * Returns all columns for which this data container holds values.
	 *
	 * @return all columns for which this data container holds values (never
	 *         null)
	 */
	Set<C> getColumns();

	/**
	 * Clears this data container.
	 */
	void clearAll();

	/**
	 * Creates a new default value for the type of values that is stored in this
	 * data container.
	 *
	 * @return a new default value (may be null)
	 */
	V createDefaultValue();
}
