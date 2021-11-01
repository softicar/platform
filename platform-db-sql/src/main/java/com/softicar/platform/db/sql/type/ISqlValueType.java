package com.softicar.platform.db.sql.type;

import com.softicar.platform.db.core.DbResultSet;
import java.sql.ResultSet;

/**
 * Common interface of all SQL value types.
 *
 * @author Oliver Richers
 */
public interface ISqlValueType<V> {

	/**
	 * Load a value from the given {@link ResultSet}.
	 *
	 * @param resultSet
	 *            the {@link ResultSet} to load from
	 * @param index
	 *            the start index into the {@link ResultSet}
	 * @return the loaded value (may be null)
	 */
	V getValue(DbResultSet resultSet, int index);

	/**
	 * Returns the Java class for values of this value type.
	 *
	 * @return the value class (never null)
	 */
	Class<V> getValueClass();

	/**
	 * Compares the given values.
	 * <p>
	 * The comparison is <i>null</i>-safe in the sense, that <i>null</i> is less
	 * than non-null and equal to itself.
	 *
	 * @param left
	 *            the left value
	 * @param right
	 *            the right value
	 * @return returns -1 if <i>left</i> is less than <i>right</i>, 1 if
	 *         <i>right</i> is less than <i>left</i> and 0 otherwise.
	 */
	int compare(V left, V right);

	/**
	 * Returns the number of columns that this value type spans in the
	 * {@link ResultSet}.
	 *
	 * @return the number of result set columns
	 */
	int getColumnCount();
}
