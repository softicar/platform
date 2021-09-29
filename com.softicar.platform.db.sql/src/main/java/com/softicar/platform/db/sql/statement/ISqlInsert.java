package com.softicar.platform.db.sql.statement;

import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;
import java.util.List;

/**
 * This class represents an <i>INSERT</i> statement for a database table.
 *
 * @param <R>
 *            the row type of the database table
 * @author Oliver Richers
 */
public interface ISqlInsert<R> extends ISqlFieldValueConsumer<R> {

	/**
	 * Sets the value for the given field.
	 * <p>
	 * Adds a respective assignment to the VALUES clause of this INSERT
	 * statement.
	 *
	 * @param field
	 *            the field to update (never null)
	 * @param value
	 *            the value to assign (may be null)
	 * @return this statement
	 */
	<V> ISqlInsert<R> set(ISqlField<R, V> field, V value);

	/**
	 * Closes the current value list and opens a new value list.
	 * <p>
	 * For multi-row inserts, this method must be called start a new table row.
	 * <p>
	 * Calling this method redundantly does nothing, that is, without a
	 * preceding call to {@link #set(ISqlField, Object)}, this method does
	 * nothing.
	 *
	 * @return this statement
	 */
	ISqlInsert<R> nextRow();

	/**
	 * Executes this statement as a single-row INSERT.
	 *
	 * @return the generated table row ID
	 */
	int execute();

	/**
	 * Executes this statement as a multi-row INSERT.
	 * <p>
	 * Please note that if the values for the ID column are pre-supplied by this
	 * INSERT statement, then this method returns an empty list.
	 *
	 * @return the list of generated table row IDs
	 */
	List<Integer> executeMultiInsert();

	/**
	 * Executes this statement without ID generation.
	 * <p>
	 * This method can be called for tables that do not have an ID column.
	 * <p>
	 * This method can also be used to <b>reinsert</b> table rows that
	 * <b>have</b> an ID column, but instead of generating new IDs, the existing
	 * IDs shall be used.
	 */
	void executeWithoutIdGeneration();
}
