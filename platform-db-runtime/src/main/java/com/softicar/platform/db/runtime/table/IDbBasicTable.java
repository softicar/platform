package com.softicar.platform.db.runtime.table;

import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.cache.DbTableRowCache;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.table.listener.IDbTableListener;
import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.db.runtime.transients.IDbTransientField;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.ISqlDelete;
import com.softicar.platform.db.sql.statement.ISqlInsert;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.db.sql.statement.ISqlUpdate;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public interface IDbBasicTable<R> extends ISqlTable<R>, ITestMarker {

	/**
	 * Returns the row factory of this table.
	 * <p>
	 * This is an internal method that should not be used by normal code.
	 *
	 * @return row factory (never null)
	 */
	Supplier<R> getRowFactory();

	/**
	 * Returns whether table listeners are registered or not.
	 *
	 * @return true if at least one listeners exists, false otherwise
	 */
	boolean hasTableListeners();

	/**
	 * Returns all table listeners of this table.
	 *
	 * @return the table listeners (never null)
	 */
	Iterable<IDbTableListener<R>> getTableListeners();

	// -------------------- fields -------------------- //

	/**
	 * Returns a list of all fields, including control and data fields.
	 *
	 * @return list of all fields
	 */
	@Override
	List<? extends IDbField<R, ?>> getAllFields();

	/**
	 * Returns a list of all control fields.
	 *
	 * @return list of all control fields
	 */
	List<? extends IDbField<R, ?>> getControlFields();

	/**
	 * Returns a list of all data fields.
	 *
	 * @return list of all data fields
	 */
	List<? extends IDbField<R, ?>> getDataFields();

	// -------------------- field access -------------------- //

	/**
	 * Returns the value of the field from the given table row.
	 * <p>
	 * This method first checks if the data of the table row must be loaded or
	 * reloaded from the database, which is the case for stub-objects and
	 * invalidated table rows.
	 *
	 * @param row
	 *            the table row
	 * @param field
	 *            the field to read
	 * @return the field value
	 */
	<V> V getValue(R row, IDbField<R, V> field);

	/**
	 * Sets the field of the given table row to the specified value.
	 * <p>
	 * This method first calls {@link #getValue} to check if the new value is
	 * different from the current value of the field. This may cause a reload of
	 * the table row from the database. If the new value is equal to the
	 * existing value, this method does nothing and returns. If the new value is
	 * different, a backup of the table row is created and attached to the
	 * current {@link DbTransaction} object (if not done so yet), so that the
	 * initial value can be restored on a transaction rollback. After assigning
	 * the new value to the field, the data-changed flags of the table row is
	 * enabled and all registered callbacks are executed.
	 *
	 * @param row
	 *            the table row containing the field
	 * @param field
	 *            the field to write
	 * @param value
	 *            the new value
	 * @return <i>true</i> if a new value was assigned, <i>false</i> if the new
	 *         value is equal to the current value
	 */
	<V> boolean setValue(R row, IDbField<R, V> field, V value);

	// -------------------- transient field access -------------------- //

	<V> V getTransientValue(R row, IDbTransientField<R, V> field);

	<V> void setTransientValue(R row, IDbTransientField<R, V> field, V value);

	// -------------------- keys -------------------- //

	Collection<? extends IDbKey<R>> getAllKeys();

	Collection<? extends IDbKey<R>> getUniqueKeys();

	Collection<? extends IDbKey<R>> getIndexKeys();

	// -------------------- statements -------------------- //

	ISqlDelete<R> createDelete();

	ISqlInsert<R> createInsert();

	ISqlUpdate<R> createUpdate();

	ISqlSelect<R> createSelect();

	ISqlSelect<R> createSelect(SqlSelectLock lock);

	// -------------------- counting -------------------- //

	/**
	 * Returns the number of all table rows in this table.
	 *
	 * @return the number of all table rows
	 */
	default int countAll() {

		return createSelect().count();
	}

	/**
	 * Returns the number of all table rows matching the given condition.
	 *
	 * @param condition
	 *            the condition to check
	 * @return the number of matching table rows
	 */
	default int countAll(ISqlBooleanExpression<R> condition) {

		return createSelect().where(condition).count();
	}

	// -------------------- deleting -------------------- //

	/**
	 * Deletes all table rows matching the given condition.
	 *
	 * @param condition
	 *            the condition to check
	 * @return the number of deleted table rows
	 */
	default int deleteAll(ISqlBooleanExpression<R> condition) {

		return createDelete().where(condition).execute();
	}

	// -------------------- loading -------------------- //

	/**
	 * Loads all table rows from this table.
	 * <p>
	 * All loaded table rows that are contained in the internal table row cache
	 * (see {@link DbTableRowCache}) will be updated as long as they are not
	 * <i>dirty</i> (see {@link DbTableRowFlag#DIRTY}.
	 * <p>
	 * Be careful, this method can easily run out of heap memory.
	 *
	 * @return list of all persistent table rows
	 */
	List<R> loadAll();

	// -------------------- reloading -------------------- //

	/**
	 * Reloads all given table rows, with a single statement.
	 *
	 * @param rows
	 *            the table rows to reload
	 */
	void reloadAll(Collection<R> rows);

	/**
	 * Reloads all given table rows for update, with a single statement.
	 *
	 * @param rows
	 *            the table rows to reload for update
	 */
	void reloadAllForUpdate(Collection<R> rows);

	// -------------------- stubs -------------------- //

	/**
	 * Converts the given stub rows into real rows by loading them from this
	 * database table.
	 *
	 * @param stubRows
	 *            list of stub rows to load
	 */
	void unstubAll(Collection<R> stubRows);

	// -------------------- saving -------------------- //

	/**
	 * Saves the given table row to the database.
	 * <p>
	 * This method uses the same logic as {@link #saveAll(Collection)}.
	 *
	 * @param row
	 *            the table row to save
	 */
	void save(R row);

	/**
	 * Saves the given table rows to the database.
	 * <p>
	 * If a table row does already exist in the database, an <i>UPDATE</i>
	 * statement is executed. Otherwise and <i>INSERT</i> statement is executed.
	 * <p>
	 * If the rows in this table have a generated primary key, rows without
	 * primary key will be inserted, while rows without a key will be updated.
	 * The primary key fields of the rows will be updated with the generated
	 * values.
	 * <p>
	 * If the primary key is not generated, a <i>SELECT</i> will be executed to
	 * check which rows already exists in the database. Afterwards, respective
	 * <i>INSERT</i> and <i>UPDATE</i> statements will be executed as necessary.
	 * <p>
	 * This methods aggregates the rows to insert into multi-<i>INSERT</i>
	 * statements to save them as quickly as possible.
	 *
	 * @param rows
	 *            the table row to save
	 */
	void saveAll(Collection<? extends R> rows);

	/**
	 * Inserts and/or updates the given objects using the specified chunk size.
	 * <p>
	 * Same as {@link #saveAll(Collection)} but with the given chunk size.
	 *
	 * @param rows
	 *            the rows to save
	 * @param chunkSize
	 *            the maximum number of objects to aggregate into an a single
	 *            multi-<i>INSERT</i> statement
	 */
	void saveAll(Collection<? extends R> rows, int chunkSize);

	/**
	 * Saves the given table rows, that need to be saved, to the database.
	 * <p>
	 * Same as {@link #saveAll(Collection)} but only executes statements for new
	 * or modified rows.
	 *
	 * @param rows
	 *            the rows to save
	 */
	void saveAllIfNecessary(Collection<? extends R> rows);

	/**
	 * Saves the given table rows, that need to be saved, to the database.
	 * <p>
	 * Same as {@link #saveAllIfNecessary(Collection)} but with the given chunk
	 * size.
	 *
	 * @param rows
	 *            the rows to save
	 * @param chunkSize
	 *            the maximum number of objects to aggregate into an a single
	 *            multi-<i>INSERT</i> statement
	 */
	void saveAllIfNecessary(Collection<? extends R> rows, int chunkSize);

	// -------------------- basics -------------------- //

	/**
	 * Returns the fully qualified name of this table.
	 */
	@Override
	String toString();

	/**
	 * Returns true if the two tables point to the same database table.
	 */
	@Override
	boolean equals(Object other);
}
