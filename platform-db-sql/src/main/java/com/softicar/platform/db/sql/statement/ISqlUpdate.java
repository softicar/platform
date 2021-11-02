package com.softicar.platform.db.sql.statement;

import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

/**
 * This class represents an <i>UPDATE</i> statement over a database table.
 *
 * @param <R>
 *            the row type of the database table
 * @author Oliver Richers
 */
public interface ISqlUpdate<R> extends ISqlConditionalStatement<R, ISqlUpdate<R>>, ISqlFieldValueConsumer<R> {

	/**
	 * Sets the value for the given field.
	 * <p>
	 * Adds a respective assignment to the SET clause of this UPDATE statement.
	 *
	 * @param field
	 *            the field to update (never null)
	 * @param value
	 *            the value to assign (may be null)
	 * @return this statement
	 */
	<V> ISqlUpdate<R> set(ISqlField<R, V> field, V value);

	/**
	 * Increments the current value of the given field.
	 * <p>
	 * This method is restricted to numeric fields.
	 *
	 * @param field
	 *            the field to increment
	 * @param value
	 *            the increment value
	 * @return this statement
	 */
	<V extends Number> ISqlUpdate<R> increment(ISqlField<R, V> field, V value);

	/**
	 * Adds the given field to the ORDER BY clause of this statement.
	 * <p>
	 * As in SQL, the default order direction is ascending. You can call this
	 * method multiple times, each call will extend the ORDER BY clause.
	 *
	 * @param field
	 *            the field to add
	 * @return this UPDATE statement
	 */
	ISqlUpdate<R> orderBy(ISqlField<R, ?> field);

	/**
	 * Adds the given field to the ORDER BY clause of this statement.
	 * <p>
	 * This method works like {@link #orderBy(ISqlField)} but with descending
	 * order direction.
	 *
	 * @param field
	 *            the field to add
	 * @return this UPDATE statement
	 */
	ISqlUpdate<R> orderDescendingBy(ISqlField<R, ?> field);

	/**
	 * Executes this UPDATE statement.
	 *
	 * @return the number of affected table rows
	 */
	int execute();

	/**
	 * Executes this UPDATE statement with a LIMIT count.
	 * <p>
	 * This method should be used with care as it can lead to non-deterministic
	 * behavior. Always ensure that the ORDER BY clause forces a deterministic
	 * ordering of all table rows. Otherwise this could break the database
	 * replication.
	 * <p>
	 * If count is zero or less, this method works like {@link #execute()}.
	 *
	 * @param count
	 *            the maximum number of affected rows (if count is zero or less,
	 *            then the number of affected rows will not be limited)
	 * @return the number of affected table rows
	 */
	int execute(int count);
}
